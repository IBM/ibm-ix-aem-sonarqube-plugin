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

package ix.ibm.sonar.java.visitors;

import ix.ibm.sonar.java.utils.PackageConstants;
import lombok.Getter;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ix.ibm.sonar.java.utils.PackageConstants.EXTEND_WITH_ANNOTATION;

public class AemTestClassFinder extends BaseTreeVisitor {

    @Getter
    private final List<ClassTree> aemTestClasses = new ArrayList<>();

    public static List<ClassTree> getAemTestClasses(final Tree tree) {
        final AemTestClassFinder finder = new AemTestClassFinder();
        tree.accept(finder);
        return finder.getAemTestClasses();
    }

    @Override
    public void visitClass(final ClassTree classTree) {
        if (this.checkForAemTestAnnotation(classTree.symbol().type())) {
            this.aemTestClasses.add(classTree);
        }
        super.visitClass(classTree);
    }

    boolean checkForAemTestAnnotation(final Type type) {
        if (type == null) return false;

        boolean foundAemTestAnnotation = false;
        final List<SymbolMetadata.AnnotationValue> annotationFields = type.symbol().metadata().valuesForAnnotation(EXTEND_WITH_ANNOTATION);
        if (annotationFields != null) {
            final Optional<SymbolMetadata.AnnotationValue> annotationValues = annotationFields.stream()
                    .filter(annotationValue -> "value".equals(annotationValue.name()))
                    .findFirst();
            if (annotationValues.isPresent()) {
                foundAemTestAnnotation = Stream.of((Object[]) annotationValues.get().value())
                        .anyMatch(symbol -> ((Symbol) symbol).type().fullyQualifiedName().equals(PackageConstants.AEM_CONTEXT_EXTENSION));
            }
        }

        if (!foundAemTestAnnotation) {
            foundAemTestAnnotation = this.checkForAemTestAnnotation(type.symbol().superClass());
        }

        return foundAemTestAnnotation;
    }
}
