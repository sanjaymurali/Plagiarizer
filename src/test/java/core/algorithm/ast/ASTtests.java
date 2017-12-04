package core.algorithm.ast;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ASTtests {

    private  AstTree tree = new AstTree();
    private  AstTree tree2 = new AstTree();
    private  AstTree tree3= new AstTree();
    private NGramComparison comparison = new NGramComparison();
    private AstTree testTree = new AstTree();
    public  ASTtests() throws IOException {

        tree.buildTree("test","src/main/resources/tests/MergeSorter.java");

        tree2.buildTree("test","src/main/resources/tests/MergeSorter2.java");
        tree3.buildTree("test","src/main/resources/tests/QuickSorter.java");

    }


    @Test
    public void testClassName(){
        Assert.assertEquals(tree.classes.get(0).getName(),"MergeSorter");
    }

    @Test
    public void testClassFeilds(){
        List<String> s = new ArrayList<>();
        s.add("private int v;");
        Assert.assertEquals(tree.classes.get(0).getFeilds(),s);
    }

    @Test
    public void testMethodInformation(){

        List<String> s2 = new ArrayList<>();
        s2.add("Comparable[] v = new Comparable[list.length];");
        s2.add("mergerSort(list, v list.length - 1, temp);");

        Assert.assertEquals(tree.classes.get(0).getMethods().get(0).getName(),"public void m(Comparable[] list)");
        Assert.assertEquals(tree.classes.get(0).getMethods().get(0).getType(),"void");
        Assert.assertEquals(tree.classes.get(0).getMethods().get(0).getParameters().get(0).toString(),"Comparable[] list");
        Assert.assertEquals(tree.classes.get(0).getMethods().get(0).getBody(),s2);
    }

    @Test
    public void testCompareSameFile() {


        comparison.nGramComparison(tree, tree);
        Assert.assertEquals(comparison.getResult(), 1.0,0);
    }

    @Test
    public void testCompareSmallChangeFile() {


        comparison.nGramComparison(tree, tree2);
        Assert.assertEquals(comparison.getResult(), 1.0,0.1);
    }

    @Test
    public void testCompareLargeChangeFile() {


        comparison.nGramComparison(tree, tree3);
        Assert.assertEquals(comparison.getResult(), 0.5,0.2);
    }

    @Test
    public void testBothEmptyTree() {

        AstTree t4 = new AstTree();
        AstTree t5 = new AstTree();
        comparison.nGramComparison(t4, t5);
        Assert.assertEquals(comparison.getResult(), 1,0.0);
    }

    @Test
    public void testOneEmptyTree() {

        AstTree t4 = new AstTree();
        AstTree t5 = new AstTree();
        comparison.nGramComparison(t4, tree);
        Assert.assertEquals(comparison.getResult(), 0,0.0);
        comparison.nGramComparison(tree, t4);
        Assert.assertEquals(comparison.getResult(), 0,0.0);
        comparison.nGramComparison(tree, t5);
        Assert.assertEquals(comparison.getResult(), 0,0.0);
    }

    @Test
    public void testTreeList() {

        AstTree t4 = new AstTree();
        List<AstTree> t5 = new ArrayList<AstTree>();
        List<AstTree> t6 = new ArrayList<AstTree>();
        t5.add(tree);
        t5.add(tree2);
        t6.add(tree);
        t6.add(t4);
        comparison.nGramComparison(t5,t6);
        Assert.assertEquals(comparison.getResult(), 1,0.0);
        comparison.nGramComparison(t6,t5);
        Assert.assertEquals(comparison.getResult(), 1,0.0);
    }

    @Test
    public void testEmptyTreeList() {

        AstTree t4 = new AstTree();
        List<AstTree> t5 = new ArrayList<AstTree>();
        List<AstTree> t6 = new ArrayList<AstTree>();

        comparison.nGramComparison(t5,t6);
        Assert.assertEquals(comparison.getResult(), 1,0.0);
        t5.add(tree);
        t6.add(t4);
        comparison.nGramComparison(t5,t6);
        Assert.assertEquals(comparison.getResult(), 0,0.0);
    }
    @Test
    public void testClassNode() {

        ClassNode c = new ClassNode("abc");
        MethodNode m = new MethodNode();
        m.setName("method1");
        m.setType("void");
        c.addMethod(m);
        List<MethodNode> mList =new ArrayList<MethodNode>();
        mList.add(m);
        c.addAllMethod(mList);
        Assert.assertEquals(c.getName(),"abc");
        Assert.assertEquals(c.getMethods().size(),2);
        Assert.assertEquals(c.getMethods().get(0).getName(),"method1");
        Assert.assertEquals(c.getMethods().get(0).getType(),"void");
        c.setName("cba");
        Assert.assertEquals(c.getName(),"cba");
    }

    @Test
    public void testPackageNode(){
        PackageNode p = new PackageNode();
        p.setName("abc");
        Assert.assertEquals(p.getName(),"abc");
    }

    @Test
    public void testParameterNode(){
        ParameterNode p = new ParameterNode();
        p.setName("abc");
        p.setType("String");
        Assert.assertEquals(p.getName(),"abc");
        Assert.assertEquals(p.getType(),"String");
    }

    @Test
    public void buildSpecialTree() throws IOException {
        testTree.buildTree("test","src/main/resources/tests/test.java");
        comparison.nGramComparison(testTree,tree);
        Assert.assertEquals(comparison.getResult(), 0,0.1);
    }


    @Test
    public void nGramComparisonTwoProject() throws IOException {
        String[] prject1 = new String[2];
        String[] prject2 = new String[2];
        String[] prject3 = new String[2];
        prject1[0]="src/main/resources/tests/test.java";
        prject1[1]="src/main/resources/tests/MergeSorter.java";

        prject2[0]="src/main/resources/tests/MergeSorter2.java";
        prject2[1]="src/main/resources/tests/QuickSorter.java";
        comparison.nGramComparison(prject1,prject1);
        Assert.assertEquals(comparison.getResult(), 1,0.0);
        comparison.nGramComparison(prject1,prject2);
        Assert.assertEquals(comparison.getResult(), 1,0.1);
        comparison.nGramComparison(prject3,prject2);
        Assert.assertEquals(comparison.getResult(), 0,0.1);
    }
}