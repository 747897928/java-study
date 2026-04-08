package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 * <p>
 * You are given a list of integers and an integer K. Write an algorithm to find the number of
 * elements in the list that are strictly less than K.
 * <p>
 * Input
 * <p>
 * The first line of the input consists of an integer element_size, representing the number of
 * elements in the list (N).
 * The second line consists of N space-separated integers element[1], element[2], ..., element[N],
 * representing the list of integers.
 * The last line consists of an integer num, representing the integer to be compared (K).
 * <p>
 * Output
 * <p>
 * Print a positive integer representing the number of elements in the list that are strictly less
 * than num.
 * <p>
 * Constraints
 * <p>
 * -10^9 <= num <= 10^9
 * -10^9 <= element[i] <= 10^9
 * <p>
 * Example
 * <p>
 * Input:
 * 7
 * 1 7 4 5 6 3 2
 * 5
 * <p>
 * Output:
 * 4
 * <p>
 * Explanation:
 * The numbers that are less than 5 are 1, 2, 3, 4.
 * <p>
 * 备注
 * <p>
 * 难度：简单。
 * <p>
 * 考点：线性扫描、基础计数。
 * 校对：题面稳定。
 * 提示：这是最适合练输入输出和边界值的题之一。
 * <p>
 * positive integer正整数
 *
 * the number of 数量的
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class CountElementsStrictlyLessThanK {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] numbers = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            numbers[i] = scanner.nextInt();
        }
        int targetK = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int listSize = 7;
         * int[] numbers = {1, 7, 4, 5, 6, 3, 2};
         * int targetK = 5;
         */

        CountElementsStrictlyLessThanK solver = new CountElementsStrictlyLessThanK();
        System.out.println(solver.countLessThan(numbers, targetK));
        /*
         * 如果需要核对另一种写法，可以临时打开下面这行：
         * System.out.println(solver.countLessThan2(numbers, targetK));
         */
    }

    public int countLessThan(int[] nums, int target) {
        /*
         * 这题不需要排序，也不需要二分。
         *
         * Question只问“有多少个元素 < target”，并没有要求把这些元素找出来或按顺序输出。
         * 所以最直接的做法就是线性扫描一遍，看到一个满足条件的元素就把计数 +1。
         *
         * 时间复杂度 O(n)，空间复杂度 O(1)。
         * 对这种纯计数题，先问自己一句：
         * “我真的需要改变数组顺序吗？”
         * 如果答案是否定的，往往一趟扫描就够了。
         */
        int count = 0;
        for (int num : nums) {
            if (num < target) {
                count++;
            }
        }
        return count;
    }

    public int countLessThan2(int[] nums, int target) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target > nums[i]) {
                count++;
            }
        }
        return count;
    }
}
