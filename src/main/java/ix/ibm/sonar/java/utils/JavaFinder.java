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

import org.apache.commons.lang3.StringUtils;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.Objects;
import java.util.Optional;

public final class JavaFinder {

    public static boolean containsAnnotation(final MethodTree methodTree, final String annotationFullyQualifiedName) {
        boolean annotationFound = false;
        for (final AnnotationTree annotationTree : methodTree.modifiers().annotations()) {
            final Type annotationType = annotationTree.annotationType().symbolType();
            if (annotationType.is(annotationFullyQualifiedName)) {
                annotationFound = true;
            }
        }
        return annotationFound;
    }

    public static Optional<String> getEnclosingClassName(final Symbol symbol) {
        final Symbol.TypeSymbol typeSymbol = symbol.enclosingClass();
        if (Objects.nonNull(typeSymbol)) {
            return Optional.of(typeSymbol.type().fullyQualifiedName());
        } else {
            return Optional.empty();
        }
    }

    public static boolean anyOfTheExceptionsBeingCaught(final CatchTree catchTree, final String... exceptionFullyQualifiedNames) {
        boolean exceptionMatchFound = false;
        final TypeTree parameterTypeTree = catchTree.parameter().type();
        if (parameterTypeTree.is(Tree.Kind.UNION_TYPE)) {
            final UnionTypeTree unionTypeTree = (UnionTypeTree) parameterTypeTree;
            if ((unionTypeTree.typeAlternatives().stream().anyMatch(typeTree -> typeTreeContainsAnyOfTheExceptions(typeTree, exceptionFullyQualifiedNames)))) {
                exceptionMatchFound = true;
            }
        } else if (typeTreeContainsAnyOfTheExceptions(parameterTypeTree, exceptionFullyQualifiedNames)) {
            exceptionMatchFound = true;
        }
        return exceptionMatchFound;
    }

    public static boolean typeTreeContainsAnyOfTheExceptions(final TypeTree typeTree, final String... exceptionFullyQualifiedNames) {
        final String typeFullyQualifiedName = typeTree.symbolType().fullyQualifiedName();
        return StringUtils.equalsAny(typeFullyQualifiedName, exceptionFullyQualifiedNames);
    }

    private JavaFinder() {
    }

}
