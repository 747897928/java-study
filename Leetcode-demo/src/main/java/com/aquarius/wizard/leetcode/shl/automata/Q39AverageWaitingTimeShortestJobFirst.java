package com.aquarius.wizard.leetcode.shl.automata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 39
 *
 * Shortest Job First (SJF) is a system for scheduling task requests. Each task request is
 * characterized by its request time (i.e., the time at which the task is submitted to the system)
 * and its duration (i.e., the time needed to complete the task). When the SJF system completes a
 * task it selects the task with the smallest duration to execute next If multiple tasks have the
 * same smallest duration, SJF selects the task with the earliest request time. The waiting time
 * for a task is the difference between the request time and the actual start time (i.e., the time
 * that it spends waiting for the system to execute it). You may assume that the tasks arrive in
 * such frequency that the system executes tasks constantly and is never idle.
 *
 * Given a list of request times and duration times, calculate the average task waiting time when
 * scheduled using the Shortest Job First (SJF) algorithm.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. taskCount
 * 2. taskCount lines: requestTime duration
 */
public class Q39AverageWaitingTimeShortestJobFirst {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int taskCount = scanner.nextInt();
        int[][] tasks = new int[taskCount][2];
        for (int i = 0; i < taskCount; i++) {
            tasks[i][0] = scanner.nextInt();
            tasks[i][1] = scanner.nextInt();
        }

        Q39AverageWaitingTimeShortestJobFirst solver =
            new Q39AverageWaitingTimeShortestJobFirst();
        System.out.println(solver.averageWaitingTime(tasks));
    }

    public String averageWaitingTime(int[][] tasks) {
        /*
         * 这题本质上是模拟 Shortest Job First 调度。
         *
         * 难点不在公式，而在“当前时刻有哪些任务已经到达、可以被选中”。
         * 所以通常会配两个结构：
         * 1. 先按 requestTime 排序，保证任务按到达时间依次进入系统
         * 2. 再用一个最小堆，维护“当前已经到达、但还没执行”的任务
         *
         * 堆里的排序规则正好对应题意：
         * - 先比 duration，小的优先
         * - duration 相同，再比 requestTime，早到的优先
         *
         * 每次从堆里 poll 出来的，就是当前 SJF 应该执行的任务。
         */
        Arrays.sort(tasks, Comparator.comparingInt(task -> task[0]));
        PriorityQueue<int[]> queue = new PriorityQueue<>(
            (a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));

        long currentTime = 0L;
        long totalWaitingTime = 0L;
        int index = 0;

        while (index < tasks.length || !queue.isEmpty()) {
            if (queue.isEmpty() && currentTime < tasks[index][0]) {
                // 如果堆空了，说明当前没有可执行任务，时间直接跳到下一次任务到达。
                currentTime = tasks[index][0];
            }
            while (index < tasks.length && tasks[index][0] <= currentTime) {
                // 把所有已经到达的任务先丢进候选堆，等着 SJF 选最短的那个。
                queue.offer(tasks[index++]);
            }
            int[] task = queue.poll();
            // waiting time = 实际开始时间 - 请求时间。
            totalWaitingTime += currentTime - task[0];
            currentTime += task[1];
        }

        // 题目问平均等待时间，所以最后再统一做一次平均。
        return BigDecimal.valueOf(totalWaitingTime)
            .divide(BigDecimal.valueOf(tasks.length), 6, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString();
    }
}
