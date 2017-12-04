package core.algorithm.ast;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AstTree {

    public String name;
    public ImportNode imports;
    public PackageNode currentPackage;
    public List<ClassNode> classes;


    public AstTree() {
        name = "";

    }


    /**
     * Build the AST for the input file and save it in the tree
     *
     * @param fileName for the name of the file
     * @param filePath is the path of the file
     * @return
     */
    public void buildTree(String fileName, String filePath) throws IOException {
        this.name = fileName;
        this.imports = new ImportNode();

        this.classes = new ArrayList<ClassNode>();


        CompilationUnit cu;

        // parse the file
        cu = JavaParser.parse(new FileInputStream(filePath));

        if (cu.getPackageDeclaration().isPresent())
            this.currentPackage = new PackageNode(cu.getPackageDeclaration().get().getNameAsString());
        else {
            this.currentPackage = new PackageNode("");
        }

        if (cu.getImports().size() > 0) {
            for (int i = 0; i < cu.getImports().size(); i++) {
                this.imports.addImports(cu.getImport(i).getNameAsString());

            }
        } else {
            this.imports.addImports("");
        }


        ClassVisitor visitor = new ClassVisitor();
        visitor.visit(cu, null);
        classes.addAll(visitor.getClasses());
    }


}
