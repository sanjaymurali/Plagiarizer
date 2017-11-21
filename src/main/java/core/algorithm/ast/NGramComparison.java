package core.algorithm.ast;

import java.util.ArrayList;
import java.util.List;

public class NGramComparison {
    private static final int N =3;
    private double result;


    public void nGramComparison(AstTree t1, AstTree t2) {
        List<String> target1;
        List<String> target2;


        target1 = new ArrayList<String>();
        target2 = new ArrayList<String>();
        for (int i = 0; i < t1.classes.size();i++) {
            target1.addAll(transfer(t1.classes.get(i)));
        }
        for (int i = 0; i < t2.classes.size();i++) {
            target2.addAll(transfer(t2.classes.get(i)));
        }
        target1.addAll(transfer(t1.currentPackage));
        target2.addAll(transfer(t2.currentPackage));


        target1.addAll(transfer(t1.imports));
        target1.addAll(transfer(t2.imports));


        result = calculateResult(target1,target2);
    }

    public void nGramComparison(List<AstTree> t1, List<AstTree> t2) {
        List<String> target1;
        List<String> target2;
        target1 = new ArrayList<String>();
        target2 = new ArrayList<String>();


        for(int m=0;m<t1.size();m++){
            for (int i = 0; i < t1.get(m).classes.size();i++) {
                target1.addAll(transfer(t1.get(m).classes.get(i)));
            }
            target1.addAll(transfer(t1.get(m).currentPackage));
            target1.addAll(transfer(t1.get(m).imports));
        }


        for(int m=0;m<t1.size();m++) {
            for (int i = 0; i < t2.get(m).classes.size(); i++) {
                target2.addAll(transfer(t2.get(m).classes.get(i)));
            }

            target2.addAll(transfer(t2.get(m).currentPackage));


            target1.addAll(transfer(t2.get(m).imports));

        }
        result = calculateResult(target1,target2);
    }


    public double calculateResult(List<String> target1, List<String> target2){
        double count =0.0;
        double result;
        if(target1.size()<=target2.size()){
            for(int i = 0;i<target1.size();i++){
                if(target2.contains(target1.get(i))){
                    count++;
                }
            }
             result = count/target1.size();
        }
        else {
            for(int i = 0;i<target2.size();i++){
                if(target1.contains(target2.get(i))){
                    count++;
                }
            }
            result = count/target2.size();
        }


        return result;
    }

    private List<String> transfer(ClassNode c){
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();
        for(int i=0;i<c.getName().length();i++){

                if (c.getName().charAt(i)!=' ')
                    data.add(c.getName().charAt(i));

        }

        for(int i=0;i<c.getFeilds().size();i++) {
            for (int j = 0; j < c.getFeilds().get(i).length(); j++) {
            if(c.getFeilds().get(i).charAt(j)!=' '){
                data.add(c.getFeilds().get(i).charAt(j));
            }
            }
        }

        for(int i=0;i<c.getMethods().size();i++) {
            for (int j = 0; j < c.getMethods().get(i).getName().length(); j++) {
                if(c.getMethods().get(i).getName().charAt(j)!=' '){
                    data.add(c.getMethods().get(i).getName().charAt(j));

                }
            }
            for (int j = 0; j < c.getMethods().get(i).getBody().size();j++){
                for(int k =0; k <c.getMethods().get(i).getBody().get(j).length();k++){
                    if(c.getMethods().get(i).getBody().get(j).charAt(k)!=' '){
                        data.add(c.getMethods().get(i).getBody().get(j).charAt(k));
                    }
                }
            }
        }


        if(data.size()==0){
            result.add("");
        }
        else if(data.size()<N){
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        }
        else {
            for (int i = 0; i <= (data.size() - N); i++) {
                String temp = "";
                for (int j = 0; j < N; j++) {
                    temp = temp + data.get(i + j);
                }
                result.add(temp);
            }
        }

        return result;
    }

    private List<String> transfer(ImportNode imports){
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();

        for(int i=0;i<imports.getImports().size();i++){
            for(int j=0;j< imports.getImports().get(i).length();j++) {
                if (imports.getImports().get(i).charAt(j)!=' ')
                    data.add(imports.getImports().get(i).charAt(j));
            }
        }
        if(data.size()==0){
            result.add("");
        }
        else if(data.size()<N){
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        }
        else {
            for (int i = 0; i <= (data.size() - N); i++) {
                String temp = "";
                for (int j = 0; j < N; j++) {
                    temp = temp + data.get(i + j);
                }
                result.add(temp);
            }
        }

        return result;



    }

    private List<String> transfer(PackageNode p){
        List<Character> data = new ArrayList<Character>();
        List<String> result = new ArrayList<String>();
        for(int i=0;i<p.getName().length();i++){
            if(p.getName().charAt(i)!=' ')
            data.add(p.getName().charAt(i));
        }
        if(data.size()==0){
            result.add("");
        }
        else if(data.size()<N){
            for (int i = 0; i < data.size(); i++) {
                result.add(data.get(i) + "");
            }
        }
        else {
            for (int i = 0; i <= (data.size() - N); i++) {
                String temp = "";
                for (int j = 0; j < N; j++) {
                    temp = temp + data.get(i + j);
                }
                result.add(temp);
            }
        }

        return result;
    }

    public double getResult() {
        return result;
    }


}
