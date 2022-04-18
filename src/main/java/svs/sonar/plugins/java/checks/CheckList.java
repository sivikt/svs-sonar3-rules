package svs.sonar.plugins.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.List;

/**
 * Unite all available checks.
 *
 * @author Serj Sintsov, 2015
 */
public class CheckList {

    public static final String JAVA_LANG = "java";

    public static final String REPOSITORY_KEY = "svs-java-rules";

    public static List<Class<? extends JavaCheck>> getChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                .addAll(getJavaChecks())
                .build();
    }

    private static List<Class<? extends JavaCheck>> getJavaChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                .add(AvoidStaticFieldsCheck.class)
                .add(AvoidLocalDiskIOCheck.class)
                .add(AvoidHttpSessionAttributesCheck.class)
                .add(AvoidCookiesCheck.class)
                .build();
    }

}
