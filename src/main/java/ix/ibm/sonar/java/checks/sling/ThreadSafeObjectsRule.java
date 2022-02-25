/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Modification copyright (C) 2020-present IBM Corporation
 * Copyright (C) 2015-2019 Wunderman Thompson Technology
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 *
 * Modifications:
 * Code changes to align with Java 11 coding standards.
 */

package ix.ibm.sonar.java.checks.sling;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import ix.ibm.sonar.java.visitors.ExpandedTreeVisitor;

@Rule(key = "ThreadSafeObjects")
public class ThreadSafeObjectsRule extends ExpandedTreeVisitor {

    private static final String RULE_MESSAGE = "Usage of %s as a field is not thread safe.";

    private static final List<String> NOT_THREAD_SAFE_INTERFACES = List.of("javax.servlet.Servlet", "javax.servlet.Filter",
      "org.osgi.service.event.EventHandler");

    private static final List<String> NOT_THREAD_SAFE_CLASSES = Collections.emptyList();

    private static final List<String> NOT_THREAD_SAFE_ANNOTATIONS = List.of("org.osgi.service.component.annotations.Component",
      "org.apache.felix.scr.annotations.sling.SlingServlet", "org.apache.felix.scr.annotations.sling.SlingFilter");

    private static final List<String> NON_THREAD_SAFE_TYPES = List.of("org.apache.sling.api.resource.ResourceResolver", "javax.jcr.Session",
      "com.day.cq.wcm.api.PageManager", "com.day.cq.wcm.api.components.ComponentManager", "com.day.cq.wcm.api.designer.Designer",
      "com.day.cq.dam.api.AssetManager", "com.day.cq.tagging.TagManager", "com.day.cq.security.UserManager",
      "org.apache.jackrabbit.api.security.user.Authorizable", "org.apache.jackrabbit.api.security.user.User",
      "org.apache.jackrabbit.api.security.user.UserManager");

    @Override
    public void visitClass(@Nonnull final ClassTree classTree) {

        final boolean notThreadSafe = this.extendsNotThreadSafeClass(classTree) || this.implementsNotThreadSafeInterface(
          classTree) || this.hasNotThreadSafeAnnotation(classTree);
        if (notThreadSafe) {
            this.checkClassMembers(classTree);
        }
        super.visitClass(classTree);
    }

    private boolean extendsNotThreadSafeClass(final ClassTree classTree) {
        final TypeTree type = classTree.superClass();
        return Objects.nonNull(type) && NOT_THREAD_SAFE_CLASSES.contains(type.symbolType().fullyQualifiedName());
    }

    private void checkClassMembers(final ClassTree classTree) {
        for (final Tree member : classTree.members()) {
            this.checkClassMemberVariables(member);
        }
    }

    private void checkClassMemberVariables(final Tree member) {
        if (member.is(Tree.Kind.VARIABLE)) {
            final String name = ((VariableTree) member).type().symbolType().fullyQualifiedName();
            if (NON_THREAD_SAFE_TYPES.contains(name)) {
                this.context.reportIssue(this, member, String.format(RULE_MESSAGE, name));
            }
        }
    }

    private boolean hasNotThreadSafeAnnotation(final ClassTree classTree) {
        return classTree
                 .modifiers()
                 .annotations()
                 .stream()
                 .anyMatch(tree -> NOT_THREAD_SAFE_ANNOTATIONS.contains(tree.annotationType().symbolType().fullyQualifiedName()));
    }

    private boolean implementsNotThreadSafeInterface(final ClassTree classTree) {
        return classTree.superInterfaces().stream().anyMatch(typeTree -> NOT_THREAD_SAFE_INTERFACES.contains(typeTree.symbolType().fullyQualifiedName()));
    }

}
