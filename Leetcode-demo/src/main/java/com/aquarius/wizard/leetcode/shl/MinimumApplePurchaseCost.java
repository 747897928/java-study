package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * Josh wants to buy exactly N apples.
 * Shop A sells apples only in lots of M1 apples, and each such lot costs P1.
 * Shop B sells apples only in lots of M2 apples, and each such lot costs P2.
 *
 * Write an algorithm to find the minimum price at which Josh can buy the apples.
 *
 * Input
 *
 * The first line of input consists of an integer - N, representing the total number of apples that
 * Josh wants to buy.
 * The second line consists of two space-separated positive integers - M1 and P1, representing the
 * number of apples in a lot and the lot's price at shop A, respectively.
 * The third line consists of two space-separated positive integers - M2 and P2, representing the
 * number of apples in a lot and the lot's price at shop B, respectively.
 *
 * Output
 *
 * Print a positive integer representing the minimum price at which Josh can buy the apples.
 *
 * Note
 *
 * There will always be at least one solution.
 * There is only one lot size for a particular shop.
 * There is an unlimited supply of apples for both the shops.
 *
 * Example
 *
 * Input:
 * 19
 * 3 10
 * 4 15
 *
 * Output:
 * 65
 *
 * Explanation:
 * Josh can buy five lots from the first shop and one lot from the second shop.
 * So the total price is (5 * 10 + 15) = 65.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：枚举、整除判断。
 * 校对：题干前半段来自 OCR 重建，但输入输出和样例是稳定的，样例已做代码校验。
 * 做法：枚举其中一种批量购买次数，检查剩余苹果是否能被另一种批量整除。
 * 相似题：两种批量规格凑整数目标、双变量方程枚举、两种硬币恰好凑出金额。
 *
 * 学这题最重要的不是“苹果”，而是识别出它其实是：
 *
 * M1 * x + M2 * y = N
 *
 * 在所有合法整数解里，找成本最小的那个。
 *
 * 为什么这题可以直接枚举：
 *
 * 1. 只有两种 lot size
 * 2. 每种 lot 都能无限买
 * 3. 题目只要求 exactly N
 *
 * 所以你只要固定一边，另一边就被“剩余量”唯一决定了。
 *
 * 也就是说，这题不是通用背包。
 * 它是一个更轻量的“枚举一边 + 检查另一边能否整除”的题。
 */
public class MinimumApplePurchaseCost {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int targetApples = scanner.nextInt();
        int lotSizeA = scanner.nextInt();
        int priceA = scanner.nextInt();
        int lotSizeB = scanner.nextInt();
        int priceB = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int targetApples = 19;
         * int lotSizeA = 3;
         * int priceA = 10;
         * int lotSizeB = 4;
         * int priceB = 15;
         */

        MinimumApplePurchaseCost solver = new MinimumApplePurchaseCost();
        System.out.println(solver.minimumCost(targetApples, lotSizeA, priceA, lotSizeB, priceB));
    }

    /**
     * 这里固定 countA，也就是先假设“Shop A 买了多少批”。
     *
     * 那么剩余苹果数就是：
     *
     * remaining = targetApples - countA * lotSizeA
     *
     * 如果 remaining 能被 lotSizeB 整除，
     * 说明此时确实存在一个合法的 countB。
     *
     * 于是每次枚举都只需要做两个判断：
     *
     * 1. remaining 是否 >= 0
     * 2. remaining % lotSizeB 是否 == 0
     *
     * 这题的学习重点是：
     *
     * 当题目只有两种选择、并且要求“恰好凑出”时，
     * 先别急着往 DP 想，先看能不能“枚举一边，解另一边”。
     */
    public long minimumCost(int targetApples, int lotSizeA, int priceA, int lotSizeB, int priceB) {
        long best = Long.MAX_VALUE;
        for (int countA = 0; countA * lotSizeA <= targetApples; countA++) {
            int remaining = targetApples - countA * lotSizeA;
            if (remaining % lotSizeB == 0) {
                long cost = (long) countA * priceA + (long) (remaining / lotSizeB) * priceB;
                best = Math.min(best, cost);
            }
        }
        return best;
    }
}
