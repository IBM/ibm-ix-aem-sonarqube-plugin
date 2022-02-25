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

import java.util.Objects;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;

import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "AvoidWcmUsePojoClass")
public class AvoidWcmUsePojoClassRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "Avoid using WCMUsePojo class, Sling models are preferred";

    @Override
    public void visitClass(final ClassTree tree) {
        if (Objects.nonNull(tree.superClass()) && tree.superClass().symbolType().is(PackageConstants.WCM_USE_POJO)) {
            this.context.reportIssue(this, tree.superClass(), WARNING_MESSAGE);
        }
        super.visitClass(tree);
    }

}

