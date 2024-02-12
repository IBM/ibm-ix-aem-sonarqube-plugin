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

@Rule(key = "AvoidMockingResourceHandlingObjects")
public class AvoidMockingResourceHandlingObjects extends AemTestClassTreeVisitor {

    private static final String AVOID_MOCKING_RESOURCE_RESOLVER = "Avoid mocking ResourceResolver, use the one provided in the context. If you need extended functionality, look into different mock resource resolver implementations";
    private static final String AVOID_MOCKING_SESSION = "Avoid mocking the Session object, adapt it from the ResourceResolver instead (look into different mock resource resolver implementations)";

    @Override
    public void visitVariable(final VariableTree variableTree) {
        final Type identifierType = variableTree.type().symbolType();
        if (identifierType.is(PackageConstants.SLING_RESOURCE_RESOLVER) && MockChecker.isBeingMockedViaAnnotation(variableTree)) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_RESOURCE_RESOLVER);
        }
        if (identifierType.is(PackageConstants.JCR_SESSION) && MockChecker.isBeingMockedViaAnnotation(variableTree)) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_SESSION);
        }
        super.visitVariable(variableTree);
    }

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        final Type returnType = methodInvocationTree.symbolType();
        if (returnType.is(PackageConstants.SLING_RESOURCE_RESOLVER) && MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree)) {
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_RESOURCE_RESOLVER);
        }
        if (returnType.is(PackageConstants.JCR_SESSION) && MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree)) {
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_SESSION);
        }
        super.visitMethodInvocation(methodInvocationTree);
    }
}
