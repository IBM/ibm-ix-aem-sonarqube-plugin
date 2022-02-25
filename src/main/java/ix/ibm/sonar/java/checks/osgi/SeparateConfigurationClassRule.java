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

package ix.ibm.sonar.java.checks.osgi;

import java.util.Objects;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import ix.ibm.sonar.java.utils.AemRecognizer;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "SeparateConfigurationClass")
public class SeparateConfigurationClassRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "Move the OSGi configuration to a separate class";

    @Override
    public void visitClass(final ClassTree classTree) {
        if (AemRecognizer.isOsgiConfiguration(classTree) && Objects.nonNull(classTree.parent()) && classTree.parent().is(Tree.Kind.CLASS)) {
            this.context.reportIssue(this, classTree.simpleName(), WARNING_MESSAGE);
        }
        super.visitClass(classTree);
    }

}

