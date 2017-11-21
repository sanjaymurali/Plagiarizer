package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class ImportNode {

    private List<String> imports = new ArrayList<String>();




    public void addImports(String n){
        this.imports.add(n);
    }

    public List<String> getImports() {
        return imports;
    }


}
