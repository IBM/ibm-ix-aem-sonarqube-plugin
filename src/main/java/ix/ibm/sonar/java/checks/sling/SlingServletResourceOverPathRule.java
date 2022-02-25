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

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;

import ix.ibm.sonar.java.utils.AemRecognizer;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "SlingServletResourceOverPath")
public class SlingServletResourceOverPathRule extends ExpandedTreeVisitor {

    private static final String WARNING_USAGE_OF_SERVLET_PATHS_ANNOTATION = "Prefer using @SlingServletResourceTypes over @SlingServletPaths";

    @Override
    public void visitClass(final ClassTree tree) {
        if (AemRecognizer.isSlingServlet(tree)) {
            boolean servletPathAnnotationFound = false;
            for (final AnnotationTree annotationTree : tree.modifiers().annotations()) {
                final Type annotationType = annotationTree.annotationType().symbolType();
                if (annotationType.is(PackageConstants.SLING_SERVLET_PATH_BIND_TYPE)) {
                    servletPathAnnotationFound = true;
                }
            }
            if (servletPathAnnotationFound) {
                this.context.reportIssue(this, tree.simpleName(), WARNING_USAGE_OF_SERVLET_PATHS_ANNOTATION);
            }
        }
        super.visitClass(tree);
    }

}

