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
 * Modifications:
 * Code changes to align with Java 11 coding standards.
 *
 */

package ix.ibm.sonar.java.checks.sling;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;

import ix.ibm.sonar.java.visitors.CheckLoggedOutVisitor;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import ix.ibm.sonar.java.visitors.FinallyBlockVisitor;
import ix.ibm.sonar.java.visitors.FindSessionDeclarationVisitor;

@Rule(key = "CloseJcrSession")
public class CloseJcrSessionRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "A JCR session should always be closed in a finally-block.";

    private static final String ACTIVATE = "Activate";

    private static final String DEACTIVATE = "Deactivate";

    private Collection<VariableTree> longSessions;

    @Override
    public void visitMethod(final MethodTree method) {
        if (!this.checkIfLongSession(method)) {
            final Collection<VariableTree> sessions = this.findSessionsInMethod(method);
            for (final VariableTree session : sessions) {
                if (!this.checkIfLoggedOut(method, session)) {
                    this.context.reportIssue(this, session, WARNING_MESSAGE);
                }
            }
        }
        super.visitMethod(method);
    }

    private boolean checkIfLongSession(final MethodTree method) {
        final List<AnnotationTree> annotations = method.modifiers().annotations();
        for (final AnnotationTree annotationTree : annotations) {
            if (annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)) {
                final IdentifierTree idf = (IdentifierTree) annotationTree.annotationType();
                if (idf.name().equals(ACTIVATE)) {
                    this.collectLongSessionOpened(method);
                    return true;
                } else if (idf.name().equals(DEACTIVATE)) {
                    this.collectLongSessionClosed(method);
                    return true;
                }
            }
        }
        return false;
    }

    private void collectLongSessionOpened(final MethodTree method) {
        this.longSessions = this.findSessionsInMethod(method);
    }

    private void collectLongSessionClosed(final MethodTree method) {
        if (Objects.nonNull(this.longSessions)) {
            for (final VariableTree longSession : this.longSessions) {
                if (!this.checkIfLoggedOut(method, longSession)) {
                    this.context.reportIssue(this, longSession, WARNING_MESSAGE);
                }
            }
        }
    }

    private boolean checkIfLoggedOut(final MethodTree method, final VariableTree injector) {
        final Set<IdentifierTree> usagesOfSession = new HashSet<>(injector.symbol().usages());
        final CheckLoggedOutVisitor checkLoggedOutVisitor = new CheckLoggedOutVisitor(usagesOfSession);
        final FinallyBlockVisitor finallyBlockVisitor = new FinallyBlockVisitor(checkLoggedOutVisitor);
        method.accept(finallyBlockVisitor);
        return checkLoggedOutVisitor.isLoggedOut();
    }

    private Collection<VariableTree> findSessionsInMethod(final MethodTree methodTree) {
        final FindSessionDeclarationVisitor findSessionDeclarationVisitor = new FindSessionDeclarationVisitor();
        methodTree.accept(findSessionDeclarationVisitor);
        return findSessionDeclarationVisitor.getDeclarations();
    }

}
