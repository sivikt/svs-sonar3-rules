package svs.sonar.plugins.java.checks;

import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.api.SourceFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseCheckTest {

    /**
     * JAR dependencies for classpath execution
     */
    public static final List<File> CLASSPATH_JAR;

    static {
        CLASSPATH_JAR = new ArrayList<>();
        for (String jar : System.getProperty("java.class.path").split(File.pathSeparator)) {
            if (jar.endsWith(".jar"))
                CLASSPATH_JAR.add(new File(jar));
        }
    }

    @Test
    public void detected() {
        SourceFile file = JavaAstScanner.scanSingleFile(
                new File(testFile()),
                new VisitorsBridge(check(), CLASSPATH_JAR)
        );

        checkAnalysisResults(file);
    }

    protected abstract JavaFileScanner check();

    protected abstract String testFile();

    protected abstract void checkAnalysisResults(SourceFile file);

}
