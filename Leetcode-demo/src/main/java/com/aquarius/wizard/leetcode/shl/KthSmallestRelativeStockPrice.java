package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;

/**
 * Question
 *
 * Andrew is a stock trader who trades in N selected stocks. He has calculated the relative stock
 * price changes in the N stocks from the previous day stock prices. Now, his lucky number is K, so
 * he wishes to invest in the particular stock that has the Kth smallest relative stock value.
 *
 * Write an algorithm for Andrew to find the Kth smallest stock price out of the selected N stocks.
 *
 * Input
 *
 * The first line of the input consists of an integer stock_size, representing the number of
 * selected stocks (N).
 * The second line consists of N space-separated integers stock[0], stock[1], ..., stock[N-1],
 * representing the relative stock prices of the selected stocks.
 * The third line consists of an integer valueK, representing the value K for which he wishes to
 * find the stock price.
 *
 * Output
 *
 * Print an integer representing the Kth smallest stock price of selected N stocks.
 *
 * Constraints
 *
 * 0 < valueK <= stock_size <= 10^6
 * 0 <= stock[i] <= 10^6
 * 0 <= i < stock_size
 *
 * Example
 *
 * Input:
 * 5
 * 10 5 7 88 19
 * 3
 *
 * Output:
 * 10
 *
 * Explanation:
 * The sorted relative stock prices are [5, 7, 10, 19, 88].
 * So, the 3rd smallest stock price is 10.
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：排序、选择算法。
 * 校对：题面稳定。
 * 提示：学习阶段先写排序版最直观，后面再补 quickselect 也不迟。
 */
public class KthSmallestRelativeStockPrice {

    public static void main(String[] args) {
        int stockCount = 5;
        int[] stockPrices = {10, 5, 7, 88, 19};
        int targetRank = 3;

        if (stockPrices.length != stockCount) {
            throw new IllegalArgumentException("stockPrices.length must equal stockCount");
        }

        KthSmallestRelativeStockPrice solver = new KthSmallestRelativeStockPrice();
        System.out.println(solver.kthSmallest(stockPrices, targetRank));
    }

    public int kthSmallest(int[] stockPrices, int k) {
        int[] sorted = Arrays.copyOf(stockPrices, stockPrices.length);
        Arrays.sort(sorted);
        return sorted[k - 1];
    }
}
