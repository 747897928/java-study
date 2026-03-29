package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * <p>create: 2020/10/14 21:29</p>
 *
 * @author zhaoyijie
 *
 * 冒泡排序（Bubble Sort）
 *
 * 你原来整理的原理保留：
 *
 * 冒泡排序是一种交换排序算法，它的思想原理是自下向上进行扫描，扫描过程中相邻关键词两两对比，
 * 若为逆序则进行交换。一次扫描结束后可以确定一个元素的位置。重复扫描直至没有记录需要交换为止。
 * 可以理解为每次循环将最大的数往后“沉”，每次循环都会少排序一个数，因为后面的数已经有序了。
 * 利用布尔变量 isSorted 作为标记，如果在本轮排序中没有发生交换，说明数列已经有序，可以提前结束。
 *
 * 核心画面：
 *
 * 每一轮都让当前未排序部分里最大的元素，一步一步“冒”到最右边。
 *
 * 例如：
 *
 * [5, 1, 4, 2, 8]
 *
 * 第一轮结束后，最大的 8 会到最后：
 *
 * [1, 4, 2, 5, 8]
 *
 * 所以第二轮就不需要再碰最后一个元素了。
 */
public class BubbleSort implements SortBase {

    public static void main(String[] args) {
        int[] numbers = {5, 1, 4, 2, 8};

        BubbleSort solver = new BubbleSort();
        System.out.println("input  = " + Arrays.toString(numbers));
        System.out.println("output = " + Arrays.toString(solver.sort(numbers)));
    }

    @Override
    public int[] sort(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = Arrays.copyOf(arr, arr.length);
        bubbleSort(copy);
        return copy;
    }

    /**
     * 你原来的边界思想其实很重要，这里保留并展开：
     *
     * 每一轮冒泡完成后，最右边都会多一个已经归位的最大元素。
     * 所以第二轮开始时，最后一个元素不用再比；
     * 第三轮开始时，最后两个元素都不用再比。
     *
     * 为什么内层是：
     *
     * for (int j = 0; j < arr.length - 1 - i; j++)
     *
     * 因为：
     *
     * 1. j 和 j + 1 要配对比较，所以 j 最大只能到倒数第二个位置
     * 2. 每完成一轮外层循环，最右边就多一个元素已经归位
     *
     * 所以：
     *
     * - 第一轮，内层比较到 n - 2
     * - 第二轮，内层比较到 n - 3
     * - 第 i 轮，内层比较到 n - 2 - i
     *
     * 这就是 `arr.length - 1 - i` 的来源。
     *
     * 你可以把 i 理解成：
     *
     * “右边已经排好序的元素个数”
     */
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            boolean sorted = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    sorted = false;
                }
            }
            if (sorted) {
                break;
            }
        }
    }
}
