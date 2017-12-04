package core.algorithm.ast;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * a Vistor class for methods
 */
public  class MethodTreeVisitor extends VoidVisitorAdapter {


    private List<MethodNode> tree = new ArrayList<MethodNode>();

    private List<String> getFieldName(ClassOrInterfaceDeclaration c){

        List<String> var = new ArrayList<String>();

        FeildVisitor fVisitor = new FeildVisitor();
        fVisitor.visit(c,null);
        return fVisitor.getGlobalVar();
    }

    /**
     * Build AST for all method in each class
     * @param  n for the current method  to visit
     * @return
     *
     */
    @Override
    public void visit(MethodDeclaration n, Object arg) {

        List<String> var = new ArrayList<String>();
        var.addAll(getFieldName((ClassOrInterfaceDeclaration)arg));


        String name = n.getDeclarationAsString();
        name=name.replace(n.getNameAsString(),"m");

        MethodNode current = new MethodNode(name,n.getType().asString());


        ParameterNode parameter;

        if(n.getParameters().size()>0)
            for(int i=0;i<n.getParameters().size();i++){
                parameter = new ParameterNode(n.getParameter(i).getName().asString(),n.getParameter(i).getType().toString());
                current.addParameter(parameter);
                var.add(n.getParameter(i).getName().asString());
            }
        else{
            parameter = new ParameterNode("","");
            current.addParameter(parameter);
        }

        if (n.getBody().get().getStatements().size()>0)
            for(int i=0;i<n.getBody().get().getStatements().size();i++){
                String body = n.getBody().get().getStatement(i).toString();
                if (n.getBody().get().getStatement(i).isExpressionStmt()){

                    String [] temp = body.split(" ");
                    var.add(temp[1]);
                    body=body.replaceAll(temp[1],"v");
                }
                else{
                    for(int j =0;j<var.size();j++){
                        body= body.replaceAll(var.get(j),"v");
                    }
                }

                current.addBody(body);

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