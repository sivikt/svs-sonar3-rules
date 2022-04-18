package svs.sonar.plugins.java.checks;

import org.junit.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

public class AvoidStaticFieldsCheckTest extends BaseCheckTest{

    @Rule
    public final CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Override
    protected JavaFileScanner check() {
        return new AvoidStaticFieldsCheck();
    }

    @Override
    protected String testFile() {
        return "src/test/files/TestAvoidStaticFieldsCheck.java";
    }

    @Override
    protected void checkAnalysisResults(SourceFile file) {
        String expectedMessage = "Static fields are forbidden. Class must be stateless";
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(8).withMessage(expectedMessage)
                .next().atLine(9).withMessage(expectedMessage);
    }

}
