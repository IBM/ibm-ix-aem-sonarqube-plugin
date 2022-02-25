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
 * Code changes to align with Java 11 coding standards and usage of JUnit5.
 * Simplification of matcher.
 *
 */

package ix.ibm.sonar.java.checks.sling;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "AvoidDeprecatedAdministrative")
public class AvoidDeprecatedAdministrativeRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "Avoid using %s method - (Deprecated)";
    private static final String SLING_REPOSITORY = "org.apache.sling.jcr.api.SlingRepository";
    private static final String LOGIN_ADMINISTRATIVE = "loginAdministrative";
    private static final String RESOURCE_RESOLVER_FACTORY = "org.apache.sling.api.resource.ResourceResolverFactory";
    private static final String GET_ADMIN_RESOURCE_RESOLVER = "getAdministrativeResourceResolver";

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        if (methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
            final String variableType = memberSelectExpressionTree.expression().symbolType().fullyQualifiedName();
            final String methodName = memberSelectExpressionTree.identifier().symbol().name();

            if (this.isDeprecatedMethod(variableType, methodName)) {
                this.context.reportIssue(this, methodInvocationTree, String.format(WARNING_MESSAGE, methodName));
            }

        } super.visitMethodInvocation(methodInvocationTree);
    }

    private boolean isDeprecatedMethod(final String variableType, final String methodName) {
        return (StringUtils.equals(variableType, SLING_REPOSITORY) && StringUtils.equals(methodName, LOGIN_ADMINISTRATIVE)) || (StringUtils.equals(
          variableType, RESOURCE_RESOLVER_FACTORY) && StringUtils.equals(methodName, GET_ADMIN_RESOURCE_RESOLVER));
    }

}
