package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are given a list of integers and an integer K. Write an algorithm to find the number of
 * elements in the list that are strictly less than K.
 *
 * Input
 *
 * The first line of the input consists of an integer element_size, representing the number of
 * elements in the list (N).
 * The second line consists of N space-separated integers element[1], element[2], ..., element[N],
 * representing the list of integers.
 * The last line consists of an integer num, representing the integer to be compared (K).
 *
 * Output
 *
 * Print a positive integer representing the number of elements in the list that are strictly less
 * than num.
 *
 * Constraints
 *
 * -10^9 <= num <= 10^9
 * -10^9 <= element[i] <= 10^9
 *
 * Example
 *
 * Input:
 * 7
 * 1 7 4 5 6 3 2
 * 5
 *
 * Output:
 * 4
 *
 * Explanation:
 * The numbers that are less than 5 are 1, 2, 3, 4.
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：线性扫描、基础计数。
 * 校对：题面稳定。
 * 提示：这是最适合练输入输出和边界值的题之一。
 */
public class CountElementsStrictlyLessThanK {

    public int countLessThan(int[] nums, int target) {
        int count = 0;
        for (int num : nums) {
            if (num < target) {
                count++;
            }
        }
        return count;
    }
}
