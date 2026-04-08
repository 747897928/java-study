package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Question
 *
 * A prime number is divisible only by 1 and itself. You are given a positive integer. Write an
 * algorithm to find all the prime numbers from 2 to the given positive number.
 *
 * Input
 *
 * The input consists of an integer num, representing the number written on the board.
 *
 * Output
 *
 * Print space-separated integers representing the numbers required by the teacher.
 *
 * Constraints
 *
 * 1 < num < 10^9
 *
 * Example
 *
 * Input:
 * 11
 *
 * Output:
 * 2 3 5 7 11
 *
 * Explanation:
 * For the given number, the list of special numbers is [2, 3, 5, 7, 11].
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：质数筛、试除法、分段筛。
 * 校对：题面把上限写到了 10^9，但又要求“输出全部质数”，这在真实面试里通常意味着截图漏了更合理的限制。
 * 提示：这里我用分段筛，至少在空间上更稳；但如果平台真的给到 10^9 级别并要求完整输出，I/O 本身都会很重。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class PrintAllPrimesFrom2ToN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int limit = 11;
         */

        PrintAllPrimesFrom2ToN solver = new PrintAllPrimesFrom2ToN();
        System.out.println(format(solver.listPrimes(limit)));
    }

    private static String format(int[] nums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(nums[i]);
        }
        return builder.toString();
    }

    /**
     * 这里没有直接用最朴素的“对每个数试除到 sqrt(n)”。
     *
     * 原因是题面上限写得比较大，
     * 所以更稳的思路是分段筛。
     *
     * 分段筛可以拆成两层：
     *
     * 1. 先用普通筛法，筛出 <= sqrt(limit) 的所有质数
     *    这些叫 basePrimes
     *
     * 2. 再把区间 [2, limit] 切成很多段
     *    每一段都用 basePrimes 去标记合数
     *
     * 这样做的好处是：
     *
     * - 时间上仍然沿用筛法思路
     * - 空间上不用一次性开到 limit 那么大的布尔数组
     */
    public int[] listPrimes(int limit) {
        if (limit < 2) {
            return new int[0];
        }
        int root = (int) Math.sqrt(limit);
        List<Integer> basePrimes = simpleSieve(root);
        List<Integer> result = new ArrayList<>();
        int segmentSize = Math.max(root + 1, 32_768);

        // 逐段处理 [low, high]。
        for (int low = 2; low <= limit; low += segmentSize) {
            int high = Math.min(limit, low + segmentSize - 1);
            boolean[] composite = new boolean[high - low + 1];
            for (int prime : basePrimes) {
                // 这一段里，prime 的第一个需要被标掉的倍数。
                long start = Math.max((long) prime * prime, ((low + (long) prime - 1) / prime) * (long) prime);
                for (long value = start; value <= high; value += prime) {
                    composite[(int) (value - low)] = true;
                }
            }
            for (int value = low; value <= high; value++) {
                if (!composite[value - low]) {
                    result.add(value);
                }
            }
        }
        int[] primes = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            primes[i] = result.get(i);
        }
        return primes;
    }

    /**
     * 普通筛法，专门用来生成 basePrimes。
     */
    private List<Integer> simpleSieve(int limit) {
        boolean[] composite = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();
        for (int value = 2; value <= limit; value++) {
            if (composite[value]) {
                continue;
            }
            primes.add(value);
            if ((long) value * value > limit) {
                continue;
            }
            for (int multiple = value * value; multiple <= limit; multiple += value) {
                composite[multiple] = true;
            }
        }
        return primes;
    }
}
