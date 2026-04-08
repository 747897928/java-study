package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 3653
 * XOR After Range Multiplication Queries I
 *
 * 给定数组 nums 和若干个查询 queries。
 *
 * 每个查询是 [l, r, k, v]：
 * 从下标 l 开始，每次跳 k 个位置，只要没有超过 r，就把当前位置乘上 v，
 * 然后对 1_000_000_007 取模。
 *
 * 所有查询处理完之后，返回整个数组所有元素的按位异或结果。
 *
 * 笔记
 *
 * 这题第一眼看上去像“区间修改”，很容易把自己往线段树、差分这些方向带。
 * 但这题是 I，而且 n 和 q 都不大，直接按题目要求模拟就够了。
 *
 * 我做这题时要先提醒自己两件事：
 *
 * 1. 先看数据范围，不要看到“query”就条件反射上重武器。
 * 2. 这里不是连续区间，而是按步长 k 跳着改，直接模拟反而最直。
 *
 * <p>create: 2026-04-08 23:48:47</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class XorAfterRangeMultiplicationQueriesI {

    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        int queryCount = scanner.nextInt();
        int[][] queries = new int[queryCount][4];
        for (int i = 0; i < queryCount; i++) {
            for (int j = 0; j < 4; j++) {
                queries[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int[] nums = {2, 3, 1, 5, 4};
         * int[][] queries = {
         *         {1, 4, 2, 3},
         *         {0, 2, 1, 2}
         * };
         */

        XorAfterRangeMultiplicationQueriesI solver = new XorAfterRangeMultiplicationQueriesI();
        System.out.println(solver.xorAfterQueries(nums, queries));
    }

    public int xorAfterQueries(int[] nums, int[][] queries) {
        /*
         * 这题就按题目意思一条一条模拟。
         *
         * 每个查询只会改：
         * l, l + k, l + 2k, ...
         * 这些没有超过 r 的位置。
         *
         * 改完所有查询后，再统一把数组异或一遍。
         */
        for (int[] query : queries) {
            int left = query[0];
            int right = query[1];
            int step = query[2];
            int value = query[3];
            for (int index = left; index <= right; index += step) {
                nums[index] = (int) ((long) nums[index] * value % MOD);
            }
        }

        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        return xor;
    }
}
