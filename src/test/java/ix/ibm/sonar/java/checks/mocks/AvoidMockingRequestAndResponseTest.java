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

package ix.ibm.sonar.java.checks.mocks;

import ix.ibm.sonar.java.checks.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

class AvoidMockingRequestAndResponseTest {

    @Test
    @DisplayName("GIVEN a test class, WHEN the request or response objects are being mocked using Mockito, THEN the test should verify that this is not compliant")
    void detected() {
        CheckVerifier
          .newVerifier()
          .onFile(TestConstants.MOCKS_TEST_PATH + "AvoidMockingRequestAndResponseCheck.java")
          .withCheck(new AvoidMockingRequestAndResponse())
          .withClassPath(FilesUtils.getClassPath(TestConstants.TEST_JARS_PATH))
          .verifyIssues();
    }

}
