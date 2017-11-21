package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassNode {

    private String name;
    private List<MethodNode> methods;
    private List<String> feilds;

    public ClassNode(String name) {
        this.name = name;

        methods = new ArrayList<MethodNode>();
        feilds = new ArrayList<String>();
    }

    public List<String> getFeilds() {
        return feilds;
    }

    public void setFeilds(List<String> feilds) {
        this.feilds = feilds;
    }

    public void setMethods(List<MethodNode> methods) {
        this.methods = methods;
    }

    public void addMethod(MethodNode m){
        this.methods.add(m);
    }

    public void addAllMethod( List<MethodNode> m){
        this.methods.addAll(m);
    }
    public List<MethodNode> getMethods() {
        return methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
