package core.algorithm.ast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NGramComparison {
    private static final int N = 3;
    private double result;


    /**
     * comapre two AST and save the result
     *
     * @param project1 the first project path to be compared
     * @param project2 the second project path to be compared
     */
    public void nGramComparison(String[] project1, String[] project2) throws IOException {

        List<AstTree> target1 = new ArrayList<AstTree>();
        List<AstTree> target2 = new ArrayList<AstTree>();

        for (int i = 0; i < project1.length; i++) {
            AstTree temp = new AstTree();
            if (project1[i] != null)
                temp.buildTree("project1", project1[i]);
            target1.add(temp);
        }

        for (int i = 0; i < project2.length; i++) {
            AstTree temp = new AstTree();
            if (project2[i] != null)
                temp.buildTree("project2", project2[i]);
            target2.add(temp);
        }

        nGramComparison(target1, target2);
    }

    /**
     * comapre two AST and save the result
     *
     * @param t1 the first AST to be compared
     * @param t2 the second AST to be compared
     */
    public void nGramComparison(AstTree t1, AstTree t2) {
        List<String> target1;
        List<String> target2;


        target1 = new ArrayList<String>();
        target2 = new ArrayList<String>();
        if (t1.classes != null)
            for (int i = 0; i < t1.classes.size(); i++) {
                target1.addAll(transfer(t1.classes.get(i)));
            }

        if (t2.classes != null)
            for (int i = 0; i < t2.classes.size(); i++) {
                target2.addAll(transfer(t2.classes.get(i)));
            }
        if (t1.currentPackage != null)
            target1.addAll(transfer(t1.currentPackage));
        if (t2.currentPackage != null)
            target2.addAll(transfer(t2.currentPackage));

        if (t1.imports != null)
            target1.addAll(transfer(t1.imports));
        if (t2.imports != null)
            target1.addAll(transfer(t2.imports));


        if (target1.isEmpty() && target2.isEmpty()) {
            result = 1.0;
        } else if (target1.isEmpty() || target2.isEmpty()) {
            result = 0.0;
        } else {
            result = calculateResult(target1, target2);
        }
    }

    /**
     * comapre two AST list and save the result
     *
     * @param t1 the  AST s of first project
     * @param t2 the AST s  of second project
     */
    public void nGramComparison(List<AstTree> t1, List<AstTree> t2) {
        List<String> target1;
        List<String> target2;
        target1 = new ArrayList<String>();
        target2 = new ArrayList<String>();


        for (int m = 0; m < t1.size(); m++) {
            if (t1.get(m).classes != null)
                for (int i = 0; i < t1.get(m).classes.size(); i++) {

                    target1.addAll(transfer(t1.get(m).classes.get(i)));
                }
            if (t1.get(m).currentPackage != null)
                target1.addAll(transfer(t1.get(m).currentPackage));
            if (t1.get(m).imports != null)
                target1.addAll(transfer(t1.get(m).imports));
        }


        for (int m = 0; m < t1.size(); m++) {
            if (t2.get(m).classes != null)
                for (int i = 0; i < t2.get(m).classes.size(); i++) {

                    target2.addAll(transfer(t2.get(m).classes.get(i)));
                }
            if (t2.get(m).currentPackage != null)
                target2.addAll(transfer(t2.get(m).currentPackage));

            if (t2.get(m).imports != null)
                target2.addAll(transfer(t2.get(m).imports));

        }

        if (target1.isEmpty() && target2.isEmpty()) {
            result = 1.0;
        } else if (target1.isEmpty() || target2.isEmpty()) {
            result = 0.0;
        } else {
            result = calculateResult(target1, target2);
        }
    }


    /**
     * calculate the difference between the String data of two AST using ngram
     *
     * @param target1 the String list data of the first targer
     * @param target2 the  String list data of the second targer
     */
    public double calculateResult(List<String> target1, List<String> target2) {
        double count = 0.0;
        double result;
        if (target1.size() <= target2.size()) {

            for (int i = 0; i < target1.size(); i++) {

                if (target2.contains(target1.get(i))) {
                    count++;
                    target2.remove(target1.get(i));
                }
            }
            result = count / target1.size();
        } else {

            for (int i = 0; i < target2.size(); i++) {

                if (target1.contains(target2.get(i))) {
                    count++;
                    target1.remove(target2.get(i));
                }
            }
            result = count / target2.size();
        }


        return result;
    }

    /**
     * Transfer the Node into String List which will be used for ngram
     *
     * @param c is the ClaasNode to be transfered
     */
    private List<String> transfer(ClassNode c) {
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < c.getName().length(); i++) {

            if (c.getName().charAt(i) != ' ')
                data.add(c.getName().charAt(i));

        }

        for (int i = 0; i < c.getFeilds().size(); i++) {
            for (int j = 0; j < c.getFeilds().get(i).length(); j++) {
                if (c.getFeilds().get(i).charAt(j) != ' ') {
                    data.add(c.getFeilds().get(i).charAt(j));
                }
            }
        }

        for (int i = 0; i < c.getMethods().size(); i++) {
            for (int j = 0; j < c.getMethods().get(i).getName().length(); j++) {
                if (c.getMethods().get(i).getName().charAt(j) != ' ') {
                    data.add(c.getMethods().get(i).getName().charAt(j));

                }
            }
            for (int j = 0; j < c.getMethods().get(i).getBody().size(); j++) {
                for (int k = 0; k < c.getMethods().get(i).getBody().get(j).length(); k++) {
                    if (c.getMethods().get(i).getBody().get(j).charAt(k) != ' ') {
                        data.add(c.getMethods().get(i).getBody().get(j).charAt(k));
                    }
                }
            }
        }


        if (data.size() < N) {
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        } else {

            result.addAll(makeNString(data));
        }

        return result;
    }

    /**
     * Transfer the Node into String List which will be used for ngram
     *
     * @param imports is the ImportNode to be transfered
     */
    private List<String> transfer(ImportNode imports) {
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < imports.getImports().size(); i++) {
            for (int j = 0; j < imports.getImports().get(i).length(); j++) {
                if (imports.getImports().get(i).charAt(j) != ' ')
                    data.add(imports.getImports().get(i).charAt(j));
            }
        }

        if (data.size() < N) {
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        } else {

            result.addAll(makeNString(data));
        }

        return result;


    }

    /**
     * Transfer the Node into String List which will be used for ngram
     *
     * @param p is the PackageNode to be transfered
     */
    private List<String> transfer(PackageNode p) {
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < p.getName().length(); i++) {
            if (p.getName().charAt(i) != ' ')
                data.add(p.getName().charAt(i));
        }

        if (data.size() < N) {
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        } else {

            result.addAll(makeNString(data));
        }

        return result;
    }


    /**
     * create the lisf of length-n string by using the char list
     *
     * @param data is the list of the char
     */
    private List<String> makeNString(List<Character> data) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i <= (data.size() - N); i++) {
            String temp = "";
            for (int j = 0; j < N; j++) {
                temp = temp + data.get(i + j);
            }
            result.add(temp);
        }

        return result;
    }

    /**
     * @return the result of the comparison
     */
    public double getResult() {
        return result;
    }


}
