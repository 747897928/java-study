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
        int[] result = Arrays.copyOf(nums, nums.length);
        Arrays.sort(result, 0, k);
        Arrays.sort(result, k, result.length);
        reverse(result, k, result.length - 1);
        return result;
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
