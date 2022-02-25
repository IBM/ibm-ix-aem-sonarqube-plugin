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

package ix.ibm.sonar.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

/**
 * Declare rule metadata in server repository of rules.
 * That allows to list the rules in the page "Rules".
 */
public class MyJavaRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "ibmix-aem";
    public static final String REPOSITORY_NAME = "IBM iX AEM rules";

    // don't change that because the path is hard coded in CheckVerifier
    private static final String RESOURCE_BASE_PATH = "ix/ibm/sonar/l10n/java/rules/squid";

    // Add the rule keys of the rules which need to be considered as template-rules
    private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "java").setName(REPOSITORY_NAME);

        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH);

        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(RulesList.getChecks()));

        setTemplates(repository);

        repository.done();
    }

    private static void setTemplates(NewRepository repository) {
        RULE_TEMPLATES_KEY.stream().map(repository::rule).filter(Objects::nonNull).forEach(rule -> rule.setTemplate(true));
    }

}