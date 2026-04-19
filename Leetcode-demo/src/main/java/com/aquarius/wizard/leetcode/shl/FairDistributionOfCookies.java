package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 2305
 * Fair Distribution of Cookies
 *
 * 原题链接：
 * https://leetcode.com/problems/fair-distribution-of-cookies/description/?envType=problem-list-v2&envId=dynamic-programming
 *
 * 题意（按原题补全）：
 * 给定若干袋饼干 cookies，以及孩子人数 k。
 * 你需要把每一袋饼干完整地分给某一个孩子。
 * 一个分配方案的不公平值，定义为“拿到饼干总数最多的那个孩子的总量”。
 * 返回最小可能的不公平值。
 *
 * 示例 1：
 * Input: cookies = [8,15,10,20,8], k = 2
 * Output: 31
 * 解释：一种最优分法是 [8,15,8] 和 [10,20]，不公平值是 31。
 *
 * 示例 2：
 * Input: cookies = [6,1,3,2,2,4,1,2], k = 3
 * Output: 7
 *
 * 约束：
 * 2 <= cookies.length <= 8
 * 1 <= cookies[i] <= 10^5
 * 2 <= k <= cookies.length
 *
 * 笔记
 *
 * 这题本质是在做分配搜索：
 * 每袋饼干要分给哪个孩子。
 *
 * 常见两种写法：
 *
 * 1. 回溯 + 剪枝
 * 2. 位压 DP
 *
 * 回溯版更贴近“我怎么暴力枚举所有分法，再剪掉没必要的分支”。
 * 所以正式主解我这里用回溯。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class FairDistributionOfCookies {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] cookies = new int[n];
        for (int i = 0; i < n; i++) {
            cookies[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();

        FairDistributionOfCookies solver = new FairDistributionOfCookies();
        System.out.println(solver.distributeCookies(cookies, k));
    }

    public int distributeCookies(int[] cookies, int k) {
        return distributeCookiesBacktracking(cookies, k);
    }

    public int distributeCookiesBacktracking(int[] cookies, int k) {
        int[] sorted = Arrays.copyOf(cookies, cookies.length);
        Arrays.sort(sorted);
        reverse(sorted);

        int[] children = new int[k];
        int[] best = new int[] {Integer.MAX_VALUE};
        backtrack(sorted, 0, children, 0, best);
        return best[0];
    }

    private void backtrack(int[] cookies, int index, int[] children, int currentMax, int[] best) {
        if (currentMax >= best[0]) {
            return;
        }
        if (index == cookies.length) {
            best[0] = Math.min(best[0], currentMax);
            return;
        }

        for (int i = 0; i < children.length; i++) {
            children[i] += cookies[index];
            backtrack(cookies, index + 1, children, Math.max(currentMax, children[i]), best);
            children[i] -= cookies[index];

            if (children[i] == 0) {
                break;
            }
        }
    }

    public int distributeCookiesBitmaskDp(int[] cookies, int k) {
        int n = cookies.length;
        int totalMask = 1 << n;
        int[] subsetSum = new int[totalMask];
        for (int mask = 1; mask < totalMask; mask++) {
            int bit = Integer.numberOfTrailingZeros(mask);
            subsetSum[mask] = subsetSum[mask ^ (1 << bit)] + cookies[bit];
        }

        int[] dp = Arrays.copyOf(subsetSum, totalMask);
        for (int child = 2; child <= k; child++) {
            int[] next = new int[totalMask];
            Arrays.fill(next, Integer.MAX_VALUE / 4);
            for (int mask = 0; mask < totalMask; mask++) {
                for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
                    next[mask] = Math.min(next[mask], Math.max(dp[mask ^ sub], subsetSum[sub]));
                }
                if (mask == 0) {
                    next[mask] = 0;
                }
            }
            dp = next;
        }
        return dp[totalMask - 1];
    }

    private void reverse(int[] nums) {
        for (int left = 0, right = nums.length - 1; left < right; left++, right--) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }
}
