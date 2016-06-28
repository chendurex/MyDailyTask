package com.algorithms;

import com.sun.xml.internal.messaging.saaj.soap.ver1_2.DetailEntry1_2Impl;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/28.
 */
public class ForkJoinSort {
    private int[] array = {1, 9, 18, 25, 4, 6, 2, 99,50};

    @Test
    public void forkJoinSort() {
        //System.out.println(Arrays.toString(mergeArray(new int[]{1,3,7,8,20,23},new int[]{2,5,9,10,19})));
        System.out.println(Arrays.toString(newArray(array)));
    }

    private int[] newArray(int[] array) {
        if (array.length == 1) {
            return array;
        } else if (array.length == 2) {
            return baseSort(array[0],array[1]);
        } else {
            int middleLen = array.length >> 1;
            int array1Len = middleLen;
            int array2Len = array.length - middleLen;
            int []array1 = new int[array1Len];
            int []array2 = new int[array2Len];
            for (int i = 0;i < array1Len;i++) {
                array1[i] = array[i];
            }
            for (int k = 0;k < array2Len; k++) {
                array2[k] = array[k + middleLen];
            }
            return mergeArray(newArray(array1),newArray(array2));
        }
    }

    private int[] baseSort(int i, int k) {
        if (i < k) {
            return new int[] {i,k};
        } else {
            return new int[] {k,i};
        }
    }

    private int[] mergeArray(int[] array1, int[] array2) {
        int array1Len = array1.length;
        int array2Len = array2.length;
        int mergeArrayLen = array1Len + array2Len;
        int[] mergeArray = new int[mergeArrayLen];
        for (int mergeIndex = 0,array1Index = 0,array2Index = 0; mergeIndex < mergeArrayLen; mergeIndex++) {
            if (array1Index == array1Len) {
                while (array2Index < array2Len) {
                    mergeArray[mergeIndex] = array2[array2Index];
                    array2Index ++;
                    mergeIndex ++;
                }
            } else if (array2Index == array2Len){
                while (array1Index < array1Len) {
                    mergeArray[mergeIndex] = array1[array1Index];
                    array1Index ++;
                    mergeIndex ++;
                }
            } else if (array1[array1Index] > array2[array2Index]) {
                mergeArray[mergeIndex] = array2[array2Index];
                array2Index ++;
            } else {
                mergeArray[mergeIndex] = array1[array1Index];
                array1Index ++;
            }
        }
        return mergeArray;
    }
}
