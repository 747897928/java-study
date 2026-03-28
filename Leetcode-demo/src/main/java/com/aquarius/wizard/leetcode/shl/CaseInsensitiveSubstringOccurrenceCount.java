package com.aquarius.wizard.leetcode.shl;

/**
 * Question
 *
 * You are given two strings containing only English letters. Write an algorithm to count the
 * number of occurrences of the second string in the first string. (You may disregard the case of
 * the letters.)
 *
 * Input
 *
 * The first line of the input consists of a string parent, representing the first string.
 * The second line consists of a string sub, representing the second string.
 *
 * Output
 *
 * Print an integer representing the number of occurrences of sub in parent. If no occurrence of
 * sub is found in parent, then print 0.
 *
 * Example
 *
 * Input:
 * TimisplayinginthehouseofTimwiththetoysofTim
 * Tim
 *
 * Output:
 * 3
 *
 * Explanation:
 * Tim occurs 3 times in the first string.
 * So, the output is 3.
 *
 * 我的备注
 *
 * 难度：简单。
 *
 * 考点：字符串匹配、大小写归一化。
 * 校对：样例已做代码校验。
 * 当前实现按所有起点计数，允许重叠匹配；这也是我认为最稳妥的题意理解。
 */
public class CaseInsensitiveSubstringOccurrenceCount {

    public int countOccurrences(String parent, String sub) {
        String source = parent.toLowerCase();
        String target = sub.toLowerCase();
        int count = 0;
        for (int i = 0; i + target.length() <= source.length(); i++) {
            if (source.startsWith(target, i)) {
                count++;
            }
        }
        return count;
    }
}
