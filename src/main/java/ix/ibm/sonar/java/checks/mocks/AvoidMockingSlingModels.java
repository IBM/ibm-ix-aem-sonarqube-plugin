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


@Rule(key = "AvoidMockingSlingModels")
public class AvoidMockingSlingModels extends AemTestClassTreeVisitor {

    private static final String AVOID_MOCKING_SLING_MODELS = "Avoid mocking Sling models, adapt it from a context resource/request instead";

    @Override
    public void visitVariable(final VariableTree variableTree) {
        if (MockChecker.isBeingMockedViaAnnotation(variableTree) && this.isSlingModel(variableTree.symbol().type())) {
            this.context.reportIssue(this, variableTree.simpleName(), AVOID_MOCKING_SLING_MODELS);
        }
        super.visitVariable(variableTree);
    }

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        if (MockChecker.isBeingMockedThroughMethodCall(methodInvocationTree) && this.isSlingModel(methodInvocationTree.symbolType())) {
            this.context.reportIssue(this, methodInvocationTree, AVOID_MOCKING_SLING_MODELS);
        }
        super.visitMethodInvocation(methodInvocationTree);
    }

    private boolean isSlingModel(Type type) {
        boolean isSlingModel = false;
        while (type != null){
            if (type.symbol().metadata().isAnnotatedWith(PackageConstants.SLING_MODEL_ANNOTATION)){
                isSlingModel = true;
                break;
            }
            type = type.symbol().superClass();
        }
        return isSlingModel;
    }
}
