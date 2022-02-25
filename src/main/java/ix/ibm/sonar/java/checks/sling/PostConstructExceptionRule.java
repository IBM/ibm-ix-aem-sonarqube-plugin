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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import ix.ibm.sonar.java.utils.JavaFinder;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.utils.WarningConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "PostConstructException")
public class PostConstructExceptionRule extends ExpandedTreeVisitor {

    @Override
    public void visitMethod(final MethodTree methodTree) {
        if (JavaFinder.containsAnnotation(methodTree, PackageConstants.POST_CONSTRUCT_ANNOTATION)) {
            final BlockTree blockTree = methodTree.block();
            if (Objects.nonNull(blockTree)) {
                final List<StatementTree> blockElements = blockTree.body();

                if (!CollectionUtils.isEmpty(blockElements)) {
                    final boolean isTryStatementFirst = blockElements.get(0).is(Tree.Kind.TRY_STATEMENT);
                    if (isTryStatementFirst) {
                        final TryStatementTree tryStatementTree = (TryStatementTree) blockElements.get(0);
                        this.checkCaughtExceptions(tryStatementTree);
                    }

                    if (blockElements.size() != 1 || !blockElements.get(0).is(Tree.Kind.TRY_STATEMENT)) {
                        this.context.reportIssue(this, methodTree.simpleName(), WarningConstants.WARNING_WRAP_IN_TRY_CATCH);
                    }
                }
            }

        }
        super.visitMethod(methodTree);
    }

    private void checkCaughtExceptions(final TryStatementTree tryStatementTree) {
        for (final CatchTree catchTree : tryStatementTree.catches()) {
            final String exceptionTypeFullyQualifiedName = catchTree.parameter().simpleName().symbolType().fullyQualifiedName();
            if (StringUtils.equals(exceptionTypeFullyQualifiedName, PackageConstants.EXCEPTION)) {
                this.context.reportIssue(this, catchTree.catchKeyword(), WarningConstants.WARNING_GENERIC_EXCEPTION);
            }
        }
    }

}

