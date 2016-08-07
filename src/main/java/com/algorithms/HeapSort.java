package com.algorithms;

/**
 * Created by Administrator on 2016/8/6.
 */

import java.util.Arrays;

/* Class HeapSort */
public class HeapSort {
    private static int[] array = {1, 9, 18, 25, 4, 6, 2, 99, 50};
    private static int arrayLen;

    public static void main(String[] args) {
        sort();
        System.out.println(Arrays.toString(array));
    }

    public static void sort() {
        buildHeap();
        for (int i = 0, len = array.length - 1; i <= len; i++) {
            swap(0, arrayLen);
            arrayLen--;
            maxHeap(0);
        }
    }

    public static void buildHeap() {
        arrayLen = array.length - 1;
        for (int len = array.length/2 - 1, i = len; i >= 0; i--) {
            maxHeap(i);
        }
    }

    public static void maxHeap(int cur) {
        int left = 2 * cur + 1;
        int right = 2 * cur + 2;
        int max = cur;
        if (left <= arrayLen && array[max] < array[left]) {
            max = left;
        }
        if (right <= arrayLen && array[max] < array[right]) {
            max = right;
        }
        if (cur != max) {
            swap(cur, max);
            maxHeap(max);
        }
    }

    public static void swap(int cur, int max) {
        int temp = array[cur];
        array[cur] = array[max];
        array[max] = temp;
    }
}
