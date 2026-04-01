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
                int freq = prefix[right + 1][c] - prefix[left][c];
                maxFreq = Math.max(maxFreq, freq);
            }
            if (q > 0) {
                result.append('\n');
            }
            for (int c = 0; c < ASCII; c++) {
                if (prefix[right + 1][c] - prefix[left][c] == maxFreq) {
                    result.append((char) c);
                }
            }
        }
        return result.toString();
    }
}
