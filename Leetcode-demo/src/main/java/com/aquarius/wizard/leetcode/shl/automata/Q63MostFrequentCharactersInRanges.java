package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 63
 *
 * Mitchell has invented a machine that outputs the most frequently occurring characters in a
 * string that lie in the range [L, R]. The machine accepts a series of characters and asks the
 * user to input two numbers, L and R. The machine outputs the characters for all the pairs of [L,
 * R] values the user provides.
 *
 * Write an algorithm to help Mitchell find the output for all the inputs he provides.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. text
 * 2. queryCount
 * 3. queryCount lines: left right (1-based, inclusive)
 *
 * For ties, this version prints all most-frequent characters in ascending character order.
 */
public class Q63MostFrequentCharactersInRanges {

    private static final int ASCII = 128;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.next();
        int queryCount = scanner.nextInt();
        int[][] queries = new int[queryCount][2];
        for (int i = 0; i < queryCount; i++) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }

        Q63MostFrequentCharactersInRanges solver = new Q63MostFrequentCharactersInRanges();
        System.out.print(solver.answerQueries(text, queries));
    }

    public String answerQueries(String text, int[][] queries) {
        /*
         * 题目会反复问很多次区间 [L, R]，
         * 每次都重新数一遍频次当然能做，但如果查询很多，就会重复扫描同一段字符串。
         *
         * 这里的标准做法是“前缀频次数组”：
         * prefix[i + 1][c] 表示 text[0..i] 里字符 c 出现了多少次。
         *
         * 那么任意区间 [left, right] 里字符 c 的出现次数就可以 O(1) 算出：
         * prefix[right + 1][c] - prefix[left][c]
         *
         * 整体流程是：
         * 1. 先预处理整串的前缀频次
         * 2. 每个查询先扫一遍字符集，找出最大频次
         * 3. 再扫一遍字符集，把所有频次等于最大值的字符输出
         *
         * 因为这里固定只处理 ASCII 128 个字符，
         * 所以每次查询再扫一遍字符集的成本是可控的。
         */
        int[][] prefix = new int[text.length() + 1][ASCII];
        for (int i = 0; i < text.length(); i++) {
            System.arraycopy(prefix[i], 0, prefix[i + 1], 0, ASCII);
            prefix[i + 1][text.charAt(i)]++;
        }

        StringBuilder result = new StringBuilder();
        for (int q = 0; q < queries.length; q++) {
            int left = queries[q][0] - 1;
            int right = queries[q][1] - 1;
            int maxFreq = 0;
            for (int c = 0; c < ASCII; c++) {
                // 用两份前缀频次相减，得到字符 c 在当前区间里的出现次数。
                int freq = prefix[right + 1][c] - prefix[left][c];
                maxFreq = Math.max(maxFreq, freq);
            }
            if (q > 0) {
                result.append('\n');
            }
            for (int c = 0; c < ASCII; c++) {
                if (prefix[right + 1][c] - prefix[left][c] == maxFreq) {
                    // 题目要的是“最频繁字符”，如果并列，就把并列字符都输出。
                    result.append((char) c);
                }
            }
        }
        return result.toString();
    }
}
