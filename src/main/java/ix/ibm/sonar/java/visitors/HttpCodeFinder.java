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

import lombok.Getter;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayList;
import java.util.List;

import static ix.ibm.sonar.java.utils.PackageConstants.SLING_HTTP_SERVLET_RESPONSE;

public class HttpCodeFinder extends BaseTreeVisitor {

    private static final String SET_STATUS = "setStatus";
    private boolean isSetHttpSlingResponseStatus = false;

    @Getter
    private final List<LiteralTree> httpCodeLiterals = new ArrayList<>();

    @Override
    public void visitMethodInvocation(final MethodInvocationTree tree) {
        final String methodName = tree.symbol().name();
        String expressionFqn = StringUtils.EMPTY;
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            expressionFqn = ((MemberSelectExpressionTree) tree.methodSelect()).expression().symbolType().fullyQualifiedName();
        }

        this.isSetHttpSlingResponseStatus = StringUtils.equals(SET_STATUS, methodName) && StringUtils.equals(
          SLING_HTTP_SERVLET_RESPONSE, expressionFqn);
        super.visitMethodInvocation(tree);
        this.isSetHttpSlingResponseStatus = false;
    }

    @Override
    public void visitLiteral(final LiteralTree literalTree) {
        final int literalNumber = NumberUtils.toInt(literalTree.value(), 0);

        if (this.isSetHttpSlingResponseStatus && literalNumber >= 100 && literalNumber < 600) {
            this.httpCodeLiterals.add(literalTree);
        }
        super.visitLiteral(literalTree);
    }

}
