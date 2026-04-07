package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Sheldon is going to a book fair where all the books are star-rated. As he is interested in just
 * two types of books, Horror and Sci-fi, so he would buy the books from these two categories only.
 * He would want to buy at least one book from each category so as to maximize the total star-rating
 * of his books. Also, the total price of the books should not exceed the amount of money that he
 * can spend. The output is -1 if it is not possible to buy at least one book from both the
 * categories with the money that he has.
 *
 * Write an algorithm to help Sheldon buy the books from both the categories.
 *
 * Input
 *
 * The first line of the input consists of an integer amount, representing the amount of money
 * Sheldon can spend.
 * The second line consists of two integers numHorror and numH, representing the number of Horror
 * books (H) and the number of values given for every horror book (X is always equal to 2,
 * respectively).
 * The next H lines consist of X space-separated integers hrating and hprice, representing the
 * star-rating and the price of each Horror book, respectively.
 * The next line consists of two space-separated integers numSciFi and numS, representing the number
 * of Sci-fi books (S) and the number of values given for every Sci-fi book (P is always equal to 2,
 * respectively).
 * The last S lines consist of P space-separated integers srating and sprice, representing the
 * star-rating and the price of each Sci-fi book, respectively.
 *
 * Output
 *
 * Print an integer representing the total maximum star-rating of books bought by Sheldon. If he
 * cannot buy at least one book from both the categories then print -1.
 *
 * Constraints
 *
 * 1 <= numHorror <= 1000
 * 1 <= numSciFi <= 1000
 * 1 <= amount <= 10^5
 * 1 <= hrating, srating <= 10^6
 * 1 <= hprice, sprice <= 10^5
 * numH = 2
 * numS = 2
 *
 * Visible example input from docx:
 * 50
 * 3 2
 * 5 10
 * 3 30
 * 6 20
 * 3 2
 * 6 30
 * 2 10
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：0/1 背包、状态压缩、预算约束下的最优选择。
 * 校对：docx 样例在末尾被截断，输出和最后若干行缺失；但核心题意足够稳定，可以按“每本书最多买一次”来建模。
 * 提示：关键不只是花费不超预算，还要保证 Horror 和 Sci-fi 两类都至少选到一本。
 */
public class MaximumTotalRatingWithHorrorAndSciFiBooks {

    private static final long NEGATIVE_INF = Long.MIN_VALUE / 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();
        int horrorBookCount = scanner.nextInt();
        int horrorValueCount = scanner.nextInt();
        int[][] horrorBooks = new int[horrorBookCount][horrorValueCount];
        for (int i = 0; i < horrorBookCount; i++) {
            for (int j = 0; j < horrorValueCount; j++) {
                horrorBooks[i][j] = scanner.nextInt();
            }
        }
        int sciFiBookCount = scanner.nextInt();
        int sciFiValueCount = scanner.nextInt();
        int[][] sciFiBooks = new int[sciFiBookCount][sciFiValueCount];
        for (int i = 0; i < sciFiBookCount; i++) {
            for (int j = 0; j < sciFiValueCount; j++) {
                sciFiBooks[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int amount = 20;
         * int horrorBookCount = 2;
         * int horrorValueCount = 2;
         * int[][] horrorBooks = {{5, 6}, {7, 10}};
         * int sciFiBookCount = 2;
         * int sciFiValueCount = 2;
         * int[][] sciFiBooks = {{4, 5}, {8, 12}};
         */

        MaximumTotalRatingWithHorrorAndSciFiBooks solver = new MaximumTotalRatingWithHorrorAndSciFiBooks();
        System.out.println(solver.maximumRating(amount, horrorBooks, sciFiBooks));
    }

    /**
     * 这题如果只看“预算不超过 amount，最大化 rating”，
     * 很容易直接想到普通 0/1 背包。
     *
     * 但真正麻烦的点是：
     *
     * - Horror 至少选一本
     * - Sci-fi 至少选一本
     *
     * 所以状态不能只记“花了多少钱”，
     * 还要记“目前两种类别的完成情况”。
     *
     * 这里用一个 2 bit 的 mask 表示：
     *
     * - 00：两类都还没选到
     * - 01：已经选到 Horror
     * - 10：已经选到 Sci-fi
     * - 11：两类都已经选到
     *
     * 然后：
     *
     * dp[mask][money]
     * = 花费恰好为 money，且类别覆盖状态为 mask 时，能拿到的最大总 rating
     *
     * 最终我们只关心 mask = 3，也就是两类都选到了的状态。
     */
    public long maximumRating(int amount, int[][] horrorBooks, int[][] sciFiBooks) {
        long[][] dp = new long[4][amount + 1];
        for (long[] row : dp) {
            Arrays.fill(row, NEGATIVE_INF);
        }

        // 花费 0，且两类都没选时，rating = 0。
        dp[0][0] = 0L;

        // 先把 Horror 书一本本加入状态。
        addBooks(dp, horrorBooks, 1);
        // 再把 Sci-fi 书一本本加入状态。
        addBooks(dp, sciFiBooks, 2);

        long best = NEGATIVE_INF;
        for (int money = 0; money <= amount; money++) {
            best = Math.max(best, dp[3][money]);
        }
        return best == NEGATIVE_INF ? -1L : best;
    }

    /**
     * 把某一类书逐本加入 DP。
     *
     * categoryMask：
     *
     * - Horror 传 1（二进制 01）
     * - Sci-fi 传 2（二进制 10）
     *
     * 加入一本书时，本质上就是普通 0/1 背包转移：
     *
     * - 花费增加这本书的 price
     * - rating 增加这本书的 rating
     * - mask 额外并上这本书所属类别
     *
     * 这里从大到小枚举 money，
     * 是标准 0/1 背包写法，目的就是防止同一本书在同一轮被重复选。
     */
    private void addBooks(long[][] dp, int[][] books, int categoryMask) {
        for (int[] book : books) {
            long rating = book[0];
            int price = book[1];
            for (int mask = 3; mask >= 0; mask--) {
                for (int money = dp[mask].length - 1; money >= price; money--) {
                    long previous = dp[mask][money - price];
                    if (previous == NEGATIVE_INF) {
                        continue;
                    }
                    // 选上这本书之后，对应类别会被“点亮”。
                    int nextMask = mask | categoryMask;
                    dp[nextMask][money] = Math.max(dp[nextMask][money], previous + rating);
                }
            }
        }
    }
}
