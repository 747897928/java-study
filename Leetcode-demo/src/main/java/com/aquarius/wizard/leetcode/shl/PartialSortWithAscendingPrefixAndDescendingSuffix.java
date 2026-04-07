package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Question
 *
 * You are given a list of integers of size N. Write an algorithm to sort the first K elements (from
 * list[0] to list[K-1]) of the list in ascending order and the remaining (list[K] to list[N-1])
 * elements in descending order.
 *
 * Input
 *
 * The first line of the input consists of an integer - inputList_size, representing the number of
 * elements in the list (N).
 * The next line consists of N space-separated integers representing the elements of the list.
 * The last line consists of an integer - num, representing the number of elements to be sorted in
 * ascending order (K).
 *
 * Output
 *
 * Print N space-separated integers representing the sorted list.
 *
 * Example
 *
 * Input:
 * 8
 * 11 7 5 10 46 23 16 8
 * 3
 *
 * Output:
 * 5 7 11 46 23 16 10 8
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：分段排序。
 * 校对：题面稳定。
 * 提示：不是整体排序后切两段，而是两段各自独立排序。
 */
public class PartialSortWithAscendingPrefixAndDescendingSuffix {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] numbers = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            numbers[i] = scanner.nextInt();
        }
        int prefixLength = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int listSize = 8;
         * int[] numbers = {11, 7, 5, 10, 46, 23, 16, 8};
         * int prefixLength = 3;
         */

        PartialSortWithAscendingPrefixAndDescendingSuffix solver = new PartialSortWithAscendingPrefixAndDescendingSuffix();
        System.out.println(format(solver.partialSort(numbers, prefixLength)));
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    public int[] partialSort(int[] nums, int k) {
        /*
         * 这题最容易写错的点是：
         * 不是“整体排序后前 k 个升序、后面降序”，
         * 而是“前半段自己排，后半段自己排”。
         *
         * 所以步骤应该拆成：
         * 1. 复制原数组，避免直接改输入
         * 2. 对 [0, k) 这一段做升序排序
         * 3. 对 [k, n) 这一段也先做升序排序
         * 4. 再把后半段反转，得到降序
         *
         * Java 标准库的 Arrays.sort 对 int[] 的区间排序只能做升序，
         * 所以后半段要“先升序，再 reverse”。
         */
        int[] result = Arrays.copyOf(nums, nums.length);
        Arrays.sort(result, 0, k);
        Arrays.sort(result, k, result.length);
        reverse(result, k, result.length - 1);
        return result;
    }

    private void reverse(int[] nums, int left, int right) {
        /*
         * 这里是最普通的双指针反转：
         * 左右交换，然后向中间收缩。
         */
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
