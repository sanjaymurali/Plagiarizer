package core.algorithm.ast;

public class PackageNode {

    private String name;

    public PackageNode() {
        this.name = "";
    }

    public PackageNode(String name) {
        this.name = name;
    }

    /**
     * @return return the name of this package
     */
    public String getName() {
        return name;
    }

    /**
     * @param name for the name of cureent package
     */
    public void setName(String name) {
        this.name = name;
    }
}
