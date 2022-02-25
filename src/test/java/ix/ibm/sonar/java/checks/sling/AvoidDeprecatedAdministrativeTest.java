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
 * Code changes to align with Java 11 coding standards and usage of JUnit5.
 *
 */

package ix.ibm.sonar.java.checks.sling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

import ix.ibm.sonar.java.checks.TestConstants;

class AvoidDeprecatedAdministrativeTest {

    @Test
    @DisplayName("GIVEN a method, WHEN the method contains the deprecated method, THEN the test should verify that this is not compliant")
    void detected() {
        CheckVerifier
          .newVerifier()
          .onFile(TestConstants.SLING_TEST_PATH + "AvoidDeprecatedAdministrativeCheck.java")
          .withCheck(new AvoidDeprecatedAdministrativeRule())
          .withClassPath(FilesUtils.getClassPath(TestConstants.TEST_JARS_PATH))
          .verifyIssues();
    }

}
