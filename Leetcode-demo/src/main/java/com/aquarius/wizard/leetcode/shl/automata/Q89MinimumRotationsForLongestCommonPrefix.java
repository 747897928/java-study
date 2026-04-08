package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 89
 *
 * Peter has two strings of the same length. The first string is fixed and the second string is
 * rotatable. In the left rotation, the first character is removed and added to the end of the
 * string. In the right rotation, the last character is removed and added to the start of the
 * string. Peter is interested in knowing the longest common prefix of both the strings.
 *
 * Write an algorithm to help Peter find the minimum number of rotations required to find the
 * longest common prefix. If no prefix is common then output -1.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. fixedString
 * 2. rotatableString
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q89MinimumRotationsForLongestCommonPrefix {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fixed = scanner.next();
        String rotatable = scanner.next();

        Q89MinimumRotationsForLongestCommonPrefix solver =
            new Q89MinimumRotationsForLongestCommonPrefix();
        System.out.println(solver.minimumRotations(fixed, rotatable));
    }

    public int minimumRotations(String fixed, String rotatable) {
        if (fixed.length() != rotatable.length()) {
            return -1;
        }

        int n = fixed.length();
        int bestPrefix = 0;
        int bestRotations = Integer.MAX_VALUE;
        for (int leftRotation = 0; leftRotation < n; leftRotation++) {
            int prefix = commonPrefixLength(fixed, rotatable, leftRotation);
            int rotations = Math.min(leftRotation, n - leftRotation);
            if (prefix > bestPrefix || (prefix == bestPrefix && rotations < bestRotations)) {
                bestPrefix = prefix;
                bestRotations = rotations;
            }
        }
        return bestPrefix == 0 ? -1 : bestRotations;
    }

    private int commonPrefixLength(String fixed, String rotatable, int leftRotation) {
        int count = 0;
        for (int i = 0; i < fixed.length(); i++) {
            char rotated = rotatable.charAt((i + leftRotation) % rotatable.length());
            if (fixed.charAt(i) != rotated) {
                break;
            }
            count++;
        }
        return count;
    }
}
