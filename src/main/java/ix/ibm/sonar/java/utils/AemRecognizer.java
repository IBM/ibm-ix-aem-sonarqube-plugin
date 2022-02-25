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

package ix.ibm.sonar.java.utils;

import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

public final class AemRecognizer {

    public static boolean isOsgiConfiguration(final ClassTree classTree) {
        if (!classTree.is(Tree.Kind.ANNOTATION_TYPE)) {
            return false;
        }
        for (final AnnotationTree annotationTree : classTree.modifiers().annotations()) {
            if (annotationTree.symbolType().is(PackageConstants.OSGI_OBJECT_CLASS_DEFINITION)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSlingServlet(final ClassTree classTree) {
        final Type classType = classTree.symbol().type();
        return isSlingServlet(classType);
    }

    public static boolean isSlingServlet(final Type classType) {
        return classType.isSubtypeOf(PackageConstants.SLING_SERVLET_TYPE_SAFE) || classType.isSubtypeOf(PackageConstants.SLING_SERVLET_TYPE_ALL);
    }

    // Currently checks for any Filter, doesn't explicitly check whether it's the Sling variant
    public static boolean isSlingFilter(final ClassTree classTree) {
        final Type classType = classTree.symbol().type();
        return classType.isSubtypeOf(PackageConstants.JAVAX_SERVLET_FILTER);
    }

    private AemRecognizer() {}

}
