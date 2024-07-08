package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/20 15:23</p>
 *
 * @author zhaoyijie(AquariusGenius)
 */
public class MergeSort implements SortBase {

    @Override
    public int[] sort(int[] arr) {
        return mergeSort(arr);
    }

    /**
     * 归并排序，是创建在归并操作上的一种有效的排序算法。算法是采用分治法（Divide and Conquer）的一个非常典型
     * 的应用，且各层分治递归可以同时进行。归并排序思路简单，速度仅次于快速排序，为稳定排序算法，
     * 一般用于对总体无序，但是各子项相对有序的数列。
     * 1. 基本思想
     * 归并排序是用分治思想，分治模式在每一层递归上有三个步骤：
     * 分解（Divide）：将n个元素分成个含n/2个元素的子序列。
     * 解决（Conquer）：用合并排序法对两个子序列递归的排序。
     * 合并（Combine）：合并两个已排序的子序列已得到排序结果。
     * 首先把一个未排序的序列从中间分割成2部分，再把2部分分成4部分，依次分割下去，直到分割成一个一个的数据，
     * 再把这些数据两两归并到一起，使之有序，不停的归并，最后成为一个排好序的序列。
     */
    public static int[] mergeSort(int[] arr) {
        int len = arr.length;
        int[] result = new int[len];
        mergeSortRecursive(arr, result, 0, len - 1);
        return result;
    }

    // 归并排序（Java-递归版）
    public static void mergeSortRecursive(int[] arr, int[] result, int start, int end) {
        if (start >= end)
            return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        mergeSortRecursive(arr, result, start1, end1);
        mergeSortRecursive(arr, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        while (start1 <= end1)
            result[k++] = arr[start1++];
        while (start2 <= end2)
            result[k++] = arr[start2++];
        for (k = start; k <= end; k++)
            arr[k] = result[k];
    }


}
