package com.aquarius.wizard.leetcode.sort;

import java.util.Arrays;

/**
 * <p>create: 2020/11/20 15:23</p>
 *
 * @author zhaoyijie(AquariusGenius)
 *
 * 归并排序（Merge Sort）
 *
 * 你原来整理的原理保留：
 *
 * 归并排序是创建在归并操作上的一种有效排序算法，是分治法（Divide and Conquer）的典型应用。
 * 它的思路简单，速度稳定，且是稳定排序算法。
 *
 * 分治过程分三步：
 *
 * 1. Divide：将 n 个元素分成两个子序列
 * 2. Conquer：对子序列递归排序
 * 3. Combine：把两个有序子序列合并成一个更大的有序序列
 *
 * 首先把一个未排序的序列从中间切成 2 部分，再把 2 部分切成 4 部分，依次分下去，
 * 直到每部分都只剩一个元素；然后再两两归并到一起，使之有序，不停归并，最后成为完整有序序列。
 *
 * 核心画面：
 *
 * 1. 先一直二分，直到每段只剩 1 个数
 * 2. 再把两个有序段合并成一个更大的有序段
 *
 * 所以这题最重要的是先把“区间”脑补出来。
 *
 * 例如：
 *
 * [38, 27, 43, 3, 9, 82, 10]
 *
 * 会先分成：
 *
 * [38, 27, 43, 3] | [9, 82, 10]
 *
 * 再继续分，直到每段只剩一个元素。
 */
public class MergeSort implements SortBase {

    public static void main(String[] args) {
        int[] numbers = {38, 27, 43, 3, 9, 82, 10};

        MergeSort solver = new MergeSort();
        System.out.println("input  = " + Arrays.toString(numbers));
        System.out.println("output = " + Arrays.toString(solver.sort(numbers)));
    }

    @Override
    public int[] sort(int[] arr) {
        return mergeSort(arr);
    }

    /**
     * 原始整理保留：
     *
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
     *
     * 保留你原来的静态入口：
     *
     * mergeSort(arr)
     *
     * 这样既保留旧写法，也能和现在的学习注释共存。
     */
    public static int[] mergeSort(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] copy = Arrays.copyOf(arr, arr.length);
        int[] helper = new int[copy.length];
        mergeSortRecursive(copy, helper, 0, copy.length - 1);
        return copy;
    }

    /**
     * 这里处理的是闭区间 [start, end]。
     *
     * 这也是归并排序最容易写错的地方之一：
     * 你必须先统一“我处理的是闭区间还是半开区间”，否则 mid 和递归边界很容易乱。
     *
     * 所以：
     *
     * - 终止条件是 start >= end，表示区间长度 <= 1
     * - 中点是 mid
     * - 左半段是 [start, mid]
     * - 右半段是 [mid + 1, end]
     *
     * 为什么右边要从 mid + 1 开始？
     *
     * 因为 mid 已经属于左半段了。
     * 如果右边也从 mid 开始，就会重叠。
     *
     * 这里还有一个位运算细节：
     *
     * int mid = start + ((end - start) >>> 1);
     *
     * 对小白来说，可以先把它直接理解成：
     *
     * int mid = start + (end - start) / 2;
     *
     * 这里的 `>>> 1` 就是“无符号右移一位”，对非负整数来说，效果和“除以 2”一样。
     *
     * 你可能会忘记 `>>` 和 `>>>` 的区别，这里单独记：
     *
     * 1. `>>`  是带符号右移
     * 2. `>>>` 是无符号右移
     *
     * 最直白的理解：
     *
     * - `>>` 右移时，会把符号位一起考虑进去
     * - `>>>` 右移时，左边空出来的位置一律补 0
     *
     * 在这里 `end - start` 一定是 >= 0 的，
     * 所以用 `>> 1` 和 `>>> 1` 在结果上没区别。
     *
     * 我这里写 `>>> 1`，
     * 只是为了明确表达“这里就是在安全地取一半”。
     */
    public static void mergeSortRecursive(int[] arr, int[] helper, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = start + ((end - start) >>> 1);
        mergeSortRecursive(arr, helper, start, mid);
        mergeSortRecursive(arr, helper, mid + 1, end);
        merge(arr, helper, start, mid, end);
    }

    /**
     * 原始代码写法保留版。
     *
     * 这组方法不是为了替代当前实现，
     * 而是为了把你原来整理时的代码风格完整留在文件里，方便以后对照。
     */
    public static int[] mergeSortOriginalStyle(int[] arr) {
        int len = arr.length;
        int[] result = new int[len];
        mergeSortRecursiveOriginalStyle(arr, result, 0, len - 1);
        return result;
    }

    /**
     * 原始代码写法保留版。
     *
     * 这里保留你原来的变量名：
     *
     * len, mid, start1, end1, start2, end2, k
     *
     * 这样你以后如果想回看自己最初是怎么理解归并排序的，
     * 可以直接对照这版。
     *
     * 这里的 `(len >> 1)` 也是“除以 2 取中间”的意思。
     * 因为 len >= 0，所以 `>> 1` 在这里很好理解成“整除 2”。
     */
    public static void mergeSortRecursiveOriginalStyle(int[] arr, int[] result, int start, int end) {
        if (start >= end) {
            return;
        }
        int len = end - start;
        int mid = (len >> 1) + start;
        int start1 = start;
        int end1 = mid;
        int start2 = mid + 1;
        int end2 = end;
        mergeSortRecursiveOriginalStyle(arr, result, start1, end1);
        mergeSortRecursiveOriginalStyle(arr, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2) {
            result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        }
        while (start1 <= end1) {
            result[k++] = arr[start1++];
        }
        while (start2 <= end2) {
            result[k++] = arr[start2++];
        }
        for (k = start; k <= end; k++) {
            arr[k] = result[k];
        }
    }

    /**
     * 原始实现思路对应的变量关系：
     *
     * int len = end - start, mid = (len >> 1) + start;
     * int start1 = start, end1 = mid;
     * int start2 = mid + 1, end2 = end;
     *
     * 现在我把它拆成了更直白的名字：
     *
     * left  = start
     * right = mid + 1
     * write = start
     *
     * 本质没有变，只是把“两个有序段归并”的过程拆得更易读。
     *
     * 合并两个已经有序的闭区间：
     *
     * 你原来“归并”这个概念本身很重要，这里保留并说透：
     *
     * 归并不是重新排序整个区间，
     * 而是在“左右两段已经各自有序”的前提下，像拉链一样把它们合成一个更大的有序段。
     *
     * 左边：[start, mid]
     * 右边：[mid + 1, end]
     *
     * 指针变化：
     *
     * left  从 start 开始
     * right 从 mid + 1 开始
     * write 从 start 开始往 helper 里写
     *
     * 每次比较 arr[left] 和 arr[right]：
     *
     * 谁小，就先写谁，然后那个指针往后走。
     */
    private static void merge(int[] arr, int[] helper, int start, int mid, int end) {
        int left = start;
        int right = mid + 1;
        int write = start;

        while (left <= mid && right <= end) {
            if (arr[left] <= arr[right]) {
                helper[write++] = arr[left++];
            } else {
                helper[write++] = arr[right++];
            }
        }

        while (left <= mid) {
            helper[write++] = arr[left++];
        }
        while (right <= end) {
            helper[write++] = arr[right++];
        }

        for (int i = start; i <= end; i++) {
            arr[i] = helper[i];
        }
    }
}
