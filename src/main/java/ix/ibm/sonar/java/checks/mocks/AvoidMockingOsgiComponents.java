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

@Rule(key = "AvoidMockingOsgiComponents")
public class AvoidMockingOsgiComponents extends AemTestClassTreeVisitor {

    private static final String AVOID_MOCKING_OSGI_COMPONENT = "Avoid mocking OSGi component implementations, create a stub implementation or mock the interface instead";

    @Override
    public void visitVariable(final VariableTree variableTree) {
        if (MockChecker.isBeingMockedViaAnnotation(variableTree) && this.isOsgiComponent(variableTree)) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_OSGI_COMPONENT);
        }
        super.visitVariable(variableTree);
    }

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        if (MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree) && this.isOsgiComponent(methodInvocationTree)){
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_OSGI_COMPONENT);
        }
        super.visitMethodInvocation(methodInvocationTree);
    }

    private boolean isOsgiComponent(final VariableTree variableTree) {
        return this.isOsgiComponent(variableTree.symbol().type());
    }

    private boolean isOsgiComponent(final MethodInvocationTree methodInvocationTree) {
        return this.isOsgiComponent(methodInvocationTree.symbolType());
    }

    private boolean isOsgiComponent(final Type type) {
        return type.symbol().metadata().isAnnotatedWith(PackageConstants.OSGI_COMPONENT_ANNOTATION);
    }
}
