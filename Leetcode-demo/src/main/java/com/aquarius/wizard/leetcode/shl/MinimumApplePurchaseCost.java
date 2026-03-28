package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：枚举、整除判断。
 * 校对：题干前半段来自 OCR 重建，但输入输出和样例是稳定的，样例已做代码校验。
 * 做法：枚举其中一种批量购买次数，检查剩余苹果是否能被另一种批量整除。
 */
public class MinimumApplePurchaseCost {

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
