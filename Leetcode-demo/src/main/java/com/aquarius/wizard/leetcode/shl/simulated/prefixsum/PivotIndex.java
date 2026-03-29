package com.aquarius.wizard.leetcode.shl.simulated.prefixsum;

import java.util.Arrays;

/**
 * Prefix Sum Drill
 *
 * Original source style:
 *
 * LeetCode 724 - Find Pivot Index
 *
 * 这不是原 SHL 题，是我补的前缀和练习题。
 *
 * 给你一个整数数组 nums，请返回数组的中心下标。
 * 中心下标的定义是：下标左侧所有元素之和等于右侧所有元素之和。
 * 如果存在多个中心下标，返回最靠左的一个；如果不存在，返回 -1。
 *
 * 这题是 EqualMatrixPartition 的一维版，适合拿来建立“左右两部分相等”这类题的固定反应。
 *
 * 暴力思路：
 *
 * 对每个位置 i：
 * 1. 重新计算 [0, i - 1] 的和
 * 2. 重新计算 [i + 1, n - 1] 的和
 * 3. 比较左右是否相等
 *
 * 时间复杂度 O(n^2)。
 *
 * 优化思路：
 *
 * 1. 先求 total = 所有元素和
 * 2. 枚举每个位置时，维护 leftSum
 * 3. 当前右边和就是 total - leftSum - nums[i]
 *
 * 于是每个位置只需要 O(1) 判断，整体 O(n)。
 *
 * 题型总结：
 *
 * 看到“某个位置把数组分成左右两边，要求两边满足某种和关系”，
 * 优先想“总和 + 左前缀和”，而不是每次从头累加左右两边。
 */
public class PivotIndex {

    public static void main(String[] args) {
        int[] nums = {1, 7, 3, 6, 5, 6};

        PivotIndex solver = new PivotIndex();
        System.out.println("input  = " + Arrays.toString(nums));
        System.out.println("brute  = " + solver.pivotIndexBruteForce(nums));
        System.out.println("fast   = " + solver.pivotIndex(nums));
    }

    /**
     * 暴力版：每个位置都重新计算左右两边的和。
     */
    public int pivotIndexBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            long leftSum = 0L;
            long rightSum = 0L;
            for (int left = 0; left < i; left++) {
                leftSum += nums[left];
            }
            for (int right = i + 1; right < nums.length; right++) {
                rightSum += nums[right];
            }
            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 推荐版：总和 + 左前缀和。
     *
     * leftSum 表示当前位置左边所有元素和。
     * rightSum 不需要单独维护，直接用 total - leftSum - nums[i] 推出来。
     */
    public int pivotIndex(int[] nums) {
        long total = 0L;
        for (int num : nums) {
            total += num;
        }

        long leftSum = 0L;
        for (int i = 0; i < nums.length; i++) {
            long rightSum = total - leftSum - nums[i];
            if (leftSum == rightSum) {
                return i;
            }
            leftSum += nums[i];
        }
        return -1;
    }
}

