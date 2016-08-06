package com.algorithms;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/26.
 */
public class InsertionSort {
    int[] array = {29, 10, 20, 4, 5, 3};

    @Test
    public void insertionSort() {
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
    public void compareSort() {
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

    @Test
    public void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int k = array[i];
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (k < array[j]) {
                    k = array[j];
                    min = j;
                }
            }
            array[min] = array[i];
            array[i] = k;
        }
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void selectionSortOptimize() {
        int len = array.length;
        int halfSwap = len >> 1;
        // 因为每次都是最小与最大一起替换，所以这里仅仅需要交换一半的位置即可
        for (int i = 0; i <= halfSwap; i++) {
            // 定义数组起始与结束下标
            int headIndex = i;
            int tailIndex = len - i - 1;
            // 定义最大、最小元素下标
            int minIndex = i;
            int maxIndex = tailIndex;
            // 初始化默认最大值与最小值
            int min = array[headIndex];
            int max = array[tailIndex];

            // 检查从本身元素开始，结束于最后元素，因为可能出现第一个元素就是最大值或者最后一个元素是最小值
            for (int j = headIndex; j <= tailIndex; j++) {
                if (array[j] < min) {
                    minIndex = j;
                    min = array[j];
                }
                if (array[j] > max) {
                    maxIndex = j;
                    max = array[j];
                }
            }
            array[minIndex] = array[headIndex];
            array[headIndex] = min;
            // 历史的第一个元素，可能就是最大元素，导致最小元素换位时把最大元素下标移位，所以需要修复最大元素下标
            if (array[minIndex] == max) {
                maxIndex = minIndex;
            }
            array[maxIndex] = array[tailIndex];
            array[tailIndex] = max;
        }
        System.out.println(Arrays.toString(array));
    }
}
