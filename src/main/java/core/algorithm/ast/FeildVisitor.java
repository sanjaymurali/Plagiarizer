package core.algorithm.ast;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * a Vistor class for global varible
 */
public  class FeildVisitor extends VoidVisitorAdapter {
    private List<String> feilds = new ArrayList<String>();
    private List<String> globalVar = new ArrayList<String>();

    /**
     * Store all the globle varible in each class
     *
     * @param f for the current field declaration to visit
     * @return
     */
    @Override
    public void visit(FieldDeclaration f, Object arg) {

        globalVar.add(f.getVariable(0).getNameAsString());
        feilds.add(f.toString().replace(f.getVariable(0).getNameAsString(),"v"));
    }

    /**
     * Get all the globle varible in each class
     *
     * @return Return a String list include all globle declaration in this class
     */
    public List<String> getFeilds() {
        return feilds;
    }


    /**
     * Get all the name of globle varible in each class
     *
     * @return Return a String list include all globle variable names in this class
     */
    public List<String> getGlobalVar() {
        return globalVar;
    }
}