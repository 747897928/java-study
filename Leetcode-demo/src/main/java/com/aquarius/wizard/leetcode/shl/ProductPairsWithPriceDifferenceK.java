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
 * 这题题面容易让人读别扭，因为它说的是“pair of products”，
 * 但输入只给了一串价格，没有给商品编号、没有给商品名称、也没有完整样例。
 *
 * 所以这里实际采用的解释是：
 *
 * - 输入数组里的每一个元素，代表一个商品
 * - 现在要从这些商品里选两个
 * - 如果这两个商品的价格差正好等于 K，就算一个有效商品对
 *
 * 也就是说，这里统计的不是“总价差额”，
 * 也不是“某种复杂礼包价值”，
 * 本质上就是：
 *
 * 统计满足 |a - b| = K 的商品对数量
 *
 * 因为这里后面的实现是按 price 和 price + K 来数，
 * 所以相当于固定只数一次，不会把 (1, 3) 和 (3, 1) 重复算两遍。
 *
 * 考点：哈希计数、差值配对。
 * 校对：题目缺样例和约束，是高风险题。
 *
 * 当前代码的解释口径：
 *
 * 1. 数组里的每个元素都当成一个独立商品
 * 2. 只统计无序对一次
 * 3. 如果 K = 0，就统计同价商品之间能组成多少对
 * 4. 如果 K > 0，就统计价格 x 和价格 x + K 能组成多少对
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

    /**
     * countPairs 的含义先说清楚：
     *
     * 这题不是在算“总价”。
     * 它算的是“两个商品的价格差是否等于 K”。
     *
     * 例如：
     *
     * prices = [1, 5, 3, 4, 2, 2]
     * K = 2
     *
     * 有效商品对包括：
     *
     * - (1, 3)
     * - (3, 5)
     * - (2, 4)
     * - 另一个 2 和 4 也能再组成一对
     *
     * 所以答案是 4。
     *
     * 这个方法的核心不是双重循环，而是先统计每个价格出现了多少次。
     *
     * 例如上面的频次表会变成：
     *
     * - 1 -> 1 次
     * - 2 -> 2 次
     * - 3 -> 1 次
     * - 4 -> 1 次
     * - 5 -> 1 次
     *
     * 然后分两种情况：
     *
     * 1. K = 0
     *
     *    说明要找“同价商品对”。
     *    如果某个价格出现了 count 次，
     *    那么它内部能组成的对数就是组合数：
     *
     *    count * (count - 1) / 2
     *
     *    这个公式其实就是“从 count 个东西里选 2 个”的组合数。
     *
     *    为什么会是这个样子：
     *
     *    - 第一个位置有 count 种选法
     *    - 第二个位置有 count - 1 种选法
     *
     *    所以先得到：
     *
     *    count * (count - 1)
     *
     *    但这样会把同一对算两遍。
     *
     *    例如：
     *
     *    - (a, b)
     *    - (b, a)
     *
     *    在这题里其实是同一对，
     *    所以最后要再除以 2 去重。
     *
     *    比如价格 2 出现 3 次，
     *    那就能组成 3 对：
     *    (第1个2, 第2个2)、(第1个2, 第3个2)、(第2个2, 第3个2)
     *
     * 2. K > 0
     *
     *    那就去看每个 price，是否存在 price + K。
     *
     *    如果：
     *
     *    - 价格 x 出现了 freq[x] 次
     *    - 价格 x + K 出现了 freq[x + K] 次
     *
     *    那么它们能组成的商品对数量就是：
     *
     *    freq[x] * freq[x + K]
     *
     *    例如：
     *
     *    - 2 出现 2 次
     *    - 4 出现 1 次
     *
     *    那么 (2, 4) 这类商品对就有：
     *
     *    2 * 1 = 2 对
     *
     *    这里为什么不需要再除以 2：
     *
     *    因为这已经不是“同一组里选两个”了，
     *    而是“从价格 x 这一组里选一个，再从价格 x + K 那一组里选一个”。
     *
     *    这是两个不同组之间的配对，
     *    本质上就是笛卡尔积，直接相乘就行。
     *
     *    只要代码固定只从 x 去找 x + K，
     *    就不会再反过来从 x + K 去找 x，
     *    所以不会出现重复计数。
     *
     * 为什么代码里只看 price + K，而不是同时看 price - K？
     *
     * 因为这样就能保证每对只统计一次。
     *
     * 否则如果一会儿从 1 找 3，一会儿又从 3 找 1，
     * 就会把同一对重复计算。
     */
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
