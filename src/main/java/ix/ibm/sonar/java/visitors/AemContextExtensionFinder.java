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
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;

public class AemContextExtensionFinder extends BaseTreeVisitor {

    @Getter
    private boolean foundAnnotation = false;

    @Override
    public void visitIdentifier(final IdentifierTree identifierTree) {
        if (identifierTree.symbolType().is(PackageConstants.AEM_CONTEXT_EXTENSION)) {
            this.foundAnnotation = true;
        }
        super.visitIdentifier(identifierTree);
    }

}
