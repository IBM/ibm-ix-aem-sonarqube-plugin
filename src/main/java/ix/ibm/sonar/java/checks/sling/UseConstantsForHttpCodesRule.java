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

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.LiteralTree;

import ix.ibm.sonar.java.utils.AemRecognizer;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import ix.ibm.sonar.java.visitors.HttpCodeFinder;

@Rule(key = "UseConstantsForHttpCodes")
public class UseConstantsForHttpCodesRule extends ExpandedTreeVisitor {

    private static final String WARNING_USE_CONSTANTS_FOR_HTTP_CODES = "Use HttpServletResponse class constants for Http response codes";

    @Override
    public void visitClass(final ClassTree tree) {
        if (AemRecognizer.isSlingServlet(tree)) {
            final HttpCodeFinder httpCodeFinder = new HttpCodeFinder();
            tree.accept(httpCodeFinder);

            final List<LiteralTree> httpCodeLiterals = httpCodeFinder.getHttpCodeLiterals();
            for (final LiteralTree httpCodeLiteral : httpCodeLiterals) {
                this.context.reportIssue(this, httpCodeLiteral, WARNING_USE_CONSTANTS_FOR_HTTP_CODES);
            }

        }
        super.visitClass(tree);
    }

}

