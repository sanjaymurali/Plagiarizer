package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {

    private String name;
    private List<MethodNode> methods;
    private List<String> feilds;

    /**
     * constructor
     *
     * @param name for class name
     * @return
     */
    public ClassNode(String name) {
        this.name = name;

        methods = new ArrayList<MethodNode>();
        feilds = new ArrayList<String>();
    }

    /**
     * @return the globle variable in this class
     */
    public List<String> getFeilds() {
        return feilds;
    }

    /**
     * set the feilds for this class
     *
     * @param feilds for the globle variable in this class
     */
    public void setFeilds(List<String> feilds) {
        this.feilds = feilds;
    }

    /**
     * set the methods for this class
     *
     * @param methods for the methods in this class
     */
    public void setMethods(List<MethodNode> methods) {
        this.methods = methods;
    }


    /**
     * @return all methods in this calss
     */
    public List<MethodNode> getMethods() {
        return methods;
    }

    /**
     * @return the name of this class
     */
    public String getName() {
        return name;
    }

    /**
     * set the name for this class
     *
     * @param name for the name of this class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * add one  method to this class AST
     *
     * @param m for the method in this class
     */
    public void addMethod(MethodNode m) {
        this.methods.add(m);
    }

    /**
     * add several  methods to this class AST
     *
     * @param m for the methods in this class
     */
    public void addAllMethod(List<MethodNode> m) {
        this.methods.addAll(m);
    }


}
