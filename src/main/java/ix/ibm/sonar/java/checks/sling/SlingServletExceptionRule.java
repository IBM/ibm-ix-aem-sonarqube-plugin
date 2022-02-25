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

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import ix.ibm.sonar.java.utils.AemRecognizer;
import ix.ibm.sonar.java.utils.WarningConstants;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import ix.ibm.sonar.java.visitors.OverrideMethodFinder;
import ix.ibm.sonar.java.visitors.ResponseStatusFinder;

@Rule(key = "SlingServletException")
public class SlingServletExceptionRule extends ExpandedTreeVisitor {

    private static final String WARNING_SET_RESPONSE_CODE_500 = "Set response status to HTTP code 500";

    @Override
    public void visitClass(final ClassTree tree) {
        if (AemRecognizer.isSlingServlet(tree)) {
            final List<MethodTree> servletMethods = OverrideMethodFinder.getOverriddenMethods(tree);

            for (final MethodTree servletMethodTree : servletMethods) {
                final BlockTree blockTree = servletMethodTree.block();
                if (Objects.nonNull(blockTree)) {
                    final List<StatementTree> blockElements = blockTree.body();
                    if (blockElements.size() != 1 || !blockElements.get(0).is(Tree.Kind.TRY_STATEMENT)) {
                        this.context.reportIssue(this, servletMethodTree.simpleName(), WarningConstants.WARNING_WRAP_IN_TRY_CATCH);
                    } else {
                        final TryStatementTree tryStatementTree = ((TryStatementTree) blockElements.get(0));
                        this.verifyTryCatchClause(tryStatementTree);
                    }
                }
            }
        }
        super.visitClass(tree);
    }

    private void verifyTryCatchClause(final TryStatementTree tryStatementTree) {
        final List<CatchTree> catchTrees = tryStatementTree.catches();
        for (final CatchTree catchTree : catchTrees) {
            if (ResponseStatusFinder.isResponseStatusMissing(catchTree.block())) {
                this.context.reportIssue(this, catchTree.catchKeyword(), WARNING_SET_RESPONSE_CODE_500);
            }
            final String exceptionTypeFullyQualifiedName = catchTree.parameter().simpleName().symbolType().fullyQualifiedName();
            if (StringUtils.equals(exceptionTypeFullyQualifiedName, PackageConstants.EXCEPTION)) {
                this.context.reportIssue(
                  this, catchTree.catchKeyword(), WarningConstants.WARNING_GENERIC_EXCEPTION);
            }
        }
    }

}

