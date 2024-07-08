package com.aquarius.wizard.leetcode.sort;

/**
 * <p>description:  </p>
 * <p>create: 2020/10/14 21:29</p>
 *
 * @author :zhaoyijie
 */


public class BubbleSort implements SortBase {

    @Override
    public int[] sort(int[] arr) {
        int[] a = new int[arr.length];
        System.arraycopy(arr, 0, a, 0, arr.length);
        bubbleSort(a);
        return a;
    }

    /**
     * 冒泡排序是一种交换排序算法，他的思想原理是自下向上进行扫描，扫描过程中相邻关键词两两对比，
     * 若为逆序则进行记录交换，一次扫描结束后可以确定一个元素的位置。重复扫描直至没有记录需要交换为止。
     * 可以理解为每次循环将最大的数往后沉，每次循环都会少排序一个数，因为后面的数都有序了。
     * 利用布尔变量isSorted作为标记。如果在本轮排序中，元素有交换，则说明数列无序；如果没有元素交换，
     * 说明数列已然有序，直接跳出大循环。
     */
    public static void bubbleSort(int[] arr) {
        int tmp;
        for (int i = 0; i < arr.length; i++) {
            boolean isSorted = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }


}
