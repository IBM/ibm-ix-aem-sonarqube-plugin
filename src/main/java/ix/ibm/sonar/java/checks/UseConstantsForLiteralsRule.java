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
 */
package ix.ibm.sonar.java.checks;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.LiteralTree;

import ix.ibm.sonar.java.utils.LiteralConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "UseConstantsForLiterals")
public class UseConstantsForLiteralsRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "Use available constant %s instead of hardcoded value.";

    @Override
    public void visitLiteral(final LiteralTree tree) {
        final String literal = this.replaceStringQuotations(tree.value());
        final String literalConstantAvailable = LiteralConstants.isLiteralConstantAvailable(literal);
        if (StringUtils.isNotEmpty(literalConstantAvailable)) {
            this.context.reportIssue(this, tree, String.format(WARNING_MESSAGE, literalConstantAvailable));
        }
        super.visitLiteral(tree);
    }

    private String replaceStringQuotations(final String value) {
        return value.replaceAll("(^\")|(\"$)", StringUtils.EMPTY);
    }

}
