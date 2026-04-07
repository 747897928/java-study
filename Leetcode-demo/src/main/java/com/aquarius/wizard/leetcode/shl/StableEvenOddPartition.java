package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * You are playing an online game. In the game, a list of N numbers is given. The player has to
 * arrange the numbers so that all the odd numbers of the list come after the even numbers.
 *
 * Write an algorithm to arrange the given list such that all the odd numbers of the list come after
 * the even numbers.
 *
 * Input
 *
 * The first line of the input consists of an integer num, representing the size of the list (N).
 * The second line of the input consists of N space-separated integers representing the values of the
 * list.
 *
 * Output
 *
 * Print N space-separated integers such that all the odd numbers of the list come after the even
 * numbers.
 *
 * Note
 *
 * The relative order of odd numbers and the relative order of even numbers in the output should be
 * same as given in the input.
 *
 * Example
 *
 * Input:
 * 8
 * 10 98 3 33 12 22 21 11
 *
 * Output:
 * 10 98 12 22 3 33 21 11
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：稳定分组。
 * 校对：题面稳定。
 * 提示：不能直接做普通原地交换，因为题目要求相对顺序不变。
 */
public class StableEvenOddPartition {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] numbers = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            numbers[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int listSize = 8;
         * int[] numbers = {10, 98, 3, 33, 12, 22, 21, 11};
         */

        StableEvenOddPartition solver = new StableEvenOddPartition();
        System.out.println(format(solver.rearrange(numbers)));
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

    public int[] rearrange(int[] nums) {
        /*
         * 题目要求的不只是“偶数在前、奇数在后”，
         * 还额外要求：
         * - 偶数之间的相对顺序不能乱
         * - 奇数之间的相对顺序也不能乱
         *
         * 这就说明不能用普通的双指针原地交换。
         * 因为交换虽然能分组，但会把原顺序打乱。
         *
         * 最稳的做法是两趟扫描：
         * 1. 第一趟先把所有偶数按原顺序放进结果数组
         * 2. 第二趟再把所有奇数按原顺序接在后面
         *
         * 这就是稳定分组（stable partition）的最直观写法。
         */
        int[] result = new int[nums.length];
        int index = 0;
        for (int num : nums) {
            if ((num & 1) == 0) {
                result[index++] = num;
            }
        }
        for (int num : nums) {
            if ((num & 1) == 1) {
                result[index++] = num;
            }
        }
        return result;
    }
}
