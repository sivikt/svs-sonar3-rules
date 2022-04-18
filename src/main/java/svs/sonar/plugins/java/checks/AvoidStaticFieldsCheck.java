package svs.sonar.plugins.java.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Description below.
 *
 * @author Serj Sintsov, 2015
 */
@Rule(key = "AvoidStaticFields",
        name = "AvoidStaticFields",
        description = "Classes must not use 'static' fields. This rule detects usage of 'static' fields",
        tags = {"bug"},
        priority = Priority.CRITICAL)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SOFTWARE_RELATED_PORTABILITY)
@SqaleConstantRemediation("5min")
public class AvoidStaticFieldsCheck extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger logger = LoggerFactory.getLogger(AvoidStaticFieldsCheck.class);

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        logger.trace("Start scanning for 'static' fields");
    }

    @Override
    public void visitVariable(VariableTree tree) {
        logger.trace("START visiting variable and checking modifiers");

        for (Modifier modifier : tree.modifiers().modifiers())
            if (modifier == Modifier.STATIC) {
                context.addIssue(tree, this, "Static fields are forbidden. Class must be stateless");
                break;
            }
        super.visitVariable(tree);

        logger.trace("END visiting variable and checking modifiers");
    }

}
