package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * @author zhaoyijie
 * @since 2024/7/8 12:23
 *
 * 选择排序（Selection Sort）
 *
 * 你原来整理的原理保留：
 *
 * 选择排序的主要思想是每次从未排序的部分中选出最小的元素，
 * 并将其放在已排序部分的末尾。
 *
 * 核心画面：
 *
 * 每一轮在“未排序部分”里挑一个最小值，
 * 然后把它放到当前轮次应该在的位置 i。
 *
 * 它和冒泡排序很像，但本质不同：
 *
 * 1. 冒泡是相邻交换，把最大值慢慢推到后面
 * 2. 选择是先扫描找最小值，再一次性交换到前面
 */
public class SelectionSort implements SortBase {

    public static void main(String[] args) {
        int[] numbers = {64, 25, 12, 22, 11};

        SelectionSort solver = new SelectionSort();
        System.out.println("input  = " + Arrays.toString(numbers));
        System.out.println("output = " + Arrays.toString(solver.sort(numbers)));
    }

    @Override
    public int[] sort(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = Arrays.copyOf(arr, arr.length);
        selectionSort(copy);
        return copy;
    }

    /**
     * 外层 i 表示“当前位置应该放第 i 小的元素”。
     *
     * 这题真正要练的不是交换，而是“未排序区间的左边界在往右推进”。
     *
     * 所以：
     *
     * - arr[0..i-1] 已经排好
     * - arr[i..n-1] 还没排
     *
     * 内层 j 从 i + 1 开始，
     * 因为 minIndex 一开始就假设为 i 了，不需要再和自己比一次。
     */
    public void selectionSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
    }
}
