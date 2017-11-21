package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class MethodNode {
    private String name;
    private String type;
    private List<ParameterNode> parameters;
    private List<String> body;
    MethodNode(){
        this.parameters=new ArrayList<ParameterNode>();
        this.body = new ArrayList<String>();
    }

    public MethodNode(String name, String type) {
        this.name = name;
        this.type = type;
        this.parameters=new ArrayList<ParameterNode>();
        this.body = new ArrayList<String>();
    }

    public void addParameter(ParameterNode p){
        this.parameters.add(p);
    }

    public List<String> getBody() {
        return body;
    }

    public void addBody(String  b){
        this.body.add(b);
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<ParameterNode> getParameters() {
        return parameters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
