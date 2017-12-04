/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4;

import hw4.*;

/**
 * @author cjrjh
 */
public class MergeSorter2<T extends Comparable<T>> implements Sorter {

    private int asc;

    @Override
    public void sort(Comparable[] list) {
        Comparable[] temp = new Comparable[list.length];
        mergerSort(list, 0, list.length - 1, temp);
    }

    public void merge(Comparable[] list, int first, int mid, int last, Comparable[] temp) {
        int current = 0;
        int i = first;
        int j = mid + 1;
        int m = mid;
        int n = last;
        asc = 1;
        while (i < m && j <= n) {
            if (list[i].compareTo(list[j]) <= 0) {
                temp[current] = list[i];
                current++;
                i++;
            } else {
                temp[current] = list[j];
                current++;
                j++;
            }
        }
        while (i <= m) {
            temp[current] = list[i];
            current++;
            i++;
        }
        while (j <= n) {
            temp[current] = list[j];
            current++;
            j++;
        }

        for (i = 0; i < current; i++) {
            list[first + i] = temp[i];
        }
    }

    public void mergerSort(Comparable[] list, int first, int last, Comparable[] temp) {

        if (first < last) {
            int mid = (first + last) / 2;
            mergerSort(list, first, mid, temp);
            mergerSort(list, mid + 1, last, temp);
            merge(list, first, mid, last, temp);
        }

    }
}