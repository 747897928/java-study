package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Question
 *
 * In a city there are N houses. Noddy is looking for a plot of land in the city on which to build
 * his house. He wants to buy the largest plot of land that will allow him to build the largest
 * possible house. All the houses in the city lie in a straight line and all of them have a house
 * number and a second number indicating the position of the house from the entry point in the city.
 * Noddy wants to find the houses between which he can build the largest possible house.
 *
 * Write an algorithm to help Noddy find the house numbers between which he can build his largest
 * possible house.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - num and val, representing
 * the number of houses (N) and the value val (where val is always equal to 2 representing the house
 * number (H) and the position of houses (P) for N houses).
 * The next N lines consist of two space-separated integers representing the house number (H_i) and
 * the position (P_i), respectively.
 *
 * Output
 *
 * Print two space-separated integers representing the house numbers in ascending order between which
 * the largest plot is available.
 *
 * Note
 *
 * No two houses have the same position. In the case of multiple possibilities, print the one with
 * the least distance from the reference point.
 *
 * Example
 *
 * Input:
 * 5 2
 * 3 7
 * 1 9
 * 2 0
 * 5 15
 * 4 30
 *
 * Output:
 * 4 5
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：按位置排序、相邻差值最大化。
 * 校对：题面稳定。
 * 提示：若间隔并列，优先选更靠近入口的那一对。
 */
public class LargestPlotBetweenHouses {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int houseCount = scanner.nextInt();
        int houseValueCount = scanner.nextInt();
        int[][] houses = new int[houseCount][houseValueCount];
        for (int i = 0; i < houseCount; i++) {
            for (int j = 0; j < houseValueCount; j++) {
                houses[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int houseCount = 5;
         * int houseValueCount = 2;
         * int[][] houses = {{3, 7}, {1, 9}, {2, 0}, {5, 15}, {4, 30}};
         */

        LargestPlotBetweenHouses solver = new LargestPlotBetweenHouses();
        System.out.println(format(solver.findHouseNumbers(houses)));
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

    /**
     * 这题的核心不是房号，而是“位置”。
     *
     * 最大可建 plot 一定只会出现在：
     *
     * “按位置排序后，相邻两栋房子之间”
     *
     * 为什么只看相邻房子就够了？
     *
     * 因为如果两栋房子中间还夹着别的房子，
     * 那么真正可用的空地会被这些中间房子切碎，
     * 不可能整段都拿来建房。
     *
     * 所以做法就是：
     *
     * 1. 先按位置排序
     * 2. 看每一对相邻房子的间隔
     * 3. 取 gap 最大的那一对
     * 4. 如果 gap 并列，取更靠近入口的那一对
     */
    public int[] findHouseNumbers(int[][] houses) {
        int[][] sorted = Arrays.copyOf(houses, houses.length);
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));

        int bestGap = -1;
        int[] bestPair = new int[2];
        int bestLeftPosition = Integer.MAX_VALUE;
        for (int i = 1; i < sorted.length; i++) {
            int gap = sorted[i][1] - sorted[i - 1][1];
            int leftPosition = sorted[i - 1][1];

            // gap 更大，说明这对相邻房子之间空地更大。
            // gap 相同则按题意选更靠近入口的，也就是 leftPosition 更小的。
            if (gap > bestGap || (gap == bestGap && leftPosition < bestLeftPosition)) {
                bestGap = gap;
                bestLeftPosition = leftPosition;
                bestPair[0] = Math.min(sorted[i - 1][0], sorted[i][0]);
                bestPair[1] = Math.max(sorted[i - 1][0], sorted[i][0]);
            }
        }
        return bestPair;
    }
}
