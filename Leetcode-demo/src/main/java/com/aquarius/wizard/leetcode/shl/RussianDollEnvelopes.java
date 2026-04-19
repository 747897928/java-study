package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 354
 * Russian Doll Envelopes
 *
 * 给定一批信封 envelopes[i] = [wi, hi]，
 * 如果一个信封的宽和高都严格小于另一个信封，
 * 那它就可以被装进去。
 *
 * 问最多能套多少层。
 *
 * 笔记
 *
 * 这题第一眼很容易想到二维 DP：
 *
 * - 先排序
 * - 再做“以第 i 个信封结尾的最长链”
 *
 * 这个想法是对的，时间复杂度 O(n^2)。
 *
 * 继续优化时，关键就在排序细节：
 *
 * - 宽度升序
 * - 宽度相同的时候，高度降序
 *
 * 为什么同宽要按高度降序？
 *
 * 因为同宽的两个信封不能互相套。
 * 如果同宽时高度也升序，
 * 那后面直接对高度做 LIS 时，就可能把同宽的也错误地串起来。
 *
 * 所以必须让同宽时高度倒过来排，
 * 这样同一宽度的一串信封，不会被 LIS 错算成严格递增链。
 *
 * 这题常见两种写法：
 *
 * 1. O(n^2) DP
 * 2. 排序 + 高度 LIS，时间复杂度 O(n log n)
 *
 * 正式主解我这里用 LIS 版。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class RussianDollEnvelopes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] envelopes = new int[n][2];
        for (int i = 0; i < n; i++) {
            envelopes[i][0] = scanner.nextInt();
            envelopes[i][1] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int[][] envelopes = {
         *     {5, 4},
         *     {6, 4},
         *     {6, 7},
         *     {2, 3}
         * };
         */

        RussianDollEnvelopes solver = new RussianDollEnvelopes();
        System.out.println(solver.maxEnvelopes(envelopes));

        /*
         * 如果想顺手核对 O(n^2) 版，可以临时打开下面这行：
         * System.out.println(solver.maxEnvelopesQuadratic(envelopes));
         */
    }

    public int maxEnvelopes(int[][] envelopes) {
        return maxEnvelopesLis(envelopes);
    }

    public int maxEnvelopesQuadratic(int[][] envelopes) {
        if (envelopes.length == 0) {
            return 0;
        }

        int[][] sorted = copyAndSort(envelopes);
        int n = sorted.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int answer = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (sorted[j][0] < sorted[i][0] && sorted[j][1] < sorted[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            answer = Math.max(answer, dp[i]);
        }

        return answer;
    }

    /**
     * 主解：排序后对高度做 LIS。
     */
    public int maxEnvelopesLis(int[][] envelopes) {
        if (envelopes.length == 0) {
            return 0;
        }

        int[][] sorted = copyAndSort(envelopes);
        int[] tails = new int[sorted.length];
        int size = 0;

        for (int[] envelope : sorted) {
            int height = envelope[1];
            int left = 0;
            int right = size;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < height) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = height;
            if (left == size) {
                size++;
            }
        }

        return size;
    }

    private int[][] copyAndSort(int[][] envelopes) {
        int[][] copy = new int[envelopes.length][2];
        for (int i = 0; i < envelopes.length; i++) {
            copy[i][0] = envelopes[i][0];
            copy[i][1] = envelopes[i][1];
        }

        Arrays.sort(copy, new Comparator<int[]>() {
            @Override
            public int compare(int[] first, int[] second) {
                if (first[0] != second[0]) {
                    return Integer.compare(first[0], second[0]);
                }
                return Integer.compare(second[1], first[1]);
            }
        });
        return copy;
    }
}
