package hw;

import ab;

public class QuickSorter<T extends Comparable<T>> implements Sorter {


    public void quicksort(Comparable[] list, int left, int right) {
        if (right > left) {
            int pivotNewIndex = partition(list, left, right, right);
            quicksort(list, left, pivotNewIndex - 1);
            quicksort(list, pivotNewIndex + 1, right);
        }

    }

    public int partition(Comparable[] list, int left,
                         int right, int pivotIndex) {
        Comparable pivotValue = list[pivotIndex];
        Comparable temp = list[right];
        list[right] = list[pivotIndex];
        list[pivotIndex] = temp;
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (list[i].compareTo(pivotValue) <= 0) {
                temp = list[storeIndex];
                list[storeIndex] = list[i];
                list[i] = temp;
                storeIndex++;
            }
        }
        temp = list[storeIndex];
        list[storeIndex] = list[right];
        list[right] = temp;

        return storeIndex;
    }

    @Override
    public void sort(Comparable[] list) {
        // TODO Auto-generated method stub
        T[] newlist = (T[]) list;
        quicksort(newlist, 0, list.length - 1);

    }


    public void justForTest() {

    }


}
