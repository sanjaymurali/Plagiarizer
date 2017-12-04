package core.algorithm.ast;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/* a Vistor class for classOrInerface
        */
public class ClassVisitor extends VoidVisitorAdapter {
    public List<ClassNode> classes = new ArrayList<ClassNode>();

    /**
     * Build the AST for each class in the file
     *
     * @param c for the current class to visit
     * @return
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration c, Object arg) {
        ClassNode current = new ClassNode(c.getNameAsString());
        MethodTreeVisitor visitor = new MethodTreeVisitor();

        visitor.visit(c, c);

        current.setMethods(visitor.getTree());

        FeildVisitor fVisitor = new FeildVisitor();
        fVisitor.visit(c, null);
        current.setFeilds(fVisitor.getFeilds());
        classes.add(current);

    }

    /**
     * Get the class AST
     *
     * @return the AST of all classes inside the file
     */
    public List<ClassNode> getClasses() {
        return classes;
    }
}
