package com.aquarius.wizard.leetcode.shl;

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
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：等差数列交集、数论、gcd。
 * 校对：题面缺得比较多，所以这题的代码是基于可见英文重新推导的。我把父亲的落脚点看成有限等差数列，把 Martin 的落脚点看成从第一步开始的无限等差数列。
 * 提示：先枚举 Martin 第一步对应父亲的第 t 个脚印，此时 V2 就唯一确定；之后公共脚印会按固定步长继续出现。
 */
public class MaximumCommonFootstepsWithFather {

    public long[] maximizeCommonSteps(long fatherPos, long martinPos, long fatherVelocity, long steps) {
        long delta = fatherPos - martinPos;
        long bestCommon = 0L;
        long bestVelocity = -1L;
        for (long fatherStepIndex = 1; fatherStepIndex <= steps; fatherStepIndex++) {
            long martinVelocity = delta + fatherStepIndex * fatherVelocity;
            if (martinVelocity <= 0) {
                continue;
            }
            long gcd = gcd(fatherVelocity, martinVelocity);
            long strideOnFatherSteps = martinVelocity / gcd;
            long common = 1L + (steps - fatherStepIndex) / strideOnFatherSteps;
            if (common > bestCommon || (common == bestCommon && martinVelocity > bestVelocity)) {
                bestCommon = common;
                bestVelocity = martinVelocity;
            }
        }
        return new long[]{bestCommon, bestVelocity};
    }

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
