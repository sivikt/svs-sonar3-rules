package svs.sonar.plugins.java.checks;

import org.junit.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

public class AvoidCookiesCheckTest extends BaseCheckTest {

    @Rule
    public final CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Override
    protected JavaFileScanner check() {
        return new AvoidCookiesCheck();
    }

    @Override
    protected String testFile() {
        return "src/test/files/TestAvoidCookiesCheck.java";
    }

    @Override
    protected void checkAnalysisResults(SourceFile file) {
        String expectedMessage = "Avoid sending cookies to client";
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(13).withMessage(expectedMessage);
    }

}
