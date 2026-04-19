package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 96
 * Unique Binary Search Trees
 *
 * 原题链接：
 * https://leetcode.com/problems/unique-binary-search-trees/description/?envType=problem-list-v2&envId=dynamic-programming
 *
 * 题意（按原题补全）：
 * 给定整数 n，请你用 1 到 n 这 n 个不同数字作为节点值，
 * 统计一共能构造出多少棵结构不同的二叉搜索树。
 *
 * 示例 1：
 * Input: n = 3
 * Output: 5
 *
 * 示例 2：
 * Input: n = 1
 * Output: 1
 *
 * 约束：
 * 1 <= n <= 19
 *
 * 笔记
 *
 * 这题是很典型的“先枚举根，再把左右子树方案数乘起来”的 DP。
 *
 * 如果把某个数字 root 选成根：
 *
 * - 左子树只能用比 root 小的那些数
 * - 右子树只能用比 root 大的那些数
 *
 * 并且左右子树的结构选择彼此独立，
 * 所以：
 *
 * 以 root 为根的方案数 = 左子树方案数 * 右子树方案数
 *
 * 所以这题的核心不是“BST 怎么建”，
 * 而是“按根拆分区间，左右独立相乘”。
 *
 * 这题常见两种写法：
 *
 * 1. DP
 *    dp[i] = 用 i 个节点能组成多少种 BST
 *
 * 2. Catalan 数公式
 *    这题答案就是第 n 个 Catalan 数
 *
 * 正式主解我这里用 DP，因为更贴近“我是怎么推出来的”。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class UniqueBinarySearchTrees {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int n = 3;
         */

        UniqueBinarySearchTrees solver = new UniqueBinarySearchTrees();
        System.out.println(solver.numTrees(n));

        /*
         * 如果想顺手对照 Catalan 公式版，可以临时打开下面这行：
         * System.out.println(solver.numTreesCatalan(n));
         */
    }

    public int numTrees(int n) {
        return numTreesDp(n);
    }

    /**
     * DP 写法。
     *
     * dp[i] 表示：恰好用 i 个节点时，一共有多少种不同 BST。
     *
     * 枚举根节点时，左子树用了 leftCount 个节点，
     * 那右子树就自动用了 i - 1 - leftCount 个节点。
     */
    public int numTreesDp(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        if (n >= 1) {
            dp[1] = 1;
        }

        for (int nodes = 2; nodes <= n; nodes++) {
            for (int leftCount = 0; leftCount < nodes; leftCount++) {
                int rightCount = nodes - 1 - leftCount;
                dp[nodes] += dp[leftCount] * dp[rightCount];
            }
        }

        return dp[n];
    }

    /**
     * Catalan 公式写法。
     *
     * C0 = 1
     * C(n+1) = C(n) * 2 * (2n + 1) / (n + 2)
     *
     * 这里用 long 只是为了中间乘法更稳，
     * 最终题目范围内答案仍然能装回 int。
     */
    public int numTreesCatalan(int n) {
        long catalan = 1L;
        for (int i = 0; i < n; i++) {
            catalan = catalan * 2 * (2L * i + 1) / (i + 2);
        }
        return (int) catalan;
    }
}
