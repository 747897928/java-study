package com.aquarius.wizard.leetcode.sort;

/**
 * @author zhaoyijie
 * @since 2024/7/8 12:23
 */
public class SelectionSort implements SortBase {

    @Override
    public int[] sort(int[] arr) {
        int[] a = new int[arr.length];
        System.arraycopy(arr, 0, a, 0, arr.length);
        selectionSort(a);
        return a;
    }

    /**
     * 选择排序，主要思想是每次从未排序的部分中选出最小的元素并将其放在已排序部分的末尾。
     *
     * @param arr
     */
    public void selectionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        // 遍历数组的每一个位置
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;  // 假设当前元素是最小值

            // 找到从当前位置到数组末尾的最小值
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // 如果找到的最小值不是当前位置，进行交换
            if (minIndex != i) {
                int tmp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = tmp;
            }
        }
    }
}
