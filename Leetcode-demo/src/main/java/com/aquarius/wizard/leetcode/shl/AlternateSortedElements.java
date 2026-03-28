package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * An alternate sort of a list consists of alternate elements (starting from the first position) of
 * the given list after sorting it in an ascending order. You are given a list of unsorted elements.
 * Write an algorithm to find the alternate sort of the given list.
 *
 * Input
 *
 * The first line of the input consists of an integer size, representing the size of the given list
 * (N).
 * The second line consists of N space-separated integers arr[0], arr[1], ..., arr[N-1],
 * representing the elements of the input list.
 *
 * Output
 *
 * Print space-separated integers representing the alternately sorted elements of the given list.
 *
 * Constraints
 *
 * 0 < size <= 10^6
 * -10^6 <= arr[i] <= 10^6
 * 0 <= i < size
 *
 * Example
 *
 * Input:
 * 8
 * 3 5 1 5 9 10 2 6
 *
 * Output:
 * 1 3 5 9
 *
 * Explanation:
 * After sorting, the list is [1, 2, 3, 5, 5, 6, 9, 10].
 * So, the alternate elements of the sorted list are [1, 3, 5, 9].
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：排序后按间隔取值。
 * 校对：题面稳定，示例无歧义。
 * 提示：这题不是交替输出最小最大，而是排序后取下标 0,2,4,... 的元素。
 */
public class AlternateSortedElements {

    public static void main(String[] args) {
        int listSize = 8;
        int[] numbers = {3, 5, 1, 5, 9, 10, 2, 6};

        if (numbers.length != listSize) {
            throw new IllegalArgumentException("numbers.length must equal listSize");
        }

        AlternateSortedElements solver = new AlternateSortedElements();
        System.out.println(format(solver.alternateSort(numbers)));
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

    public int[] alternateSort(int[] nums) {
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        int[] answer = new int[(sorted.length + 1) / 2];
        for (int i = 0, index = 0; i < sorted.length; i += 2) {
            answer[index++] = sorted[i];
        }
        return answer;
    }
}
