package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * Given a binary string S consisting only of 0s and 1s, you may change at most K zeros inside a
 * substring into ones. Let L be the maximum possible length of a substring that can be turned into
 * all ones in this way.
 *
 * Write an algorithm to find the number of substrings whose length is exactly L and that contain at
 * most K zeros.
 *
 * Input
 *
 * The first line of the input consists of the string S.
 * The second line consists of an integer changeK, representing the maximum number of zeros that can
 * be changed (K).
 *
 * Output
 *
 * Print an integer representing the number of substrings whose length is equal to the maximum
 * achievable value L and that contain at most K zeros.
 *
 * Constraints
 *
 * 1 <= size of string <= 2*10^5
 * 0 <= changeK <= size
 *
 * Example
 *
 * Input:
 * 1010101
 * 1
 *
 * Output:
 * 3
 *
 * Explanation:
 * The maximum achievable length is 3.
 * There are exactly three substrings of length 3 that contain at most one 0: the three occurrences
 * of 101. Each such substring can be turned into 111 by changing one 0. So, the output is 3.
 *
 * 我的备注
 *
 * 难度：中等。
 *
 * 考点：滑动窗口。
 * 校对：原题里的 different ways 有明显歧义。这里直接按当前代码改写为“统计最优窗口数”的学习版定稿，不再保留会把读者带向“结果串去重”的原句。
 * 提示：校验器里保留了 `S = 101, K = 0` 这种能区分“窗口数”和“结果串数”的反例，方便后续继续考证。
 */
public class NumberOfWaysToObtainTheLongestConsecutiveOnes {

    public int countWays(String binaryString, int changeK) {
        int maxLength = findMaximumLength(binaryString, changeK);
        if (maxLength == 0) {
            return 1;
        }

        int[] prefixZeros = new int[binaryString.length() + 1];
        for (int i = 0; i < binaryString.length(); i++) {
            prefixZeros[i + 1] = prefixZeros[i] + (binaryString.charAt(i) == '0' ? 1 : 0);
        }

        int ways = 0;
        for (int start = 0; start + maxLength <= binaryString.length(); start++) {
            int zeroCount = prefixZeros[start + maxLength] - prefixZeros[start];
            if (zeroCount <= changeK) {
                ways++;
            }
        }
        return ways;
    }

    private int findMaximumLength(String binaryString, int changeK) {
        int left = 0;
        int zeroCount = 0;
        int maxLength = 0;
        for (int right = 0; right < binaryString.length(); right++) {
            if (binaryString.charAt(right) == '0') {
                zeroCount++;
            }
            while (zeroCount > changeK) {
                if (binaryString.charAt(left) == '0') {
                    zeroCount--;
                }
                left++;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
