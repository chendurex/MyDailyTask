package com.algorithms;

/**
 * Created by Administrator on 2016/8/6.
 */

import java.util.Arrays;

/* Class HeapSort */
public class HeapSort {
    private static int[] array = {1, 9, 18, 25, 4, 6, 2, 99, 50};
    private static int len;

    public static void main(String[] args) {
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    /* Sort Function */
    public static void sort(int arr[]) {
        heapify(arr);
        for (int i = len; i > 0; i--) {
            swap(arr, 0, i);
            len = len - 1;
            maxheap(arr, 0);
        }
    }

    /* Function to build a heap */
    public static void heapify(int arr[]) {
        len = arr.length - 1;
        for (int i = len / 2; i >= 0; i--)
            maxheap(arr, i);
    }

    /* Function to swap largest element in heap */
    public static void maxheap(int arr[], int i) {
        int left = 2 * i;
        int right = 2 * i + 1;
        int max = i;
        if (left <= len && arr[left] > arr[i])
            max = left;
        if (right <= len && arr[right] > arr[max])
            max = right;

        if (max != i) {
            swap(arr, i, max);
            maxheap(arr, max);
        }
    }

    public static void swap(int arr[], int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
