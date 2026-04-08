package com.aquarius.wizard.leetcode.shl.automata;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 46
 *
 * Emerson is very fond of strings, and he keeps trying to reverse them. His mother gives him two
 * binary strings and asks him to convert the binary string str1 into str2 by applying the
 * following rules:
 *
 * Step 1: Reverse any substring of length 2 (of str1) and get str1' ( str1 != str1' ).
 *
 * Step 2: Reverse any substring of length 3 (of str1') and get str1'' ( str1' != str1'' ).
 *
 * Step 3: Reverse any substring of length 4 (of str1'') and get str1''' ( str1'' != str1''' ) .
 *
 * Step 4, Step 5 and so on.
 *
 * Write an algorithm to help Emerson convert the binary string str1 into str2, in the minimum
 * number of steps.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. sourceBinaryString
 * 2. targetBinaryString
 *
 * This version follows the literal step order from the statement:
 * step 1 must reverse a length-2 substring, step 2 a length-3 substring, and so on.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q46MinimumStepsToConvertBinaryStringByIncreasingReversals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String source = scanner.next();
        String target = scanner.next();

        Q46MinimumStepsToConvertBinaryStringByIncreasingReversals solver =
            new Q46MinimumStepsToConvertBinaryStringByIncreasingReversals();
        System.out.println(solver.minimumSteps(source, target));
    }

    public int minimumSteps(String source, String target) {
        if (source.length() != target.length()) {
            return -1;
        }
        if (source.equals(target)) {
            return 0;
        }

        Set<String> current = new HashSet<>();
        current.add(source);
        int n = source.length();

        for (int len = 2; len <= n; len++) {
            Set<String> next = new HashSet<>();
            for (String state : current) {
                for (int start = 0; start + len <= n; start++) {
                    String reversed = reverseSubstring(state, start, len);
                    if (!reversed.equals(state)) {
                        next.add(reversed);
                    }
                }
            }
            if (next.contains(target)) {
                return len - 1;
            }
            if (next.isEmpty()) {
                break;
            }
            current = next;
        }
        return -1;
    }

    private String reverseSubstring(String value, int start, int length) {
        char[] chars = value.toCharArray();
        int left = start;
        int right = start + length - 1;
        while (left < right) {
            char tmp = chars[left];
            chars[left] = chars[right];
            chars[right] = tmp;
            left++;
            right--;
        }
        return new String(chars);
    }
}
