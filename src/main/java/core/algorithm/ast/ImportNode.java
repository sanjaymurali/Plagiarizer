package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class ImportNode {

    private List<String> imports = new ArrayList<String>();


    /**
     * add one  import to this file AST
     *
     * @param n for the imports in this file
     */
    public void addImports(String n) {
        this.imports.add(n);
    }

    /**
     * @return the imports in this file
     */
    public List<String> getImports() {
        return imports;
    }


}
