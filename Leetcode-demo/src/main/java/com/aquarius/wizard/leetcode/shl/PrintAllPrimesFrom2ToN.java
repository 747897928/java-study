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

    public int[] listPrimes(int limit) {
        if (limit < 2) {
            return new int[0];
        }
        int root = (int) Math.sqrt(limit);
        List<Integer> basePrimes = simpleSieve(root);
        List<Integer> result = new ArrayList<>();
        int segmentSize = Math.max(root + 1, 32_768);

        for (int low = 2; low <= limit; low += segmentSize) {
            int high = Math.min(limit, low + segmentSize - 1);
            boolean[] composite = new boolean[high - low + 1];
            for (int prime : basePrimes) {
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
