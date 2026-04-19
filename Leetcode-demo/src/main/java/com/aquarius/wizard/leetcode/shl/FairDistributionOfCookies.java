package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 2305
 * Fair Distribution of Cookies
 *
 * 把若干袋饼干分给 k 个孩子，
 * 每个孩子拿到的总饼干数越不均衡，不公平值越大。
 *
 * 定义不公平值为“拿得最多的那个孩子拿到的总数”。
 * 问最小可能不公平值。
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
