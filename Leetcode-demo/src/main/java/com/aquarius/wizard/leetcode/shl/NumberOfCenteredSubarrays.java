package com.aquarius.wizard.leetcode.shl;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 题目
 *
 * LeetCode 3804
 * Number of Centered Subarrays
 *
 * 给定整数数组 nums。
 *
 * 如果一个子数组的元素和，恰好等于这个子数组里的某个元素，
 * 那么这个子数组就叫 centered subarray。
 *
 * 返回 centered subarray 的数量。
 *
 * 笔记
 *
 * 这题也很容易一开始想复杂，总觉得是不是要前缀和 + 某种高级结构。
 * 但这题真正的关键还是先看范围。
 *
 * 学习时我更想把这题记成：
 * “枚举每个子数组，然后检查当前总和是不是出现在这个子数组里。”
 *
 * 只要内层枚举右端点时顺手维护：
 * 1. 当前子数组和
 * 2. 当前子数组里出现过哪些值
 *
 * 那每次判断就都很顺。
 *
 * <p>create: 2026-04-08 23:48:47</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class NumberOfCenteredSubarrays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int[] nums = {2, -1, 1};
         */

        NumberOfCenteredSubarrays solver = new NumberOfCenteredSubarrays();
        System.out.println(solver.centeredSubarrays(nums));
    }

    public long centeredSubarrays(int[] nums) {
        /*
         * 固定左端点 left，然后不断扩右端点 right。
         *
         * 在这个过程中：
         * - sum 表示 nums[left..right] 的元素和
         * - seen 表示 nums[left..right] 里出现过哪些值
         *
         * 如果当前 sum 本身就是这个子数组里的某个元素，
         * 那这个子数组就满足题意。
         */
        int n = nums.length;
        long answer = 0;

        for (int left = 0; left < n; left++) {
            long sum = 0;
            Set<Integer> seen = new HashSet<>();
            for (int right = left; right < n; right++) {
                sum += nums[right];
                seen.add(nums[right]);
                if (sum >= Integer.MIN_VALUE && sum <= Integer.MAX_VALUE
                        && seen.contains((int) sum)) {
                    answer++;
                }
            }
        }
        return answer;
    }
}
