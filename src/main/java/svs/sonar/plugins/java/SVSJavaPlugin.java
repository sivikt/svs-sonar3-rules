package svs.sonar.plugins.java;

import com.google.common.collect.ImmutableList;
import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

import java.util.List;

/**
 * Entry point of plugin
 *
 * @author Serj Sintsov, 2015
 */
public class SVSJavaPlugin extends SonarPlugin {

    @Override
    public List getExtensions() {
        return ImmutableList.<Class<? extends Extension>>builder()
                .add(SVSJavaPluginRulesDefinition.class)
                .add(SVSJavaPluginChecksRegistrar.class)
                .add(SVSConventionProfileDefinition.class)
                .build();
    }

}
