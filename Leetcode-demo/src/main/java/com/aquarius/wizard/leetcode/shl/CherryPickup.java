package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 741
 * Cherry Pickup
 *
 * 给定 n x n 网格，1 表示樱桃，0 表示空地，-1 表示障碍。
 * 从左上角走到右下角，再从右下角回到左上角，
 * 经过有樱桃的位置可以拿走樱桃，每个樱桃只能拿一次。
 *
 * 笔记
 *
 * 这题表面上是“两趟路”，
 * 但更容易写对的视角是：
 *
 * 把它看成“两个人同时从左上走到右下”。
 *
 * 为什么能这么转？
 *
 * 因为来回两趟走的总步数结构是一样的，
 * 把回程反过来看，就等价于两个人同步向右 / 向下走。
 *
 * 于是状态就可以写成：
 *
 * - 第一个人走到 (r1, c1)
 * - 第二个人走到 (r2, c2)
 * - 并且两个人已经走了同样多的步数
 *
 * 因为步数相同，所以：
 *
 * r1 + c1 = r2 + c2
 *
 * 这样四维状态就能降成三维。
 *
 * 常见两种写法：
 *
 * 1. 记忆化搜索
 * 2. 按步数做 bottom-up DP
 *
 * 正式主解我这里用记忆化搜索。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class CherryPickup {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        CherryPickup solver = new CherryPickup();
        System.out.println(solver.cherryPickup(grid));
    }

    public int cherryPickup(int[][] grid) {
        return cherryPickupMemo(grid);
    }

    public int cherryPickupMemo(int[][] grid) {
        int n = grid.length;
        int[][][] memo = new int[n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(memo[i][j], Integer.MIN_VALUE);
            }
        }

        int answer = dfs(grid, 0, 0, 0, memo);
        return Math.max(0, answer);
    }

    private int dfs(int[][] grid, int r1, int c1, int r2, int[][][] memo) {
        int n = grid.length;
        int c2 = r1 + c1 - r2;

        if (r1 >= n || c1 >= n || r2 >= n || c2 >= n) {
            return Integer.MIN_VALUE / 4;
        }
        if (grid[r1][c1] == -1 || grid[r2][c2] == -1) {
            return Integer.MIN_VALUE / 4;
        }
        if (r1 == n - 1 && c1 == n - 1) {
            return grid[r1][c1];
        }
        if (memo[r1][c1][r2] != Integer.MIN_VALUE) {
            return memo[r1][c1][r2];
        }

        int cherries = grid[r1][c1];
        if (r1 != r2 || c1 != c2) {
            cherries += grid[r2][c2];
        }

        int bestNext = Math.max(
            Math.max(dfs(grid, r1 + 1, c1, r2 + 1, memo), dfs(grid, r1 + 1, c1, r2, memo)),
            Math.max(dfs(grid, r1, c1 + 1, r2 + 1, memo), dfs(grid, r1, c1 + 1, r2, memo))
        );

        cherries += bestNext;
        memo[r1][c1][r2] = cherries;
        return cherries;
    }

    public int cherryPickupBottomUp(int[][] grid) {
        int n = grid.length;
        int minValue = Integer.MIN_VALUE / 4;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], minValue);
        }
        dp[0][0] = grid[0][0];

        for (int step = 1; step <= 2 * n - 2; step++) {
            int[][] next = new int[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(next[i], minValue);
            }

            for (int r1 = Math.max(0, step - (n - 1)); r1 <= Math.min(n - 1, step); r1++) {
                int c1 = step - r1;
                if (grid[r1][c1] == -1) {
                    continue;
                }
                for (int r2 = Math.max(0, step - (n - 1)); r2 <= Math.min(n - 1, step); r2++) {
                    int c2 = step - r2;
                    if (grid[r2][c2] == -1) {
                        continue;
                    }

                    int bestPrevious = minValue;
                    bestPrevious = Math.max(bestPrevious, get(dp, r1, r2));
                    bestPrevious = Math.max(bestPrevious, get(dp, r1 - 1, r2));
                    bestPrevious = Math.max(bestPrevious, get(dp, r1, r2 - 1));
                    bestPrevious = Math.max(bestPrevious, get(dp, r1 - 1, r2 - 1));
                    if (bestPrevious == minValue) {
                        continue;
                    }

                    int cherries = bestPrevious + grid[r1][c1];
                    if (r1 != r2 || c1 != c2) {
                        cherries += grid[r2][c2];
                    }
                    next[r1][r2] = Math.max(next[r1][r2], cherries);
                }
            }
            dp = next;
        }

        return Math.max(0, dp[n - 1][n - 1]);
    }

    private int get(int[][] dp, int r1, int r2) {
        if (r1 < 0 || r2 < 0) {
            return Integer.MIN_VALUE / 4;
        }
        return dp[r1][r2];
    }
}
