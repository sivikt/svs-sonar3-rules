package svs.sonar.plugins.java.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Description below.
 *
 * @author Serj Sintsov, 2015
 */
@Rule(key = "AvoidHttpSessionAttributesCheck",
        name = "AvoidHttpSessionAttributesCheck",
        description = "Classes must not save states in HTTP sessions",
        tags = {"bug"},
        priority = Priority.CRITICAL)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SOFTWARE_RELATED_PORTABILITY)
@SqaleConstantRemediation("5min")
public class AvoidHttpSessionAttributesCheck extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger logger = LoggerFactory.getLogger(AvoidHttpSessionAttributesCheck.class);

    private static final String HTTP_SESSION_CLASS = "javax.servlet.http.HttpSession";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        logger.trace("Start scanning for 'HttpSession' usages");
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (tree.symbol().enclosingClass() == null) {
            logger.warn("Can not detect type for symbol '{}'. Include project dependencies to scan process",
                    tree.symbol().owner());
            super.visitMethodInvocation(tree);
            return;
        }

        checkMethodsOfHttpSession(tree);
        // TODO: second way -- check method name using patterns

        super.visitMethodInvocation(tree);
    }

    private void checkMethodsOfHttpSession(MethodInvocationTree tree) {
        if (isHttpSessionAncestry(tree.symbol().enclosingClass())) {
            switch (tree.symbol().name()) {
                case "getAttribute":
                case "getAttributeNames":
                case "setAttribute":
                    context.addIssue(tree, this, "Avoid saving attributes into HTTP Session");
            }
        }
    }

    private boolean isHttpSession(Type type) {
        return HTTP_SESSION_CLASS.equals(type.fullyQualifiedName());
    }

    private boolean isHttpSessionAncestry(Symbol.TypeSymbol type) {
        return isHttpSession(type.type()) || isHttpSession(type.superClass());
    }

}
