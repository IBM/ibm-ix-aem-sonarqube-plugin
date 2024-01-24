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

package ix.ibm.sonar.java.utils;

import org.apache.commons.lang3.StringUtils;
import org.sonar.plugins.java.api.tree.*;

public final class MockChecker {
    private static final String MOCK_METHOD_CALL = "mock";

    public static boolean isBeingMockedThroughMethodCall(final MethodInvocationTree methodInvocationTree) {
        boolean isBeingMocked = false;
        if (methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
            if (memberSelectExpressionTree.expression().is(Tree.Kind.IDENTIFIER)) {
                final IdentifierTree callingIdentifierTree = memberSelectExpressionTree.identifier();
                final String callingMethodPackage = JavaFinder.getEnclosingClassName(callingIdentifierTree.symbol()).orElse(StringUtils.EMPTY);
                final String callingMethodName = callingIdentifierTree.identifierToken().text();

                if (StringUtils.equals(callingMethodPackage, PackageConstants.MOCKITO_PACKAGE) && StringUtils.equals(callingMethodName, MOCK_METHOD_CALL)) {
                    isBeingMocked = true;
                }
            }
        }
        return isBeingMocked;
    }

    public static boolean isBeingMockedViaAnnotation(final VariableTree variableTree) {
        return variableTree.modifiers().stream()
                .filter(modifierTree -> modifierTree.is(Tree.Kind.ANNOTATION))
                .anyMatch(modifierTree -> ((AnnotationTree) modifierTree).symbolType().is(PackageConstants.MOCK_ANNOTATION));
    }
}
