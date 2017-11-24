package core.algorithm.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AstTree {

    public String name;
    public ImportNode imports;
    public PackageNode currentPackage;
    public List<ClassNode> classes;


    public AstTree() {
        name = "";

    }




    /**
     * Build the AST for the input file and save it in the tree
     * @param fileName for the name of the file
     *  @param filePath is the path of the file
     * @return
     *
     */
    public void buildTree(String fileName, String filePath)throws IOException {
        this.name = fileName;
        this.imports = new ImportNode();

        this.classes = new ArrayList<ClassNode>();



        CompilationUnit cu;

        File x = new File(filePath);
        System.out.println("From hee: " + x.exists() + " " + filePath);

        // parse the file
        cu = JavaParser.parse(new FileInputStream(filePath));


        if(cu.getPackageDeclaration().isPresent())
            this.currentPackage = new PackageNode(cu.getPackageDeclaration().get().getNameAsString());
        else{
            this.currentPackage = new PackageNode("");
        }

        if(cu.getImports().size()>0) {
            for (int i = 0; i < cu.getImports().size(); i++) {
                this.imports.addImports(cu.getImport(i).getNameAsString());

            }
        }
        else{
            this.imports.addImports("");
        }


        ClassVisitor visitor = new ClassVisitor();
        visitor.visit(cu,null);
        classes.addAll(visitor.getClasses());
    }

    /**
     * a Vistor class for classOrInerface
     */
    private static class ClassVisitor extends VoidVisitorAdapter{
        public List<ClassNode> classes =new ArrayList<ClassNode>();

        /**
         * Build the AST for each class in the file
         * @param c for the current class to visit
         * @return
         *
         */
        @Override
        public void visit(ClassOrInterfaceDeclaration c, Object arg){
            ClassNode current = new ClassNode(c.getNameAsString());
            MethodTreeVisitor visitor = new MethodTreeVisitor();

            visitor.visit(c, null);

            current.setMethods(visitor.getTree());

            FeildVisitor fVisitor = new FeildVisitor();
            fVisitor.visit(c,null);
            current.setFeilds(fVisitor.getFeilds());
            classes.add(current);

        }

        /**
         * Get the class AST
         * @return the AST of all classes inside the file
         *
         */
        public List<ClassNode> getClasses() {
            return classes;
        }
    }


    /**
     * a Vistor class for global varible
     */
    private static class FeildVisitor extends VoidVisitorAdapter{
        private List<String> feilds = new ArrayList<String>();

        /**
         * Store all the globle varible in each class
         * @param  f for the current field declaration to visit
         * @return
         *
         */
        @Override
        public void visit(FieldDeclaration f, Object arg){

            feilds.add(f.toString());
        }

        /**
         * Get all the globle varible in each class
         * @return  Return a String list include all globle declaration in this class
         *
         */
        public List<String> getFeilds() {
            return feilds;
        }
    }




    /**
     * a Vistor class for methods
     */
    private static class MethodTreeVisitor extends VoidVisitorAdapter {


        private List<MethodNode> tree = new ArrayList<MethodNode>();
        /**
         * Build AST for all method in each class
         * @param  n for the current method  to visit
         * @return
         *
         */
        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class methods
            String name = n.getDeclarationAsString();

            MethodNode current = new MethodNode(name,n.getType().asString());


            ParameterNode parameter;

            if(n.getParameters().size()>0)
                for(int i=0;i<n.getParameters().size();i++){
                    parameter = new ParameterNode(n.getParameter(i).getName().asString(),n.getParameter(i).getType().toString());
                    current.addParameter(parameter);
                }
            else{
                parameter = new ParameterNode("","");
                current.addParameter(parameter);
            }

            if (n.getBody().get().getStatements().size()>0)
                for(int i=0;i<n.getBody().get().getStatements().size();i++){

                    current.addBody(n.getBody().get().getStatement(i).toString());

                }
            else{
                current.addBody("");
            }

            tree.add(current);

        }

        /**
         * Get the AST for all method
         * @return the method AST in each class
         *
         */
        public List<MethodNode> getTree(){
            return  tree;
        }

    }
}