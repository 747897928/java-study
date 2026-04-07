package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


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
 * 备注
 *
 * 难度：简单。
 *
 * 考点：按位遍历。
 * 校对：题面稳定。
 * 提示：别漏掉 haystack = 0 的特殊情况。
 */
public class CountDigitOccurrencesInNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int targetDigit = scanner.nextInt();
        int sourceNumber = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int targetDigit = 2;
         * int sourceNumber = 123228;
         */

        CountDigitOccurrencesInNumber solver = new CountDigitOccurrencesInNumber();
        System.out.println(solver.countOccurrences(targetDigit, sourceNumber));
    }

    /**
     * 这题是最标准的“按十进制一位一位拆数字”。
     *
     * 核心动作只有两个：
     *
     * 1. value % 10
     *    拿到当前最低位
     *
     * 2. value /= 10
     *    把当前最低位删掉，继续处理下一位
     *
     * 所以循环每一轮其实都在做：
     *
     * - 先看最低位是不是目标 digit
     * - 是的话 count++
     * - 再把这个最低位去掉
     *
     * 这里最容易漏掉的特例是 number = 0。
     *
     * 因为正常 while (value > 0) 根本不会进循环，
     * 但十进制数字 0 本身其实包含一个字符 '0'。
     *
     * 所以：
     *
     * - 如果 number = 0 且 digit = 0，答案应该是 1
     * - 如果 number = 0 且 digit != 0，答案才是 0
     */
    public int countOccurrences(int digit, int number) {
        if (number == 0) {
            return digit == 0 ? 1 : 0;
        }
        int count = 0;
        int value = number;
        while (value > 0) {
            // 取当前最低位。
            if (value % 10 == digit) {
                count++;
            }
            // 删掉当前最低位，继续看下一位。
            value /= 10;
        }
        return count;
    }
}
