package com.algorithms;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/26.
 */
public class InsertionSort {
    int[] array = {29, 10, 4, 5, 3};

    @Test
    public void sort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j > -1 && array[j] < key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void sort2() {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] < array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
