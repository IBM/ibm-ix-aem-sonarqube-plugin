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

import java.util.Objects;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.TreeVisitor;
import org.sonar.plugins.java.api.tree.TryStatementTree;

/**
 * Visits only finally blocks. Used only in methods.
 */
public class FinallyBlockVisitor extends BaseTreeVisitor {

    private final TreeVisitor visitor;

    public FinallyBlockVisitor(final TreeVisitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public void visitTryStatement(final TryStatementTree tree) {
        if (Objects.nonNull(tree.finallyBlock())) {
            tree.finallyBlock().accept(this.visitor);
        }
        super.visitTryStatement(tree);
    }
}
