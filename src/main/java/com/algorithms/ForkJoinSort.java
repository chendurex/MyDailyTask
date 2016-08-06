package com.algorithms;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ForkJoinSort {
    private static int[] array = {1, 9, 18, 25, 4, 6, 2, 99, 50};

    private static int[] originArray;
    private static int[] compareArray;

    public static void main(String[] args) {
        sort(array);
        System.out.println(Arrays.toString(array));
    }


    public static void sort(int[] array) {
        originArray = array;
        int len = originArray.length;
        // according to variant either/or:
        compareArray = new int[(len + 1) / 2];
        mergeSort(0, len - 1);
    }

    private static void mergeSort(int offset, int limit) {
        if (offset < limit) {
            int middle = (offset + limit) / 2;
            mergeSort(offset, middle);
            mergeSort(middle + 1, limit);
            merge(offset, middle, limit);
        }
    }

    private static void merge(int offset, int middle, int limit) {
        int originIndex = offset;
        int compareIndex = 0;
        // 将原始数组的offset到middle间的元素复制到将要比较的数组
        while (originIndex <= middle) {
            compareArray[compareIndex++] = originArray[originIndex++];
        }
        compareIndex = 0;
        // 再次定义新的offset元素起始值，用于记录当前有多少元素已经排序
        int originResetIndex = offset;
        // 将新的数组与原数组比较排序
        // originArray like  1 15 29 2 15 30
        // compareArray like 1 15 29
        // 这里因为compareArray是originArray复制过来的一份副本，所以如果compareArray比较小则不需要替换，直接下标++即可
        while (originResetIndex < originIndex && originIndex <= limit) {
            if (compareArray[compareIndex] > originArray[originIndex]) {
                originArray[originResetIndex++] = originArray[originIndex++];
            } else {
                //originArray[originResetIndex++] = compareArray[compareIndex++];
                originResetIndex++;
                compareIndex++;
            }
        }
        // 如果originRestIndex比originIndex小，则说明compareArray有比较大的元素未替换，需要补全
        while (originResetIndex < originIndex) {
            originArray[originResetIndex++] = compareArray[compareIndex++];
        }
    }
}
