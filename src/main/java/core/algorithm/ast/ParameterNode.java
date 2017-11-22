package core.algorithm.ast;

public class ParameterNode {

    private String name;
    private String type;

    public ParameterNode() {
    }

    /**
     * constructor
     *
     * @param type for the typr of current parameter
     * @param name for the typr of current parameter
     */
    public ParameterNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return return the name of this parameter
     */
    public String getName() {
        return name;
    }

    /**
     * @param name for the typr of current parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return return the type of this parameter
     */
    public String getType() {
        return type;
    }

    /**
     * @param type for the typr of current parameter
     */
    public void setType(String type) {
        this.type = type;
    }

    public String toString() {

        return type + " " + name;
    }
}
