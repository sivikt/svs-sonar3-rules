package svs.sonar.plugins.java.checks;

import org.junit.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

public class AvoidHttpSessionAttributesCheckTest extends BaseCheckTest {

    @Rule
    public final CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Override
    protected JavaFileScanner check() {
        return new AvoidHttpSessionAttributesCheck();
    }

    @Override
    protected String testFile() {
        return "src/test/files/TestAvoidHttpSessionAttributesCheck.java";
    }

    @Override
    protected void checkAnalysisResults(SourceFile file) {
        String expectedMessage = "Avoid saving attributes into HTTP Session";
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(14).withMessage(expectedMessage)
                .next().atLine(15).withMessage(expectedMessage)
                .next().atLine(16).withMessage(expectedMessage);
    }

}
