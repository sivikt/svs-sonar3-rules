package svs.sonar.plugins.java.checks;

import org.junit.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

public class AvoidLocalDiskIOCheckTest extends BaseCheckTest {

    @Rule
    public final CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Override
    protected JavaFileScanner check() {
        return new AvoidLocalDiskIOCheck();
    }

    @Override
    protected String testFile() {
        return "src/test/files/TestAvoidLocalDiskIOCheck.java";
    }

    @Override
    protected void checkAnalysisResults(SourceFile file) {
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(17).withMessage("Avoid instantiation of this class")
                .next().atLine(20).withMessage("Avoid instantiation of this class")
                .next().atLine(20).withMessage("Avoid using arguments of this class")
                .next().atLine(23).withMessage("Avoid calling methods on this class")
                .next().atLine(26).withMessage("Avoid calling methods with this argument")
                .next().atLine(30).withMessage("Avoid calling methods on this class")
                .next().atLine(30).withMessage("Avoid instantiation of this class")
                .next().atLine(30).withMessage("Avoid using arguments of this class");
    }

}
