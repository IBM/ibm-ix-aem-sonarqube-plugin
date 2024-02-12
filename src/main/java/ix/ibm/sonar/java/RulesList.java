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

import ix.ibm.sonar.java.checks.UseConstantsForLiteralsRule;
import ix.ibm.sonar.java.checks.mocks.*;
import ix.ibm.sonar.java.checks.osgi.AvoidFelixAnnotationsRule;
import ix.ibm.sonar.java.checks.osgi.AvoidJcrApiRule;
import ix.ibm.sonar.java.checks.osgi.SeparateConfigurationClassRule;
import ix.ibm.sonar.java.checks.sling.*;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RulesList {

    private RulesList() {
    }

    public static List<Class<? extends JavaCheck>> getChecks() {
        final List<Class<? extends JavaCheck>> checks = new ArrayList<>();
        checks.addAll(getJavaChecks());
        checks.addAll(getJavaTestChecks());
        return Collections.unmodifiableList(checks);
    }

    static List<Class<? extends JavaCheck>> getJavaChecks() {
        return List.of(DefaultInjectionStrategyRule.class, AvoidWcmUsePojoClassRule.class, UseInjectorSpecificAnnotationsRule.class,
                AvoidFelixAnnotationsRule.class, AvoidJcrApiRule.class, AvoidOldSlingServletAnnotationsRule.class, SlingServletResourceOverPathRule.class,
                UseConstantsForHttpCodesRule.class, SeparateConfigurationClassRule.class, PreferInjectionOverAdaptionRule.class,
                PostConstructExceptionRule.class, SlingServletExceptionRule.class, FilterExceptionRule.class, TryWithResourcesResourceResolverRule.class,
                CloseJcrSessionRule.class, AvoidDeprecatedAdministrativeRule.class, ThreadSafeObjectsRule.class, UseConstantsForLiteralsRule.class);
    }

    static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return List.of(AvoidInjectMocksUsage.class, AvoidMockingRequestAndResponse.class, AvoidMockingOsgiComponents.class, AvoidMockingSlingModels.class,
                AvoidMockingResourceHandlingObjects.class);
    }

}
