package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


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
 * 备注
 *
 * 难度：中等。
 *
 * 考点：状态 DP。
 * 校对：样例已做代码校验。
 * 思路：区分“今天休息 / 今天做简单任务 / 今天做困难任务”三种状态，困难任务只能从“昨天休息”转移过来。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MaximumInternshipSalary {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int internshipDays = scanner.nextInt();
        int taskTypeCount = scanner.nextInt();
        int[][] payByDay = new int[internshipDays][taskTypeCount];
        for (int i = 0; i < internshipDays; i++) {
            for (int j = 0; j < taskTypeCount; j++) {
                payByDay[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int internshipDays = 4;
         * int taskTypeCount = 2;
         * int[][] payByDay = {{1, 2}, {4, 10}, {20, 21}, {2, 23}};
         */

        MaximumInternshipSalary solver = new MaximumInternshipSalary();
        System.out.println(solver.maxSalary(payByDay));
    }

    /**
     * 这题最关键的限制是：
     *
     * “只有昨天休息，今天才允许做困难任务”
     *
     * 所以状态不能只记“做到第几天的最大工资”，
     * 还要记“今天是以什么状态结束的”。
     *
     * 这里把每天结束时分成三种状态：
     *
     * - prevIdle：今天休息
     * - prevEasy：今天做了简单任务
     * - prevHard：今天做了困难任务
     *
     * 第二天转移时：
     *
     * 1. nextIdle
     *    昨天不管是什么状态，今天都可以选择休息
     *
     * 2. nextEasy
     *    昨天休息或昨天做简单任务，今天都可以做简单任务
     *    因为Question没有限制简单任务必须隔天
     *
     * 3. nextHard
     *    只能从 prevIdle 转过来
     *    这正好体现“昨天必须没工作”
     *
     * 这就是标准的小状态 DP。
     */
    public long maxSalary(int[][] payByDay) {
        // 第 0 天之前：
        // “休息”是合法初始态，工资为 0。
        long prevIdle = 0L;

        // 还没开始时，不可能已经处于“今天做了简单/困难任务”的状态，
        // 所以先设成极小值，表示非法。
        long prevEasy = Long.MIN_VALUE / 4;
        long prevHard = Long.MIN_VALUE / 4;
        for (int[] day : payByDay) {
            // 今天休息：昨天三种状态随便接。
            long nextIdle = Math.max(prevIdle, Math.max(prevEasy, prevHard));

            // 今天做简单任务：
            // 可以从“昨天休息”或“昨天也做简单任务”接过来。
            long nextEasy = Math.max(prevIdle, prevEasy) + day[0];

            // 今天做困难任务：
            // 只能从“昨天休息”接过来。
            long nextHard = prevIdle + day[1];

            // 滚动更新到下一天。
            prevIdle = nextIdle;
            prevEasy = nextEasy;
            prevHard = nextHard;
        }

        // 最后一天结束时，三种状态里工资最大的就是答案。
        return Math.max(prevIdle, Math.max(prevEasy, prevHard));
    }
}
