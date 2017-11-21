package core.algorithm.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class AstTree {

    public String name;
    public ImportNode imports;
    public PackageNode currentPackage;
    public List<ClassNode> classes;

    public AstTree() {
        name = "";
        imports = new ImportNode();
        currentPackage = new PackageNode();
        classes = new ArrayList<ClassNode>();
    }

    public void buildTree(String fileName, String fileData) {
        this.name = fileName;



        CompilationUnit cu;

            // parse the file
            cu = JavaParser.parse(fileData);

        this.currentPackage = new PackageNode(cu.getPackageDeclaration().get().getNameAsString());

        if(cu.getImports().size()>0) {
            for (int i = 0; i < cu.getImports().size(); i++) {
                this.imports.addImports(cu.getImport(i).getNameAsString());

            }
        }
        else{
            this.imports.addImports("");
        }

        if(cu.getNodesByType(ClassOrInterfaceDeclaration.class).size()>0) {
            for (int i = 0; i < cu.getNodesByType(ClassOrInterfaceDeclaration.class).size(); i++) {
                ClassNode current = new ClassNode(cu.getNodesByType(ClassOrInterfaceDeclaration.class).get(i).getNameAsString());
                MethodTreeVisitor visitor = new MethodTreeVisitor();

                visitor.visit(cu, null);

                current.setMethods(visitor.getTree());

                FeildVisitor fVisitor = new FeildVisitor();
                fVisitor.visit(cu,null);
                current.setFeilds(fVisitor.getFeilds());
                classes.add(current);
            }
        }
        else{
            ClassNode current = new ClassNode("");
            classes.add(current);
        }

    }

    private static class FeildVisitor extends VoidVisitorAdapter{
        private List<String> feilds = new ArrayList<String>();

        public void visit(FieldDeclaration f, Object arg){

            feilds.add(f.toString());
        }

        public List<String> getFeilds() {
            return feilds;
        }
    }



    private static class MethodTreeVisitor extends VoidVisitorAdapter {

        private List<MethodNode> tree = new ArrayList<MethodNode>();
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
//            System.out.println(n.getBody().get().getStatements().get(0));
//            System.out.println("the function type is: " +n.getType().asString());
//            System.out.println("the function name is: "+n.getName());
//            System.out.println("the function's parameters are: "+n.getParameters());
//            System.out.println("the first parameter's type is: "+n.getParameter(0).toString()+"\n");

        }

        public List<MethodNode> getTree(){
            return  tree;
        }

    }
}
