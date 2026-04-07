package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


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
 * 备注
 *
 * 难度：简单。
 *
 * 考点：完全平方数判断。
 * 校对：题面稳定。
 * 提示：0 也是完全平方数。
 */
public class PerfectSquareBillCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int customerCount = scanner.nextInt();
        int[] billAmounts = new int[customerCount];
        for (int i = 0; i < customerCount; i++) {
            billAmounts[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int customerCount = 6;
         * int[] billAmounts = {25, 77, 54, 81, 48, 34};
         */

        PerfectSquareBillCount solver = new PerfectSquareBillCount();
        System.out.println(solver.countPerfectSquares(billAmounts));
    }

    /**
     * 判断一个数是不是完全平方数，最直接的方法就是：
     *
     * 1. 先求它的平方根整数部分 root
     * 2. 再检查 root * root 是否刚好等于原数
     *
     * 为什么这能成立？
     *
     * 因为如果 bill 是完全平方数，
     * 那一定存在某个整数 root，使得：
     *
     * root^2 = bill
     *
     * 而 Math.sqrt(bill) 会给出它的平方根。
     * 强转成 int 以后，相当于取平方根的整数部分。
     *
     * 所以最后再做一次 root * root == bill，
     * 就能把“刚好是完全平方数”和“只是接近平方数”区分开。
     *
     * 例如：
     *
     * - 25 -> sqrt = 5 -> 5*5 = 25，成立
     * - 26 -> sqrt ≈ 5.09 -> 强转后 root = 5 -> 5*5 = 25，不成立
     */
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
