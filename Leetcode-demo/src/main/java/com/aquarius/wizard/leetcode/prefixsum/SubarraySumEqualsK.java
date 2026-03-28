package com.aquarius.wizard.leetcode.prefixsum;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目：和为 K 的子数组（LeetCode 560）
 *
 * 给你一个整数数组 nums 和一个整数 k，请你统计和为 k 的连续子数组个数。
 *
 * 这题是前缀和训练里最关键的一道升级题，因为它告诉你：
 *
 * 1. 只有前缀和还不够。
 * 2. 如果题目在“统计个数”，往往还需要哈希表记录历史前缀和出现次数。
 *
 * 暴力思路：
 *
 * 枚举每个起点，再向右扩展终点，边扩展边累加当前子数组和。
 * 如果当前和等于 k，就把答案加一。
 *
 * 时间复杂度 O(n^2)。
 *
 * 优化思路：
 *
 * 设 prefix[i] 表示 nums[0..i] 的前缀和。
 * 如果某个区间 [j + 1, i] 的和等于 k，那么：
 *
 * prefix[i] - prefix[j] = k
 * prefix[j] = prefix[i] - k
 *
 * 所以当我们扫到当前位置 i，只要知道“之前有多少个前缀和等于 prefix[i] - k”，
 * 就知道有多少个子数组以 i 结尾且和为 k。
 *
 * 这就是“前缀和 + 哈希表计数”的标准模板。
 */
public class SubarraySumEqualsK {

    /**
     * 暴力版：枚举起点，再向右扩展终点。
     */
    public int subarraySumBruteForce(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            long sum = 0L;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 推荐版：前缀和 + 哈希表。
     *
     * prefixCount 记录“某个前缀和出现了多少次”。
     * 初始放入 0 -> 1，表示“什么都不选时，前缀和为 0 出现过一次”。
     * 这一步非常关键，它负责处理“从下标 0 开始的子数组”。
     */
    public int subarraySum(int[] nums, int k) {
        Map<Long, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0L, 1);

        long prefix = 0L;
        int count = 0;
        for (int num : nums) {
            prefix += num;
            count += prefixCount.getOrDefault(prefix - k, 0);
            prefixCount.put(prefix, prefixCount.getOrDefault(prefix, 0) + 1);
        }
        return count;
    }
}
