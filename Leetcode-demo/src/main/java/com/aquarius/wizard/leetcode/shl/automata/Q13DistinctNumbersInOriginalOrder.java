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
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. listSize
 * 2. listSize integers
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
         * Local practice input:
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
