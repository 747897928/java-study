package com.aquarius.wizard.leetcode.shl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Question
 *
 * The manager of a supermarket wishes to hold an event at which he will distribute gift baskets to
 * lucky customers. Each gift basket contains a pair of products. Each basket contains different
 * product pairs, but the overall value of the baskets may be the same. There are N types of
 * products and each product has a price. The gift baskets will be awarded to the customers that
 * pick a product pair that has a difference in price equal to the given integer value K.
 *
 * Write an algorithm to help the manager find the total numbers of lucky customers who will win a
 * gift basket.
 *
 * Input
 *
 * The first line of the input consists of an integer list_input_size, representing the types of
 * products (N).
 * The second line consists of N space-separated integers list_input[0], list_input[1], ...
 * list_input[N-1], representing the price of the products.
 * The last line consists of an integer K_input, representing the given value K.
 *
 * Output
 *
 * Print an integer representing the total number of lucky customers who will win a gift basket.
 *
 * Constraints
 *
 * [The screenshot for constraints is missing.]
 *
 * Example
 *
 * [The screenshot for the example is missing.]
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：哈希计数、差值配对。
 * 校对：题目缺样例和约束，是高风险题。我这里采用的解释是“统计所有不同商品类型组成的无序对”，所以如果同价商品有多个，它们也会形成多个有效对。
 * 提示：若 K = 0，要统计同价商品之间的组合数；若 K > 0，可以用 freq[x] * freq[x + K]。
 */
public class ProductPairsWithPriceDifferenceK {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] prices = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            prices[i] = scanner.nextInt();
        }
        int difference = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int listSize = 6;
         * int[] prices = {1, 5, 3, 4, 2, 2};
         * int difference = 2;
         */

        ProductPairsWithPriceDifferenceK solver = new ProductPairsWithPriceDifferenceK();
        System.out.println(solver.countPairs(prices, difference));
    }

    public long countPairs(int[] prices, int difference) {
        if (difference < 0) {
            return 0L;
        }
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int price : prices) {
            frequency.merge(price, 1, Integer::sum);
        }

        long pairs = 0L;
        if (difference == 0) {
            for (int count : frequency.values()) {
                pairs += (long) count * (count - 1) / 2;
            }
            return pairs;
        }

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int price = entry.getKey();
            int nextCount = frequency.getOrDefault(price + difference, 0);
            pairs += (long) entry.getValue() * nextCount;
        }
        return pairs;
    }
}
