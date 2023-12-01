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

import ix.ibm.sonar.java.utils.JavaFinder;
import ix.ibm.sonar.java.utils.PackageConstants;
import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;
import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "AvoidJcrApi")
public class AvoidJcrApiRule extends ExpandedTreeVisitor {

    private static final String WARNING_MESSAGE = "Using JCR Api is discouraged, Sling API is preferred";

    @Override
    public void visitMethodInvocation(final MethodInvocationTree tree) {
        final String fullyQualifiedName = JavaFinder.getEnclosingClassName(tree.methodSymbol()).orElse(StringUtils.EMPTY);

        if (StringUtils.equals(fullyQualifiedName, PackageConstants.JCR_NODE) || StringUtils.equals(
          fullyQualifiedName, PackageConstants.JCR_SESSION)) {
            this.context.reportIssue(this, tree, WARNING_MESSAGE);
        }

        super.visitMethodInvocation(tree);
    }

    @Override
    public void visitIdentifier(final IdentifierTree tree) {
        final Type type = tree.symbolType();
        if (type.is(PackageConstants.JCR_UTIL) || type.is(PackageConstants.JCR_UTILS)) {
            this.context.reportIssue(this, tree, WARNING_MESSAGE);
        }
        super.visitIdentifier(tree);
    }

}

