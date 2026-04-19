package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 1444
 * Number of Ways of Cutting a Pizza
 *
 * 给定披萨矩阵 pizza，'A' 表示苹果，'.' 表示空。
 * 要把披萨切成 k 块，每一块都至少包含一个苹果。
 * 问总共有多少种切法。
 *
 * 笔记
 *
 * 这题的核心不是“怎么切”，而是“某个子矩形里还有没有苹果”。
 *
 * 所以第一步通常都是先做二维前缀 / 后缀计数，
 * 让我们能 O(1) 判断：
 *
 * 从 (r, c) 到右下角这个子矩形里还有几个苹果。
 *
 * 然后再做 DP：
 *
 * dp(cutsLeft, r, c)
 * 表示：从左上角在 (r, c) 的这块剩余披萨开始，
 * 还要再切 cutsLeft 次时，有多少种合法切法。
 *
 * 常见两种写法：
 *
 * 1. 记忆化搜索
 * 2. bottom-up DP
 *
 * 正式主解我这里用 top-down。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class NumberOfWaysOfCuttingAPizza {

    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        String[] pizza = new String[rows];
        for (int i = 0; i < rows; i++) {
            pizza[i] = scanner.next();
        }
        int k = scanner.nextInt();

        NumberOfWaysOfCuttingAPizza solver = new NumberOfWaysOfCuttingAPizza();
        System.out.println(solver.ways(pizza, k));
    }

    public int ways(String[] pizza, int k) {
        return waysTopDown(pizza, k);
    }

    public int waysTopDown(String[] pizza, int k) {
        int rows = pizza.length;
        int cols = pizza[0].length();
        int[][] apples = buildSuffixApples(pizza);
        int[][][] memo = new int[k][rows][cols];
        for (int cut = 0; cut < k; cut++) {
            for (int r = 0; r < rows; r++) {
                Arrays.fill(memo[cut][r], -1);
            }
        }
        return dfs(apples, 0, 0, k - 1, memo);
    }

    private int dfs(int[][] apples, int row, int col, int cutsLeft, int[][][] memo) {
        if (apples[row][col] == 0) {
            return 0;
        }
        if (cutsLeft == 0) {
            return 1;
        }
        if (memo[cutsLeft][row][col] != -1) {
            return memo[cutsLeft][row][col];
        }

        long ways = 0L;
        int rows = apples.length - 1;
        int cols = apples[0].length - 1;

        for (int nextRow = row + 1; nextRow < rows; nextRow++) {
            if (apples[row][col] - apples[nextRow][col] > 0) {
                ways += dfs(apples, nextRow, col, cutsLeft - 1, memo);
            }
        }
        for (int nextCol = col + 1; nextCol < cols; nextCol++) {
            if (apples[row][col] - apples[row][nextCol] > 0) {
                ways += dfs(apples, row, nextCol, cutsLeft - 1, memo);
            }
        }

        memo[cutsLeft][row][col] = (int) (ways % MOD);
        return memo[cutsLeft][row][col];
    }

    public int waysBottomUp(String[] pizza, int k) {
        int rows = pizza.length;
        int cols = pizza[0].length();
        int[][] apples = buildSuffixApples(pizza);
        int[][] dp = new int[rows][cols];

        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                dp[r][c] = apples[r][c] > 0 ? 1 : 0;
            }
        }

        for (int pieces = 2; pieces <= k; pieces++) {
            int[][] next = new int[rows][cols];
            for (int r = rows - 1; r >= 0; r--) {
                for (int c = cols - 1; c >= 0; c--) {
                    long count = 0L;
                    for (int nr = r + 1; nr < rows; nr++) {
                        if (apples[r][c] - apples[nr][c] > 0) {
                            count += dp[nr][c];
                        }
                    }
                    for (int nc = c + 1; nc < cols; nc++) {
                        if (apples[r][c] - apples[r][nc] > 0) {
                            count += dp[r][nc];
                        }
                    }
                    next[r][c] = (int) (count % MOD);
                }
            }
            dp = next;
        }

        return dp[0][0];
    }

    private int[][] buildSuffixApples(String[] pizza) {
        int rows = pizza.length;
        int cols = pizza[0].length();
        int[][] apples = new int[rows + 1][cols + 1];
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = cols - 1; c >= 0; c--) {
                apples[r][c] =
                    apples[r + 1][c] + apples[r][c + 1] - apples[r + 1][c + 1]
                        + (pizza[r].charAt(c) == 'A' ? 1 : 0);
            }
        }
        return apples;
    }
}
