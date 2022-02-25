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

import java.util.Optional;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import ix.ibm.sonar.java.utils.Constants;
import lombok.Getter;

public class FilterMethodFinder extends BaseTreeVisitor {

    @Getter
    private MethodTree doFilterMethodTree;

    public static Optional<MethodTree> getDoFilterMethodTree(final Tree tree) {
        final FilterMethodFinder filterMethodFinder = new FilterMethodFinder();
        tree.accept(filterMethodFinder);

        return Optional.ofNullable(filterMethodFinder.getDoFilterMethodTree());
    }

    @Override
    public void visitMethod(final MethodTree methodTree) {
        if (StringUtils.equals(methodTree.simpleName().name(), Constants.DO_FILTER_METHOD_NAME) && BooleanUtils.isTrue(methodTree.isOverriding())) {
            this.doFilterMethodTree = methodTree;
        }
        super.visitMethod(methodTree);
    }

}
