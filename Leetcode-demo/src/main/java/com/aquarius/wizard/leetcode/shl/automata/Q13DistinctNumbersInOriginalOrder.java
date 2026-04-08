package com.aquarius.wizard.leetcode.shl.automata;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 13
 *
 * You are given a list of numbers.
 *
 * Write an algorithm to remove all the duplicate numbers of the list so that the list contains
 * only distinct numbers in the same order as they appear in the input list.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. listSize
 * 2. listSize integers
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q13DistinctNumbersInOriginalOrder {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize = scanner.nextInt();
        int[] numbers = new int[listSize];
        for (int i = 0; i < listSize; i++) {
            numbers[i] = scanner.nextInt();
        }

        /*
         * 本地自测输入：
         *
         * int[] numbers = {4, 1, 4, 2, 1, 3, 3};
         */

        Q13DistinctNumbersInOriginalOrder solver = new Q13DistinctNumbersInOriginalOrder();
        System.out.println(solver.formatDistinct(numbers));
    }

    public String formatDistinct(int[] numbers) {
        Set<Integer> seen = new LinkedHashSet<>();
        for (int number : numbers) {
            seen.add(number);
        }

        StringBuilder builder = new StringBuilder();
        for (int number : seen) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(number);
        }
        return builder.toString();
    }
}
