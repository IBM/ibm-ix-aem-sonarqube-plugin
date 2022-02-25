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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.IdentifierTree;

import ix.ibm.sonar.java.utils.PackageConstants;

@Rule(key = "PreferInjectionOverAdaption")
public class PreferInjectionOverAdaptionRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String ADAPT_TO_METHOD = "adaptTo";

    private static final Map<String, String> WARNINGS = Map.ofEntries(Map.entry(
        PackageConstants.PAGE_MANAGER, "Inject PageManagerFactory and use it to get PageManger instead of adapting it from resource resolver"),
      Map.entry(PackageConstants.SLING_VALUE_MAP, "Use getValueMap method on a resource to retrieve the ValueMap instead of adapting it"),
      Map.entry(PackageConstants.ASSET, "Use DamUtil.resolveToAsset method to retrieve the Asset instead of adapting it from resource"),
      Map.entry(PackageConstants.EXTERNALIZER, "Inject the Externalizer object instead of adapting it from resource resolver"),
      Map.entry(PackageConstants.QUERY_BUILDER, "Inject the Query builder object instead of adapting it from resource resolver"));


    private JavaFileScannerContext context;

    @Override
    public void scanFile(final JavaFileScannerContext context) {
        this.context = context;
        this.scan(context.getTree());
    }

    @Override
    public void visitIdentifier(final IdentifierTree identifierTree) {
        if (this.isAdaptToMethod(identifierTree)) {
            final String packageName = identifierTree.symbolType().fullyQualifiedName();
            final String warningMessage = WARNINGS.get(packageName);
            if (StringUtils.isNotBlank(warningMessage)) {
                this.context.reportIssue(this, identifierTree, warningMessage);
            }
        }
        super.visitIdentifier(identifierTree);
    }

    private boolean isAdaptToMethod(final IdentifierTree identifierTree) {
        final Symbol.TypeSymbol typeSymbol = identifierTree.symbol().enclosingClass();
        return typeSymbol != null && typeSymbol.type().is(PackageConstants.SLING_ADAPTABLE) && StringUtils.equals(
          identifierTree.identifierToken().text(), ADAPT_TO_METHOD);
    }

}

