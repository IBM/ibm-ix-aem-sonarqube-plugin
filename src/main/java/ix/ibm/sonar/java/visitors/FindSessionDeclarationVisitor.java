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

import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Finds all injector variable declarations. Used in method's bodies only.
 */
public class FindSessionDeclarationVisitor extends BaseTreeVisitor {

    private static final String JCR_SESSION = "javax.jcr.Session";

    private static final String SLING_REPOSITORY = "org.apache.sling.jcr.api.SlingRepository";

    private final Set<VariableTree> sessions;

    public FindSessionDeclarationVisitor() {
        this.sessions = new HashSet<>();
    }

    public Collection<VariableTree> getDeclarations() {
        return this.sessions;
    }

    @Override
    public void visitAssignmentExpression(final AssignmentExpressionTree tree) {
        if (this.isMethodInvocation(tree)) {
            final MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
            if (this.isManuallyCreatedSession(methodInvocation)) {
                final IdentifierTree variable = (IdentifierTree) tree.variable();
                this.sessions.add((VariableTree) this.getDeclaration(variable));
            } else if (this.isSession(methodInvocation) && methodInvocation.methodSelect().is(Kind.IDENTIFIER)) {
                this.findSessionsCreatedInMethods(tree, methodInvocation);
            }
        }
        super.visitAssignmentExpression(tree);
    }

    private void findSessionsCreatedInMethods(final AssignmentExpressionTree tree, final MethodInvocationTree methodInvocation) {
        final MethodTree methodDeclaration = this.getMethodTree(methodInvocation);
        if (this.isManuallyCreatedSession(methodDeclaration)) {
            IdentifierTree identifierTree = null;
            if (tree.variable().is(Kind.IDENTIFIER)) {
                identifierTree = (IdentifierTree) tree.variable();
            } else if (tree.variable().is(Kind.MEMBER_SELECT)) {
                identifierTree = ((MemberSelectExpressionTree) tree.variable()).identifier();
            }
            if (Objects.nonNull(identifierTree)) {
                this.sessions.add((VariableTree) this.getDeclaration(identifierTree));
            }
        }
    }

    private boolean isManuallyCreatedSession(final MethodTree methodDeclaration) {
        final CheckIfSessionCreatedManually sessionCreatedManually = new CheckIfSessionCreatedManually();
        methodDeclaration.accept(sessionCreatedManually);
        return sessionCreatedManually.isCreatedManually();
    }

    private MethodTree getMethodTree(final MethodInvocationTree methodInvocation) {
        final IdentifierTree method = (IdentifierTree) methodInvocation.methodSelect();
        return (MethodTree) this.getDeclaration(method);
    }

    private Tree getDeclaration(final IdentifierTree variable) {
        return variable.symbol().declaration();
    }

    private boolean isManuallyCreatedSession(final MethodInvocationTree methodInvocation) {
        return this.isSession(methodInvocation) && this.isSlingRepository(methodInvocation);
    }

    private boolean isMethodInvocation(final AssignmentExpressionTree tree) {
        return tree.expression().is(Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitReturnStatement(final ReturnStatementTree tree) {
        if (Objects.nonNull(tree.expression()) && tree.expression().is(Kind.IDENTIFIER)) {
            final IdentifierTree identifier = (IdentifierTree) tree.expression();
            final Tree declaration = this.getDeclaration(identifier);
            this.sessions.remove(declaration);
        }
        super.visitReturnStatement(tree);
    }

    private boolean isSlingRepository(final MethodInvocationTree methodInvocation) {
        return methodInvocation.methodSymbol().owner().type().fullyQualifiedName().equals(SLING_REPOSITORY);
    }

    private boolean isSession(final MethodInvocationTree methodInvocation) {
        return methodInvocation.symbolType().fullyQualifiedName().equals(JCR_SESSION);
    }

    @Override
    public void visitTryStatement(final TryStatementTree tree) {
        // omit resources
        this.scan(tree.block());
        this.scan(tree.catches());
        this.scan(tree.finallyBlock());
    }

    private class CheckIfSessionCreatedManually extends BaseTreeVisitor {

        private Tree declarationOfReturnedVariable;

        private boolean createdManually;

        @Override
        public void visitMethod(final MethodTree tree) {
            final FindDeclarationOfReturnedVariable visitor = new FindDeclarationOfReturnedVariable();
            tree.accept(visitor);
            this.declarationOfReturnedVariable = visitor.getDeclarationOfReturnedVariable();
            super.visitMethod(tree);
        }

        @Override
        public void visitAssignmentExpression(final AssignmentExpressionTree tree) {
            if (FindSessionDeclarationVisitor.this.isMethodInvocation(tree) && FindSessionDeclarationVisitor.this.getDeclaration((IdentifierTree) tree.variable()).equals(this.declarationOfReturnedVariable)) {
                final MethodInvocationTree methodInvocation = (MethodInvocationTree) tree.expression();
                if (FindSessionDeclarationVisitor.this.isManuallyCreatedSession(methodInvocation)) {
                    this.createdManually = true;
                } else {
                    final CheckIfSessionCreatedManually sessionCreatedManually = new CheckIfSessionCreatedManually();
                    FindSessionDeclarationVisitor.this.getMethodTree(methodInvocation).accept(sessionCreatedManually);
                    this.createdManually = sessionCreatedManually.isCreatedManually();
                }
            }
            super.visitAssignmentExpression(tree);
        }

        public boolean isCreatedManually() {
            return this.createdManually;
        }

    }

    private class FindDeclarationOfReturnedVariable extends BaseTreeVisitor {

        private Tree declarationOfReturnedVariable;

        @Override
        public void visitReturnStatement(final ReturnStatementTree tree) {
            if (Objects.nonNull(tree.expression()) && tree.expression().is(Kind.IDENTIFIER)) {
                final IdentifierTree identifier = (IdentifierTree) tree.expression();
                this.declarationOfReturnedVariable = FindSessionDeclarationVisitor.this.getDeclaration(identifier);
            }
            super.visitReturnStatement(tree);
        }

        public Tree getDeclarationOfReturnedVariable() {
            return this.declarationOfReturnedVariable;
        }

    }

}
