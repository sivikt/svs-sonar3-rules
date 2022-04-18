package svs.sonar.plugins.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.plugins.java.api.JavaCheck;
import svs.sonar.plugins.java.checks.CheckList;

import java.util.List;

/**
 * Declare rule metadata in server repository of rules.
 * That allows to list the rules in the page "Rules".
 *
 * Server extension -> objects are instantiated during server startup.
 *
 * @author Serj Sintsov, 2015
 */
public class SVSJavaPluginRulesDefinition implements RulesDefinition {

    private static final Logger logger = LoggerFactory.getLogger(SVSJavaPluginRulesDefinition.class);

    @Override
    @SuppressWarnings("unchecked")
    public void define(Context context) {
        logger.info("Register SVS Rules");

        NewRepository repo = context.createRepository(CheckList.REPOSITORY_KEY, CheckList.JAVA_LANG);
        repo.setName("SVS Java rules");

        RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
        List<Class<? extends JavaCheck>> checks = CheckList.getChecks();
        annotationLoader.load(repo, checks.toArray(new Class[checks.size()]));
        repo.done();
    }

}
