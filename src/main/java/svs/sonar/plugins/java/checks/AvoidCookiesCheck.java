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
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Description below.
 *
 * @author Serj Sintsov, 2015
 */
@Rule(key = "AvoidCookiesCheck",
        name = "AvoidCookiesCheck",
        description = "Classes must not send cookies in HTTP response",
        tags = {"bug"},
        priority = Priority.CRITICAL)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SOFTWARE_RELATED_PORTABILITY)
@SqaleConstantRemediation("5min")
public class AvoidCookiesCheck extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger logger = LoggerFactory.getLogger(AvoidCookiesCheck.class);

    private static final String HTTP_RESPONSE_CLASS = "javax.servlet.http.HttpServletResponse";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        logger.trace("Start scanning for 'Cookies' usages");
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
        if (isHttpResponseAncestry(tree.symbol().enclosingClass())) {
            switch (tree.symbol().name()) {
                case "addCookie":
                    context.addIssue(tree, this, "Avoid sending cookies to client");
            }
        }
    }

    private boolean isHttpResponse(Type type) {
        return HTTP_RESPONSE_CLASS.equals(type.fullyQualifiedName());
    }

    private boolean isHttpResponseAncestry(Symbol.TypeSymbol type) {
        return isHttpResponse(type.type()) || isHttpResponse(type.superClass());
    }

}
