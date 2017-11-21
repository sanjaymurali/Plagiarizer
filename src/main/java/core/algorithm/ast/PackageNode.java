package core.algorithm.ast;

public class PackageNode {

    private String name;

    public PackageNode() {
        this.name="";
    }

    public PackageNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
