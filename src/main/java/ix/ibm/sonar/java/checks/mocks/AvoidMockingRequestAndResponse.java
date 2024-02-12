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

package ix.ibm.sonar.java.checks.mocks;

import ix.ibm.sonar.java.utils.MockChecker;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.AemTestClassTreeVisitor;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(key = "AvoidMockingRequestAndResponse")
public class AvoidMockingRequestAndResponse extends AemTestClassTreeVisitor {

    private static final String AVOID_MOCKING_REQUEST_MESSAGE = "Avoid mocking SlingHttpServletRequest, use the one provided from the mocked context instead";
    private static final String AVOID_MOCKING_RESPONSE_MESSAGE = "Avoid mocking SlingHttpServletResponse, use the one provided from the mocked context instead";

    @Override
    public void visitVariable(final VariableTree variableTree) {
        final Type identifierType = variableTree.type().symbolType();
        if (identifierType.isSubtypeOf(PackageConstants.SLING_HTTP_SERVLET_REQUEST) && MockChecker.isBeingMockedViaAnnotation(variableTree)) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_REQUEST_MESSAGE);
        }
        if (identifierType.isSubtypeOf(PackageConstants.SLING_HTTP_SERVLET_RESPONSE) && MockChecker.isBeingMockedViaAnnotation(variableTree)) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_RESPONSE_MESSAGE);
        }
        super.visitVariable(variableTree);
    }

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        final Type returnType = methodInvocationTree.symbolType();
        if (returnType.isSubtypeOf(PackageConstants.SLING_HTTP_SERVLET_REQUEST) && MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree)) {
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_REQUEST_MESSAGE);
        }
        if (returnType.isSubtypeOf(PackageConstants.SLING_HTTP_SERVLET_RESPONSE) && MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree)) {
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_RESPONSE_MESSAGE);
        }
        super.visitMethodInvocation(methodInvocationTree);
    }
}
