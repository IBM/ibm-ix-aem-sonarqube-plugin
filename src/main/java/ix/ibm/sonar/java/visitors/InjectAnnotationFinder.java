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

import java.util.ArrayList;
import java.util.List;

import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

import ix.ibm.sonar.java.utils.PackageConstants;
import lombok.Getter;

public class InjectAnnotationFinder extends BaseTreeVisitor {

    @Getter
    private final List<AnnotationTree> injectAnnotations = new ArrayList<>();

    @Override
    public void visitAnnotation(final AnnotationTree annotationTree) {
        if (annotationTree.annotationType().symbolType().is(PackageConstants.INJECT_ANNOTATION)) {
            this.injectAnnotations.add(annotationTree);
        }
        super.visitAnnotation(annotationTree);
    }

}
