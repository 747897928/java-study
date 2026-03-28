package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * A digital machine generates binary data which consists of a string of 0s and 1s. A maximum
 * signal M, in the data consists of the maximum number of either 1s or 0s appearing consecutively
 * in the data but M can't be at the beginning or end of the string.
 *
 * Design a way to find the length of the maximum signal.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the length of the binary
 * string.
 * The second line consists of a string of length N consisting of 0s and 1s only.
 *
 * Output
 *
 * Print an integer representing the length of the maximum signal.
 *
 * Example 1
 *
 * Input:
 * 6
 * 101000
 *
 * Output:
 * 1
 *
 * Example 2
 *
 * Input:
 * 9
 * 101111110
 *
 * Output:
 * 6
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：连续段统计。
 * 校对：题面稳定。
 * 提示：只有完全处于字符串内部的连续段才算，所以前缀段和后缀段都不能直接计入答案。
 */
public class MaximumInteriorSignalLength {

    public static void main(String[] args) {
        int stringLength = 6;
        String signal = "101000";

        if (signal.length() != stringLength) {
            throw new IllegalArgumentException("signal.length() must equal stringLength");
        }

        MaximumInteriorSignalLength solver = new MaximumInteriorSignalLength();
        System.out.println(solver.maximumLength(signal));
    }

    public int maximumLength(String signal) {
        int best = 0;
        int left = 0;
        while (left < signal.length()) {
            int right = left;
            while (right < signal.length() && signal.charAt(right) == signal.charAt(left)) {
                right++;
            }
            if (left > 0 && right < signal.length()) {
                best = Math.max(best, right - left);
            }
            left = right;
        }
        return best;
    }
}
