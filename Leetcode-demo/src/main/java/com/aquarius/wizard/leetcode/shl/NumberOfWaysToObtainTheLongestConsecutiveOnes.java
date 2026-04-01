package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


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
 * 备注
 *
 * 难度：中等。
 *
 * 考点：滑动窗口。
 * 校对：原题里的 different ways 有明显歧义。这里直接按当前代码改写为“统计最优窗口数”的学习版定稿，不再保留会把读者带向“结果串去重”的原句。
 * 提示：校验器里保留了 `S = 101, K = 0` 这种能区分“窗口数”和“结果串数”的反例，方便后续继续考证。
 * 相似题：LeetCode 1004 Max Consecutive Ones III、所有“最多 K 个坏字符”的最长子串题。
 *
 * 这题不要一上来就纠结“ways”是什么意思。
 * 当前学习版已经定稿成：
 *
 * 1. 先求最长可行窗口长度 L
 * 2. 再统计有多少个长度恰好为 L 的窗口满足“0 的个数 <= K”
 *
 * 所以这里其实是两个小问题拼起来：
 *
 * 问题 A：最长窗口多长
 * 问题 B：这种最长窗口有几个
 *
 * A 用滑动窗口最自然。
 * B 在知道 L 之后，再扫一遍计数就行。
 */
public class NumberOfWaysToObtainTheLongestConsecutiveOnes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String binaryString = scanner.next();
        int changeK = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * String binaryString = "1010101";
         * int changeK = 1;
         */

        NumberOfWaysToObtainTheLongestConsecutiveOnes solver = new NumberOfWaysToObtainTheLongestConsecutiveOnes();
        System.out.println(solver.countWays(binaryString, changeK));
    }

    /**
     * 这一题之所以分两步写，是因为：
     *
     * 你在第一次扫描时，并不知道最终的最优长度 L 是多少。
     *
     * 所以正确的思路是：
     *
     * 第一步：先只关心“最长能到多少”
     * 第二步：长度 L 已经确定后，再专门去数“有多少个这样的窗口”
     *
     * 这也是很多面试题会用到的套路：
     *
     * 先求最优值，再求达到最优值的方案数 / 窗口数 / 区间数。
     */
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

    /**
     * 这是标准的“窗口内坏字符数量不超过 K”模板。
     *
     * 这里的坏字符就是 0。
     *
     * 窗口移动规则是：
     *
     * 1. right 不断向右扩，把新字符纳入窗口
     * 2. 如果 0 的数量超了，就移动 left 缩窗
     * 3. 保证循环结束时，窗口始终合法
     *
     * 一旦窗口始终合法，
     * right - left + 1 就是“以 right 结尾的最长合法窗口长度”。
     *
     * 所以一边扫，一边更新 maxLength 即可。
     */
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
