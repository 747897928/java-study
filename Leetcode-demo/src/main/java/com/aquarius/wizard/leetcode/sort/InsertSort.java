package com.aquarius.wizard.leetcode.sort;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>description:  </p>
 * <p>create:  2020/11/20 16:37</p>
 *
 * @author zhaoyijie(AquariusGenius)
 */
public class InsertSort implements SortBase {

    @Override
    public int[] sort(int[] arr) {
        int[] a = new int[arr.length];
        System.arraycopy(arr, 0, a, 0, arr.length);
        insertSort(a);
        return a;
    }

    /**
     * 插入排序（Insertion-Sort）算法的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，
     * 找到相应位置并插入。插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），
     * 因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
     * ① 从第一个元素开始，该元素可以认为已经被排序
     * ② 取出下一个元素，在已经排序的元素序列中从后向前扫描
     * ③如果该元素（已排序）大于新元素，将该元素移到下一位置
     * ④ 重复步骤③，直到找到已排序的元素小于或者等于新元素的位置
     * ⑤将新元素插入到该位置后
     * ⑥ 重复步骤②~⑤
     * 平均时间复杂度：O(N^2)
     * 最差时间复杂度：O(N^2)
     * 空间复杂度：O(1)
     * 排序方式：In-place
     * 稳定性：稳定
     */
    public static void main(String[] args) {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(10);
        Executors.newSingleThreadExecutor();
    }

    // 插入排序
    public void insertSort(int[] arr) {
        // 检查数据合法性
        if (arr == null) {
            return;
        }
        int len = arr.length;
        if (len == 0) {
            return;
        }
        for (int i = 1; i < len; i++) {
            int tmp = arr[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                //如果比tmp大把值往后移动一位
                if (arr[j] > tmp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = tmp;
        }
    }

    // 插入排序改进：二分插入排序
    public void binaryInsertSort(int[] arr, int len) {
        int key, left, right, middle;
        for (int i = 1; i < len; i++) {
            key = arr[i];
            left = 0;
            right = i - 1;
            while (left <= right) {
                middle = (left + right) / 2;
                if (arr[middle] > key)
                    right = middle - 1;
                else
                    left = middle + 1;
            }

            for (int j = i - 1; j >= left; j--) {
                arr[j + 1] = arr[j];
            }
            //if (i - left >= 0) System.arraycopy(arr, left, arr, left + 1, i - left);

            arr[left] = key;
        }
    }
}
