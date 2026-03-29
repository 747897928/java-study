package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * <p>create: 2020/11/20 15:32</p>
 *
 * @author zhaoyijie(AquariusGenius)
 *
 * 快速排序（Quick Sort）
 *
 * 你原来整理的原理保留：
 *
 * 快速排序在同为 O(n log n) 的几种排序方法中通常效率较高，
 * 它的基本思想是：
 *
 * 1. 先从数列中取出一个数作为基准数
 * 2. 分区过程：将比这个数大的数放到右边，小于或等于它的数放到左边
 * 3. 再对左右区间重复第二步，直到各区间只有一个数
 *
 * 你原来提到的“挖坑填数 + 分治法”这个说法是对的，
 * 这里继续沿用这个思路去解释 partition。
 *
 * 这版用的是“挖坑填数”的 partition 写法。
 *
 * 核心画面：
 *
 * 1. 先拿最左边元素当 pivot
 * 2. 这个位置暂时看成一个“坑”
 * 3. 从右边找比 pivot 小的数，拿来填左边的坑
 * 4. 再从左边找比 pivot 大或等于的数，拿来填右边的坑
 * 5. 最后 i == j 时，把 pivot 塞回这个最终坑位
 */
public class QuickSort implements SortBase {

    public static void main(String[] args) {
        int[] numbers = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};

        QuickSort solver = new QuickSort();
        System.out.println("input  = " + Arrays.toString(numbers));
        System.out.println("output = " + Arrays.toString(solver.sort(numbers)));
    }

    @Override
    public int[] sort(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = Arrays.copyOf(arr, arr.length);
        quickSort(copy);
        return copy;
    }

    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 原始整理保留：
     *
     * 快速排序排序效率在同为O(N*logN)的几种排序方法中效率较高,加上快速排序思想----分治法也确实实用
     * 该方法的基本思想是：
     * 1．先从数列中取出一个数作为基准数。
     * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
     * 3．再对左右区间重复第二步，直到各区间只有一个数。
     * 虽然快速排序称为分治法，但分治法这三个字显然无法很好的概括快速排序的全部步骤。因此我的对快速排序作
     * 了进一步的说明：挖坑填数+分治法：
     *
     * 这里的边界非常关键：
     *
     * 这也是快速排序最容易写崩的地方之一：
     * 不是思想不会，而是区间边界、pivot 落点、递归范围容易差 1。
     *
     * quickSort(arr, left, right)
     *
     * 表示只排闭区间 [left, right]。
     *
     * 所以递归时：
     *
     * pivot 最终落在 index = i
     *
     * 左边继续排：[left, i - 1]
     * 右边继续排：[i + 1, right]
     *
     * 为什么不是把 i 也继续递归进去？
     *
     * 因为 pivot 已经在最终正确位置了，不需要再碰。
     *
     * 你原始版本里的变量名是：
     *
     * s, l, r, i, j, x
     *
     * 我现在改成：
     *
     * arr, left, right, i, j, pivot
     *
     * 只是为了让第一次看的人更容易读懂：
     *
     * x -> pivot
     * l -> left
     * r -> right
     *
     * 算法本体没有换，还是同一个“挖坑填数”的 partition。
     */
    public void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int i = left;
        int j = right;
        int pivot = arr[left];

        while (i < j) {
            while (i < j && arr[j] >= pivot) {
                j--;
            }
            if (i < j) {
                arr[i++] = arr[j];
            }

            while (i < j && arr[i] < pivot) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }

        arr[i] = pivot;
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    /**
     * 原始代码写法保留版。
     *
     * 这组方法把你原来的变量名和写法完整留着：
     *
     * s, l, r, i, j, x
     *
     * 方便你以后直接对照“原始工程写法”和“我补的学习版写法”。
     */
    public void quickSortOriginalStyle(int[] s) {
        quickSortOriginalStyle(s, 0, s.length - 1);
    }

    /**
     * 原始代码写法保留版。
     *
     * 注意这版和上面的学习版实现，本质是同一个 partition 思路。
     * 只是：
     *
     * - 学习版变量名更直白
     * - 原始版变量名更短，更接近你最初整理时的代码风格
     */
    public void quickSortOriginalStyle(int[] s, int l, int r) {
        if (l < r) {
            int i = l;
            int j = r;
            int x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) {
                    j--;
                }
                if (i < j) {
                    s[i++] = s[j];
                }

                while (i < j && s[i] < x) {
                    i++;
                }
                if (i < j) {
                    s[j--] = s[i];
                }
            }
            s[i] = x;
            quickSortOriginalStyle(s, l, i - 1);
            quickSortOriginalStyle(s, i + 1, r);
        }
    }
}
