package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * A company is planning a big sale at which they will give their customers a special promotional
 * discount. Each customer that purchases a product from the company has a unique customerID numbered
 * from 0 to N-1. Andy, the marketing head of the company, has selected bill amounts of the N
 * customers for the promotional scheme. The discount will be given to the customers whose bill
 * amounts are perfect squares. The customers may use this discount on a future purchase.
 *
 * Write an algorithm to help Andy find the number of customers that will be given discounts.
 *
 * Input
 *
 * The first line of the input consists of an integer numOfCust representing the number of customers
 * whose bills are selected for the promotional discount (N).
 * The second line consists of N space-separated integers - bill0, bill1, ......, billN-1
 * representing the bill amounts of the N customers selected for the promotional discount.
 *
 * Output
 *
 * Print an integer representing the number of customers that will be given discounts.
 *
 * Example
 *
 * Input:
 * 6
 * 25 77 54 81 48 34
 *
 * Output:
 * 2
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：完全平方数判断。
 * 校对：题面稳定。
 * 提示：0 也是完全平方数。
 */
public class PerfectSquareBillCount {

    public int countPerfectSquares(int[] bills) {
        int count = 0;
        for (int bill : bills) {
            int root = (int) Math.sqrt(bill);
            if (root * root == bill) {
                count++;
            }
        }
        return count;
    }
}
