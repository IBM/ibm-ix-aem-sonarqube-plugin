/*
 * Copyright (C) 2020-present IBM Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ix.ibm.sonar.java.checks.sling;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import ix.ibm.sonar.java.utils.AemRecognizer;
import ix.ibm.sonar.java.utils.Constants;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.utils.WarningConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import ix.ibm.sonar.java.visitors.FilterMethodFinder;

@Rule(key = "FilterException")
public class FilterExceptionRule extends ExpandedTreeVisitor {

    private static final String WARNING_USE_TRY_CATCH = "Put all the custom code in a try-catch clause (except for the doFilter call)";
    private static final String WARNING_SPECIFIC_DISALLOWED_EXCEPTION = "Do not catch IOException or ServletException, it might cause unexpected behaviour";

    @Override
    public void visitClass(final ClassTree classTree) {
        if (AemRecognizer.isSlingFilter(classTree)) {
            final Optional<MethodTree> optionalDoFilterMethodTree = FilterMethodFinder.getDoFilterMethodTree(classTree);
            if (optionalDoFilterMethodTree.isPresent()) {
                final MethodTree doFilterMethodTree = optionalDoFilterMethodTree.get();
                this.verifyDoFilterMethod(doFilterMethodTree);
            }
        }
        super.visitClass(classTree);
    }

    private void verifyDoFilterMethod(final MethodTree methodTree) {
        final BlockTree blockTree = methodTree.block();
        if (Objects.isNull(blockTree)) {
            return;
        }
        final List<StatementTree> blockElements = blockTree.body();

        //Check if possible
        if (!CollectionUtils.isEmpty(blockElements)) {
            final boolean isTryStatementFirst = blockElements.get(0).is(Tree.Kind.TRY_STATEMENT);
            if (isTryStatementFirst) {
                final TryStatementTree tryStatementTree = (TryStatementTree) blockElements.get(0);
                this.checkCaughtExceptions(tryStatementTree);
            }

            if (!isTryStatementFirst || blockElements.size() > 2 || (blockElements.size() == 2 && !this.isMethodDoFilterCalled(
              blockElements.get(1)))) {
                this.context.reportIssue(this, methodTree.simpleName(), WARNING_USE_TRY_CATCH);
            }
        }
    }

    private void checkCaughtExceptions(final TryStatementTree tryStatementTree) {
        final String[] unexpectedBehaviourExceptions = new String[] { PackageConstants.IO_EXCEPTION, PackageConstants.JAVAX_SERVLET_EXCEPTION };
        for (final CatchTree catchTree : tryStatementTree.catches()) {
            final String exceptionTypeFullyQualifiedName = catchTree.parameter().simpleName().symbolType().fullyQualifiedName();
            if (StringUtils.equalsAny(exceptionTypeFullyQualifiedName, unexpectedBehaviourExceptions)) {
                this.context.reportIssue(this, catchTree.catchKeyword(), WARNING_SPECIFIC_DISALLOWED_EXCEPTION);
            }
            if (StringUtils.equals(exceptionTypeFullyQualifiedName, PackageConstants.EXCEPTION)) {
                this.context.reportIssue(this, catchTree.catchKeyword(), WarningConstants.WARNING_GENERIC_EXCEPTION);
            }
        }
    }

    private boolean isMethodDoFilterCalled(final StatementTree statementTree) {
        boolean ret = false;
        if (statementTree.is(Tree.Kind.EXPRESSION_STATEMENT)) {
            final ExpressionStatementTree expressionStatementTree = (ExpressionStatementTree) statementTree;
            if (expressionStatementTree.expression().is(Tree.Kind.METHOD_INVOCATION)) {
                final MethodInvocationTree methodInvocationTree = (MethodInvocationTree) expressionStatementTree.expression();
                if (methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
                    final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
                    if (StringUtils.equals(memberSelectExpressionTree.identifier().name(), Constants.DO_FILTER_METHOD_NAME)) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

}

