package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Stephen is doing an internship in a company for N days. Each day, he may choose an easy task or
 * a difficult task. He may also choose to perform no task at all. He chooses a difficult task on
 * days when and only when he did not perform any work the previous day. The amounts paid by the
 * company for both the easy and difficult tasks can vary each day, but the company always pays more
 * for difficult tasks.
 *
 * Write an algorithm to help Stephen earn the maximum salary.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers: num and type, representing
 * the number of days of the internship (N) and types of tasks available for each day (M is always
 * equal to 2, respectively).
 * The next N lines consist of M space-separated integers: easy and hard, representing the amount
 * set to be paid for the easy task and the difficult task on that day, respectively.
 *
 * Output
 *
 * Print an integer representing the maximum salary that Stephen can earn.
 *
 * Constraints
 *
 * 1 <= num <= 10^5
 * type = 2
 * 2 <= hard <= 10^4
 * 1 <= easy < hard
 *
 * Example
 *
 * Input:
 * 4 2
 * 1 2
 * 4 10
 * 20 21
 * 2 23
 *
 * Output:
 * 33
 *
 * Explanation:
 * To earn the maximum salary, select the difficult task (10) from the 2nd row and the difficult
 * task (23) from the 4th row. The maximum salary earned = 10 + 23 = 33.
 *
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：状态 DP。
 * 校对：样例已做代码校验。
 * 思路：区分“今天休息 / 今天做简单任务 / 今天做困难任务”三种状态，困难任务只能从“昨天休息”转移过来。
 */
public class MaximumInternshipSalary {

    public long maxSalary(int[][] payByDay) {
        long prevIdle = 0L;
        long prevEasy = Long.MIN_VALUE / 4;
        long prevHard = Long.MIN_VALUE / 4;
        for (int[] day : payByDay) {
            long nextIdle = Math.max(prevIdle, Math.max(prevEasy, prevHard));
            long nextEasy = Math.max(prevIdle, prevEasy) + day[0];
            long nextHard = prevIdle + day[1];
            prevIdle = nextIdle;
            prevEasy = nextEasy;
            prevHard = nextHard;
        }
        return Math.max(prevIdle, Math.max(prevEasy, prevHard));
    }
}
