package svs.sonar.plugins.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.CheckRegistrar;
import svs.sonar.plugins.java.checks.CheckList;

/**
 * Provide the "checks" (implementations of rules) classes
 * that are gonna be executed during source code analysis.
 *
 * Batch extension -> objects are instantiated during code analysis.
 *
 * @author Serj Sintsov, 2015
 */
public class SVSJavaPluginChecksRegistrar implements CheckRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(SVSJavaPluginChecksRegistrar.class);

    /**
     * Register the classes that will be used to instantiate
     * checks during analysis.
     */
    @Override
    public void register(RegistrarContext registrarContext) {
        logger.info("Register SVS Checks Batch Extension");
        registrarContext.registerClassesForRepository(CheckList.REPOSITORY_KEY, CheckList.getChecks());
    }

}
