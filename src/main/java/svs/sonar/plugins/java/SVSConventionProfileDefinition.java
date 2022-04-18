package svs.sonar.plugins.java;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;
import svs.sonar.plugins.java.checks.CheckList;

public class SVSConventionProfileDefinition extends ProfileDefinition {

    private final RuleFinder ruleFinder;

    public SVSConventionProfileDefinition(RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RulesProfile createProfile(ValidationMessages messages) {
        AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
        return annotationBasedProfileBuilder.build(CheckList.REPOSITORY_KEY,
                "SVS Code Convention Rules",
                CheckList.JAVA_LANG,
                (Iterable) CheckList.getChecks(),
                messages);
    }

}
