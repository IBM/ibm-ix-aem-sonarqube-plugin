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
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;

import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import ix.ibm.sonar.java.visitors.InjectAnnotationFinder;

@Rule(key = "UseInjectorSpecificAnnotations")
public class UseInjectorSpecificAnnotationsRule extends ExpandedTreeVisitor {

    private static final String WARNING_USE_SPECIFIC_INJECTION = "Use a specific inject annotation instead of the generic one";

    @Override
    public void visitClass(final ClassTree tree) {
        final List<AnnotationTree> annotations = tree.modifiers().annotations();
        for (final AnnotationTree annotationTree : annotations) {
            if (annotationTree.annotationType().symbolType().is(PackageConstants.SLING_MODEL_ANNOTATION)) {
                final InjectAnnotationFinder injectAnnotationFinder = new InjectAnnotationFinder();
                tree.accept(injectAnnotationFinder);

                final List<AnnotationTree> injectAnnotations = injectAnnotationFinder.getInjectAnnotations();
                for (final AnnotationTree injectAnnotation : injectAnnotations) {
                    this.context.reportIssue(this, injectAnnotation, WARNING_USE_SPECIFIC_INJECTION);
                }
            }
        }
        super.visitClass(tree);
    }

}