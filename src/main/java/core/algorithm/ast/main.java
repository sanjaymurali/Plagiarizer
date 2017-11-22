package core.algorithm.ast;

import core.IO.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        AstTree tree = new AstTree();


        tree.buildTree("test", r.getFile("src\\main\\resources\\MergeSorter.java"));
        AstTree tree2 = new AstTree();
        tree2.buildTree("test", r.getFile("src\\main\\resources\\MergeSorter2.java"));
        System.out.println(tree.name);
        System.out.println("The imports is: " + tree.imports.getImports().get(0));
        for (int i = 0; i < tree.classes.size(); i++) {
            System.out.println(tree.classes.get(i).getName());
            System.out.println(tree.classes.get(i).getFeilds());
            for (int j = 0; j < tree.classes.get(i).getMethods().size(); j++) {
                MethodNode temp = tree.classes.get(i).getMethods().get(j);
                System.out.println("The " + (j + 1) + " method name is " + temp.getName());
                System.out.println("The " + (j + 1) + " method type is " + temp.getType());
                System.out.println("The " + (j + 1) + " method parameter is " + temp.getParameters());
                System.out.println("The " + (j + 1) + " method body is " + temp.getBody().toString());
            }
        }

        NGramComparison comparison = new NGramComparison();
        comparison.nGramComparison(tree, tree2);
        System.out.println("Same package and methods, different class name and import");
        System.out.println(" result: " + comparison.getResult());


        AstTree tree3 = new AstTree();
        tree3.buildTree("test", r.getFile("src\\main\\resources\\QuickSorter.java"));


        comparison.nGramComparison(tree, tree3);
        System.out.println("\nSame import and package, different class ");
        System.out.println(" result: " + comparison.getResult());

        List<AstTree> project1 = new ArrayList<AstTree>();
        List<AstTree> project2 = new ArrayList<AstTree>();
        project1.add(tree);
        project1.add(tree);
        project2.add(tree2);
        project2.add(tree3);
        comparison.nGramComparison(project1, project2);
        System.out.println("\nSame import and package, different class ");
        System.out.println(" result: " + comparison.getResult());


    }
}
