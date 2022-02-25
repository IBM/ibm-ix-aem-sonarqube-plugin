package ix.ibm.sonar.java.checks.sling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.java.checks.verifier.FilesUtils;

import ix.ibm.sonar.java.checks.TestConstants;

class ThreadSafeObjectsTest {

    @Test
    @DisplayName("GIVEN a class, WHEN using a not thread safe member variable, THEN the test should verify that this is not compliant")
    void detected() {
        CheckVerifier
          .newVerifier()
          .onFile(TestConstants.SLING_TEST_PATH + "ThreadSafeObjectsRuleCheck.java")
          .withCheck(new ThreadSafeObjectsRule())
          .withClassPath(FilesUtils.getClassPath(TestConstants.TEST_JARS_PATH))
          .verifyIssues();
    }

}

