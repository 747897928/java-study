package com.aquarius.wizard.leetcode.shl.simulated.window;

/**
 * Simulated Problem
 *
 * Difficulty
 *
 * Medium
 *
 * Question
 *
 * A factory records the health status of a machine every second. The status is stored as a binary
 * string S, where:
 *
 * 1 -> the machine worked normally during that second
 * 0 -> the machine failed during that second
 *
 * The maintenance team can repair at most K failed seconds inside one continuous time window.
 * After repairing those failed seconds, that entire window becomes stable.
 *
 * Write an algorithm to find the maximum length of a stable continuous window that can be obtained.
 *
 * Input
 *
 * The first line of the input consists of a string sensorLog, representing S.
 * The second line consists of an integer maxRepairs, representing K.
 *
 * Output
 *
 * Print an integer representing the maximum length of a stable continuous window.
 *
 * Constraints
 *
 * 1 <= size of sensorLog <= 2 * 10^5
 * 0 <= maxRepairs <= size of sensorLog
 * sensorLog contains only 0 and 1
 *
 * Example
 *
 * Input:
 * 1101001110
 * 2
 *
 * Output:
 * 7
 *
 * Explanation:
 *
 * By repairing the two failed seconds inside the window "1010011",
 * the window can be turned into seven continuous 1s.
 * Hence, the answer is 7.
 *
 * 备注
 *
 * 模拟题，不是原 SHL 题。
 *
 * 相似题：
 * 1. NumberOfWaysToObtainTheLongestConsecutiveOnes
 * 2. LeetCode 1004
 *
 * 学习重点：
 *
 * 这题练的是滑动窗口的标准反应：
 * “窗口里某种坏东西的数量不能超过 K”。
 */
public class LongestStableSensorWindowAfterRepairingKFailures {

    public static void main(String[] args) {
        String sensorLog = "1101001110";
        int maxRepairs = 2;

        LongestStableSensorWindowAfterRepairingKFailures solver =
                new LongestStableSensorWindowAfterRepairingKFailures();
        System.out.println(solver.longestStableWindow(sensorLog, maxRepairs));
    }

    /*
     * Practice Mode
     *
     * Before reading the code, ask yourself:
     *
     * 1. What should the window maintain?
     * 2. When should left move?
     * 3. What does "valid window" mean here?
     */

    public int longestStableWindow(String sensorLog, int maxRepairs) {
        int left = 0;
        int failures = 0;
        int best = 0;

        for (int right = 0; right < sensorLog.length(); right++) {
            if (sensorLog.charAt(right) == '0') {
                failures++;
            }

            while (failures > maxRepairs) {
                if (sensorLog.charAt(left) == '0') {
                    failures--;
                }
                left++;
            }

            best = Math.max(best, right - left + 1);
        }

        return best;
    }
}

