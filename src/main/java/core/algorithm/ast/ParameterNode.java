package core.algorithm.ast;

public class ParameterNode {

    private String name;
    private String type;

    public ParameterNode() {
    }

    public ParameterNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){

        return type+" " +name;
    }
}
