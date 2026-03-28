package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Write an algorithm to find the number of occurrences of needle in a given positive number
 * haystack.
 *
 * Input
 *
 * The first line of the input consists of an integer needle, representing a digit.
 * The second line consists of an integer haystack, representing the positive number.
 *
 * Output
 *
 * Print an integer representing the number of occurrences of needle in haystack.
 *
 * Constraints
 *
 * 0 <= needle <= 9
 * 0 <= haystack <= 99999999
 *
 * Example
 *
 * Input:
 * 2
 * 123228
 *
 * Output:
 * 3
 *
 * Explanation:
 * needle 2 occurs 3 times in the haystack.
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：按位遍历。
 * 校对：题面稳定。
 * 提示：别漏掉 haystack = 0 的特殊情况。
 */
public class CountDigitOccurrencesInNumber {

    public int countOccurrences(int digit, int number) {
        if (number == 0) {
            return digit == 0 ? 1 : 0;
        }
        int count = 0;
        int value = number;
        while (value > 0) {
            if (value % 10 == digit) {
                count++;
            }
            value /= 10;
        }
        return count;
    }
}
