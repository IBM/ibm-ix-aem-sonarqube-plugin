/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Modification copyright (C) 2020-present IBM Corporation
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 *
 * Modifications:
 * Code changes to align with Java 11 coding standards.
 * Ensure that the ResourceResolver is marked as non-compliant if has been received via the
 * ResourceResolverFactory and is not within a try-catch block.
 */
package ix.ibm.sonar.java.checks.sling;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LambdaExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

/**
 * Rule to check whether @ResourceResolver has been used within a try with
 * resources block to ensure auto closing.
 */
@Rule(key = "TryWithResourcesResourceResolver")
public class TryWithResourcesResourceResolverRule extends ExpandedTreeVisitor {

    private static final String SLING_RESOURCE_RESOLVER = "org.apache.sling.api.resource.ResourceResolver";
    private static final String SLING_RESOURCE_RESOLVER_FACTORY = "org.apache.sling.api.resource.ResourceResolverFactory";
    private static final String GET_RESOURCE_RESOLVER = "getResourceResolver";
    private static final String GET_SERVICE_RESOURCE_RESOLVER = "getServiceResourceResolver";
    private static final String WARNING_MESSAGE = "ResourceResolver should be closed using try-with-resources Java feature.";

    private boolean withinTryBlock = false;
    private boolean withinLambdaExpression = false;
    private List<String> resourceResolversInTryExpression = new ArrayList<>();

    @Override
    public void visitVariable(final VariableTree tree) {
        if (this.verifyResourceResolverAccess(tree)) {
            this.context.reportIssue(this, tree, WARNING_MESSAGE);
        }
        super.visitVariable(tree);
    }

    @Override
    public void visitTryStatement(final TryStatementTree tree) {
        this.withinTryBlock = true;
        tree
          .resourceList()
          .stream()
          .filter(VariableTree.class::isInstance)
          .map(VariableTree.class::cast)
          .map(variable -> variable.simpleName().name())
          .forEach(this.resourceResolversInTryExpression::add);

        super.visitTryStatement(tree);
        this.resourceResolversInTryExpression.clear();
        this.withinTryBlock = false;
    }

    @Override
    public void visitLambdaExpression(final LambdaExpressionTree lambdaExpressionTree) {
        this.withinLambdaExpression = true;
        super.visitLambdaExpression(lambdaExpressionTree);
        this.withinLambdaExpression = false;
    }

    private boolean verifyResourceResolverAccess(final VariableTree tree) {
        return this.withinTryBlock && !this.withinLambdaExpression && this.isResourceResolverType(
          tree) && !this.resourceResolversInTryExpression.contains(tree.simpleName().name())
                 && this.isAccessViaResourceResolverFactory(tree);
    }

    private boolean isResourceResolverType(final VariableTree tree) {
        return SLING_RESOURCE_RESOLVER.equals(tree.type().symbolType().fullyQualifiedName());
    }

    private boolean isAccessViaResourceResolverFactory(final VariableTree tree) {
        final ExpressionTree initializerTree = tree.initializer();
        return initializerTree != null && initializerTree.is(Tree.Kind.METHOD_INVOCATION) && this.isResourceResolverFactoryMethod(
          tree);
    }

    private boolean isResourceResolverFactoryMethod(final VariableTree tree) {
        final ExpressionTree initializer = tree.initializer();
        if (!(initializer instanceof MethodInvocationTree)) {
            return false;
        }

        final ExpressionTree methodSelect = ((MethodInvocationTree) tree.initializer()).methodSelect();
        if (!(methodSelect instanceof MemberSelectExpressionTree)) {
            return false;
        }

        final String methodName = ((MemberSelectExpressionTree) methodSelect).identifier().symbol().name();
        return SLING_RESOURCE_RESOLVER_FACTORY.equals(((MemberSelectExpressionTree) methodSelect).expression().symbolType().fullyQualifiedName()) &&
                 (GET_RESOURCE_RESOLVER.equals(methodName) || GET_SERVICE_RESOURCE_RESOLVER.equals(methodName));
    }
}
