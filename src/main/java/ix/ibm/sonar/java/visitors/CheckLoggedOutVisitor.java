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
 */

package ix.ibm.sonar.java.visitors;

import java.util.Set;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;

/**
 * Checks if variable is being logged out. Used only in finally blocks.
 */
public class CheckLoggedOutVisitor extends BaseTreeVisitor {

    private static final String LOG_OUT_METHOD_NAME = "logout";

    private final Set<IdentifierTree> usages;

    private boolean loggedOut;

    public CheckLoggedOutVisitor(final Set<IdentifierTree> usages) {
        this.usages = usages;
        this.loggedOut = false;
    }

    public boolean isLoggedOut() {
        return this.loggedOut;
    }

    @Override
    public void visitMemberSelectExpression(final MemberSelectExpressionTree tree) {
        if (this.usages.contains(tree.expression())) {
            this.loggedOut |= tree.identifier().name().equals(LOG_OUT_METHOD_NAME);
        }
        super.visitMemberSelectExpression(tree);
    }

}
