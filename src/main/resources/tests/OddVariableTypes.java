package tests;

public class OddVariableTypes<T extends Comparable<T>> implements Sorter {
    private ArrayList<String> list;
    private ArrayList simpleList;

    public OddVariableTypes(){
        this.list = null;
        this.simpleList = null;
    }

    private class privateclass {
        private privatemethod() {
            //some stuff here
        }
    }

    private method(){
        //some stuff here
    }
}
