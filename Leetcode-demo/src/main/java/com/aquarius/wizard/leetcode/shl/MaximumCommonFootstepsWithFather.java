package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;

/**
 * Question
 *
 * [The top part of the screenshot is missing.]
 * Visible fragment:
 * ... constant speed of V1 meters per step for N steps.
 *
 * Martin is standing at X2 meters away from his home. He wonders how fast he must run at some
 * constant speed of V2 meters per step so as to maximize F, where F equals the number of his
 * father's footsteps that Martin will land on during his run. It is given that the first step that
 * Martin will land on, from his starting position, will have been landed on by his father.
 *
 * Note that if more than one prospective velocity results in the same number of maximum common
 * steps, output the highest prospective velocity as V2.
 *
 * Write an algorithm to help Martin calculate F and V2.
 *
 * Input
 *
 * The first line of the input consists of an integer fatherPos, representing the initial position
 * of Martin's father (X1).
 * The second line consists of an integer martinPos, representing the initial position of Martin
 * (X2).
 * The third line consists of an integer velFather, representing the velocity of the father (V1).
 * The last line consists of an integer steps, representing the number of steps taken by the father
 * (N).
 *
 * Output
 *
 * Print two space-separated integers as the maximum number of common footsteps F and the
 * corresponding speed V2.
 *
 * Constraints
 *
 * [The screenshot for constraints is missing.]
 *
 * Example
 *
 * [The screenshot for the example is missing.]
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：等差数列交集、数论、gcd。
 * 校对：题面缺得比较多，所以这题的代码是基于可见英文重新推导的。我把父亲的落脚点看成有限等差数列，把 Martin 的落脚点看成从第一步开始的无限等差数列。
 * 提示：先枚举 Martin 第一步对应父亲的第 t 个脚印，此时 V2 就唯一确定；之后公共脚印会按固定步长继续出现。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MaximumCommonFootstepsWithFather {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long fatherPos = scanner.nextLong();
        long martinPos = scanner.nextLong();
        long fatherVelocity = scanner.nextLong();
        long steps = scanner.nextLong();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * long fatherPos = 4L;
         * long martinPos = 0L;
         * long fatherVelocity = 2L;
         * long steps = 6L;
         */

        MaximumCommonFootstepsWithFather solver = new MaximumCommonFootstepsWithFather();
        long[] answer = solver.maximizeCommonSteps(fatherPos, martinPos, fatherVelocity, steps);
        System.out.println(answer[0] + " " + answer[1]);
    }

    /**
     * 这题题面缺失比较多，所以真正的理解关键在于把它翻译成数列问题。
     *
     * 父亲的脚印位置是一个有限等差数列：
     *
     * fatherPos + fatherVelocity * 1
     * fatherPos + fatherVelocity * 2
     * ...
     * fatherPos + fatherVelocity * steps
     *
     * Martin 的脚印位置则是：
     *
     * martinPos + martinVelocity * 1
     * martinPos + martinVelocity * 2
     * ...
     *
     * Question还给了一个关键条件：
     *
     * “Martin 的第一步一定踩在父亲某一个脚印上”
     *
     * 这句话意味着：
     *
     * 只要你枚举“Martin 第一步对应父亲的第 fatherStepIndex 个脚印”，
     * 那么 Martin 的速度 martinVelocity 就被唯一确定了。
     *
     * 因为：
     *
     * martinPos + martinVelocity
     * = fatherPos + fatherStepIndex * fatherVelocity
     *
     * 所以：
     *
     * martinVelocity
     * = (fatherPos - martinPos) + fatherStepIndex * fatherVelocity
     *
     * 枚举完这个 fatherStepIndex 后，
     * 问题就变成：
     *
     * “两个等差数列之后还会重合多少次？”
     *
     * 这个重合步长由 gcd 决定。
     */
    public long[] maximizeCommonSteps(long fatherPos, long martinPos, long fatherVelocity, long steps) {
        long delta = fatherPos - martinPos;
        long bestCommon = 0L;
        long bestVelocity = -1L;

        // 枚举：Martin 第一步踩中父亲的第几步脚印。
        for (long fatherStepIndex = 1; fatherStepIndex <= steps; fatherStepIndex++) {
            long martinVelocity = delta + fatherStepIndex * fatherVelocity;
            if (martinVelocity <= 0) {
                continue;
            }

            // 两个等差数列的公共落点间隔，和 gcd 有关。
            long gcd = gcd(fatherVelocity, martinVelocity);

            // strideOnFatherSteps 表示：
            // 父亲还要再走多少步，才会再次和 Martin 落在同一个位置。
            long strideOnFatherSteps = martinVelocity / gcd;

            // 第一次公共脚印已经固定算 1 次。
            // 后面还能再出现多少次，就看父亲剩余步数里还能容纳多少个 stride。
            long common = 1L + (steps - fatherStepIndex) / strideOnFatherSteps;

            // 如果公共脚印数相同，按题意取更大的 Martin 速度。
            if (common > bestCommon || (common == bestCommon && martinVelocity > bestVelocity)) {
                bestCommon = common;
                bestVelocity = martinVelocity;
            }
        }
        return new long[]{bestCommon, bestVelocity};
    }

    /**
     * 欧几里得算法求 gcd。
     * 这里只是用来推公共脚印的重复节奏。
     */
    private long gcd(long a, long b) {
        long x = Math.abs(a);
        long y = Math.abs(b);
        while (y != 0) {
            long next = x % y;
            x = y;
            y = next;
        }
        return x;
    }
}
