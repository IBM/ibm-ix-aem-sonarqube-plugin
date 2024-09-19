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

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;

import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ClassUsageFinder;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "DefaultInjectionStrategy")
public class DefaultInjectionStrategyRule extends ExpandedTreeVisitor {

    private static final String WARNING_USE_OPTIONAL_STRATEGY = "It is recommended to use OPTIONAL injection strategy when working with or adapting Request objects in a Sling model";

    private static final String IDENTIFIER_OPTIONAL = "OPTIONAL";

    private static final String ANNOTATION_KEY_ADAPTABLES = "adaptables";
    private static final String ANNOTATION_KEY_INJECTION_STRATEGY = "defaultInjectionStrategy";

    @Override
    public void visitClass(final ClassTree classTree) {
        for (final AnnotationTree annotationTree : classTree.modifiers().annotations()) {
            if (annotationTree.annotationType().symbolType().is(PackageConstants.SLING_MODEL_ANNOTATION)) {
                this.verifySlingModelAnnotation(classTree, annotationTree);
            }
        }
        super.visitClass(classTree);
    }

    private void verifySlingModelAnnotation(final ClassTree classTree, final AnnotationTree annotationTree) {
        Tree defaultInjectionStrategyTree = null;
        boolean hasOptionalDefaultInjection = false;
        boolean usesRequestObject = false;

        for (final ExpressionTree argument : annotationTree.arguments()) {
            if (argument.is(Tree.Kind.ASSIGNMENT)) {
                final AssignmentExpressionTree assignmentTree = (AssignmentExpressionTree) argument;
                final IdentifierTree nameTree = (IdentifierTree) assignmentTree.variable();

                if (StringUtils.equals(nameTree.name(), ANNOTATION_KEY_INJECTION_STRATEGY)) {
                    hasOptionalDefaultInjection = this.checkIfInjectionStrategyIsOptional(assignmentTree);
                    defaultInjectionStrategyTree = assignmentTree;
                } else if (StringUtils.equals(nameTree.name(), ANNOTATION_KEY_ADAPTABLES)) {
                    usesRequestObject = this.checkIfAdaptsServletRequest(assignmentTree);
                }
            }
        }

        usesRequestObject |= ClassUsageFinder.isClassUsed(PackageConstants.SLING_HTTP_SERVLET_REQUEST, classTree);
        if (usesRequestObject && !hasOptionalDefaultInjection) {
            defaultInjectionStrategyTree = defaultInjectionStrategyTree == null ? annotationTree : defaultInjectionStrategyTree;
            this.context.reportIssue(this, defaultInjectionStrategyTree, WARNING_USE_OPTIONAL_STRATEGY);
        }

    }

    private boolean checkIfInjectionStrategyIsOptional(final AssignmentExpressionTree assignmentExpressionTree) {
        if (assignmentExpressionTree.expression().is(Tree.Kind.MEMBER_SELECT)) {
            final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) assignmentExpressionTree.expression();
            return StringUtils.equals(memberSelectExpressionTree.identifier().name(), IDENTIFIER_OPTIONAL);
        }
        return false;
    }

    private boolean checkIfAdaptsServletRequest(final AssignmentExpressionTree assignmentExpressionTree) {
        if (assignmentExpressionTree.expression().is(Tree.Kind.MEMBER_SELECT)) {
            final MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) assignmentExpressionTree.expression();
            return StringUtils.equals(
              memberSelectExpressionTree.expression().symbolType().fullyQualifiedName(), PackageConstants.SLING_HTTP_SERVLET_REQUEST);
        }
        return false;
    }

}

