package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class MethodNode {
    private String name;
    private String type;
    private List<ParameterNode> parameters;
    private List<String> body;

    MethodNode() {
        this.parameters = new ArrayList<ParameterNode>();
        this.body = new ArrayList<String>();
    }

    /**
     * constructor
     *
     * @param name for declaration of the method
     * @param type the type of the method
     */
    public MethodNode(String name, String type) {
        this.name = name;
        this.type = type;
        this.parameters = new ArrayList<ParameterNode>();
        this.body = new ArrayList<String>();
    }

    /**
     * add one  parameter to this method AST
     *
     * @param p for one parameter in this method
     */
    public void addParameter(ParameterNode p) {
        this.parameters.add(p);
    }

    /**
     * @return return the operations in this method
     */
    public List<String> getBody() {
        return body;
    }

    /**
     * add one  operation to this method AST
     *
     * @param b for one operation in this method
     */
    public void addBody(String b) {
        this.body.add(b);
    }

    /**
     * @return return the name of this method
     */
    public String getName() {
        return name;
    }

    /**
     * @return return the type of this method
     */
    public String getType() {
        return type;
    }

    /**
     * @return return the parameters in this method
     */
    public List<ParameterNode> getParameters() {
        return parameters;
    }

    /**
     * set the declaration for this method
     *
     * @param name for the declaration for this method
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the tyoe for current method
     *
     * @param type for the type of this method
     */
    public void setType(String type) {
        this.type = type;
    }
}
