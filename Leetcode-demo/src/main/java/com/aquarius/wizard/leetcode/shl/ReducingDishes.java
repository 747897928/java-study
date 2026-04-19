package com.aquarius.wizard.leetcode.shl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 题目
 *
 * LeetCode 1402
 * Reducing Dishes
 *
 * 给定 satisfaction 数组，每做一道菜都要占一个时间单位。
 * 某道菜如果在第 t 个时间做，它的贡献是 satisfaction[i] * t。
 * 可以丢弃一些菜，问最大总贡献。
 *
 * 笔记
 *
 * 这题常见有两种思路：
 *
 * 1. DP
 *    排序后做“选 / 不选”
 *
 * 2. 贪心
 *    从大的满意度开始往前加，只要前缀和还是正的，就值得保留
 *
 * 贪心版很漂亮，但如果第一次做，这个结论并不好直接想到。
 * 所以我这里把两种写法都放进来。
 *
 * 正式主解我用贪心版。
 *
 * <p>create: 2026-04-19 10:02:00</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class ReducingDishes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] satisfaction = new int[n];
        for (int i = 0; i < n; i++) {
            satisfaction[i] = scanner.nextInt();
        }

        ReducingDishes solver = new ReducingDishes();
        System.out.println(solver.maxSatisfaction(satisfaction));
    }

    public int maxSatisfaction(int[] satisfaction) {
        return maxSatisfactionGreedy(satisfaction);
    }

    public int maxSatisfactionGreedy(int[] satisfaction) {
        int[] copy = Arrays.copyOf(satisfaction, satisfaction.length);
        Arrays.sort(copy);

        int suffixSum = 0;
        int answer = 0;
        for (int i = copy.length - 1; i >= 0; i--) {
            suffixSum += copy[i];
            if (suffixSum <= 0) {
                break;
            }
            answer += suffixSum;
        }
        return answer;
    }

    public int maxSatisfactionDp(int[] satisfaction) {
        int[] copy = Arrays.copyOf(satisfaction, satisfaction.length);
        Arrays.sort(copy);
        int n = copy.length;
        int[][] dp = new int[n + 1][n + 2];

        for (int index = n - 1; index >= 0; index--) {
            for (int time = index + 1; time >= 1; time--) {
                int skip = dp[index + 1][time];
                int take = copy[index] * time + dp[index + 1][time + 1];
                dp[index][time] = Math.max(skip, take);
            }
        }

        return dp[0][1];
    }
}
