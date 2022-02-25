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

package ix.ibm.sonar.java.visitors;

import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import ix.ibm.sonar.java.utils.JavaFinder;
import ix.ibm.sonar.java.utils.PackageConstants;
import lombok.Getter;

public class ResponseStatusFinder extends BaseTreeVisitor {

    private static final String SET_RESPONSE_STATUS_METHOD_NAME = "setStatus";
    private static final String SEND_ERROR_STATUS_METHOD_NAME = "sendError";

    @Getter
    private boolean isResponseStatusSet = false;

    public static boolean isResponseStatusMissing(final Tree tree) {
        final ResponseStatusFinder responseStatusFinder = new ResponseStatusFinder();
        tree.accept(responseStatusFinder);

        return !responseStatusFinder.isResponseStatusSet();
    }

    @Override
    public void visitMethodInvocation(final MethodInvocationTree methodInvocationTree) {
        if (methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
            if (memberSelectExpressionTree.expression().is(Tree.Kind.IDENTIFIER)) {
                final IdentifierTree baseIdentifierTree = (IdentifierTree) memberSelectExpressionTree.expression();
                final IdentifierTree callingIdentifierTree = memberSelectExpressionTree.identifier();

                final String baseVariablePackage = baseIdentifierTree.symbolType().fullyQualifiedName();
                final String callingMethodPackage = JavaFinder.getEnclosingClassName(callingIdentifierTree.symbol()).orElse(StringUtils.EMPTY);
                final String callingMethodName = callingIdentifierTree.identifierToken().text();

                if (StringUtils.equals(baseVariablePackage, PackageConstants.SLING_HTTP_SERVLET_RESPONSE) && StringUtils.equals(
                  callingMethodPackage, PackageConstants.JAVAX_HTTP_SERVLET_RESPONSE) && (StringUtils.equals(
                  callingMethodName, SET_RESPONSE_STATUS_METHOD_NAME) || StringUtils.equals(SEND_ERROR_STATUS_METHOD_NAME, callingMethodName))) {
                    this.isResponseStatusSet = true;
                }
            }
        }
        super.visitMethodInvocation(methodInvocationTree);
    }

}
