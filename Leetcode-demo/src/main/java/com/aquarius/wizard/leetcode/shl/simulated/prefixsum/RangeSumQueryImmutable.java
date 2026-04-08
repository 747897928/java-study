package com.aquarius.wizard.leetcode.shl.simulated.prefixsum;

import java.util.Scanner;

/**
 * Prefix Sum Drill
 *
 * Original source style:
 *
 * LeetCode 303 - Range Sum Query - Immutable
 *
 * 不是原 SHL 题，是前缀和练习题。
 *
 * 给定一个数组 nums，多次查询 [left, right] 区间元素和。
 *
 * 这题的关键是：不是只问一次，而是会问很多次。
 * 一旦你发现“同一个数组上会重复查询很多段区间和”，
 * 就要优先想到“前缀和数组”。
 *
 * 暴力思路：
 *
 * 每次查询时都从 left 加到 right，单次 O(n)。
 * 如果查询很多次，总复杂度会很高。
 *
 * 优化思路：
 *
 * prefix[i] 表示前 i 个元素的和，也就是 nums[0..i-1] 的和。
 * 那么：
 *
 * sum(left, right) = prefix[right + 1] - prefix[left]
 *
 * 建表 O(n)，每次查询 O(1)。
 *
 * 题型总结：
 *
 * 只要是“静态数组 + 多次区间求和”，前缀和几乎就是第一反应。
 *
 * <p>create: 2026-03-28 23:43:16</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class RangeSumQueryImmutable {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = scanner.nextInt();
        }
        int queryCount = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int[] nums = {-2, 0, 3, -5, 2, -1};
         * int[][] queries = {{0, 2}, {2, 5}, {0, 5}};
         */

        NumArray prefixSum = new NumArray(nums);
        for (int i = 0; i < queryCount; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            System.out.println(prefixSum.sumRange(left, right));
        }
        /*
         * 如果需要核对朴素写法，可以临时打开下面这几行：
         * NumArrayBruteForce bruteForce = new NumArrayBruteForce(nums);
         * System.out.println(bruteForce.sumRange(left, right));
         */
    }

    /**
     * 暴力版，用来帮助理解“为什么会超时”。
     */
    public static class NumArrayBruteForce {
        private final int[] nums;

        public NumArrayBruteForce(int[] nums) {
            this.nums = nums.clone();
        }

        public long sumRange(int left, int right) {
            long sum = 0L;
            for (int i = left; i <= right; i++) {
                sum += nums[i];
            }
            return sum;
        }
    }

    /**
     * 推荐版：前缀和数组。
     */
    public static class NumArray {
        private final long[] prefix;

        public NumArray(int[] nums) {
            prefix = new long[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        public long sumRange(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }
    }
}
