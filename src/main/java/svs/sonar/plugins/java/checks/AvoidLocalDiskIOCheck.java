package svs.sonar.plugins.java.checks;

import com.google.common.collect.ImmutableSet;
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

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Set;

/**
 * Description below.
 *
 * @author Serj Sintsov, 2015
 */
@Rule(key = "AvoidLocalFileIO",
        name = "AvoidLocalFileIO",
        description = "Classes must not use local disk IO",
        tags = {"bug"},
        priority = Priority.CRITICAL)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SOFTWARE_RELATED_PORTABILITY)
@SqaleConstantRemediation("5min")
public class AvoidLocalDiskIOCheck extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger logger = LoggerFactory.getLogger(AvoidLocalDiskIOCheck.class);

    private JavaFileScannerContext context;

    private static final Set<String> forbiddenClasses = ImmutableSet.<String>builder()
            .add(File.class.getCanonicalName())
            .add(FileInputStream.class.getCanonicalName())
            .add(FileReader.class.getCanonicalName())
            .add(FileWriter.class.getCanonicalName())
            .add(FilePermission.class.getCanonicalName())
            .add(RandomAccessFile.class.getCanonicalName())
            .add(FileDescriptor.class.getCanonicalName())
            .add(FileOutputStream.class.getCanonicalName())
            .add(FileChannel.class.getCanonicalName())
            .add(FileLock.class.getCanonicalName())
            .build();

    @Override
    public void scanFile(JavaFileScannerContext context) {
        logger.trace("START scanning for 'local IO' fields");
        this.context = context;
        scan(context.getTree());
        logger.trace("STOP scanning for 'local IO' fields");
    }

    @Override
    public void visitNewClass(NewClassTree tree) {
        if (isForbiddenType(tree.identifier()))
            context.addIssue(tree, this, "Avoid instantiation of this class");

        for (ExpressionTree argTree : tree.arguments())
            if (isForbiddenType(argTree.symbolType())) {
                context.addIssue(tree, this, "Avoid using arguments of this class");
                break;
            }

        super.visitNewClass(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (tree.symbol().enclosingClass() == null) {
            logger.warn("Can not detect type for symbol '{}'. Include project dependencies to scan process",
                    tree.symbol().owner());
            super.visitMethodInvocation(tree);
            return;
        }

        checkInvokationOnForbiddenClasses(tree);
        // TODO: second way -- check method name using patterns

        super.visitMethodInvocation(tree);
    }

    private void checkInvokationOnForbiddenClasses(MethodInvocationTree tree) {
        if (isForbiddenAncestry(tree.symbol().enclosingClass()))
            context.addIssue(tree, this, "Avoid calling methods on this class");

        for (ExpressionTree argTree : tree.arguments())
            if (isForbiddenType(argTree.symbolType())) {
                context.addIssue(tree, this, "Avoid calling methods with this argument");
                break;
            }

        if (isForbiddenType(tree.methodSelect().symbolType()))
            context.addIssue(tree, this, " calling methods of this type");
    }

    private boolean isForbiddenType(TypeTree tree) {
        return isForbiddenType(tree.symbolType());
    }

    private boolean isForbiddenType(Type type) {
        return forbiddenClasses.contains(type.fullyQualifiedName());
    }

    private boolean isForbiddenAncestry(Symbol.TypeSymbol type) {
        return isForbiddenType(type.type()) || isForbiddenType(type.superClass());
    }

}
