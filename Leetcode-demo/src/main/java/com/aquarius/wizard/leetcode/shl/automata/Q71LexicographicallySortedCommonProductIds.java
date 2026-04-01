package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 71
 *
 * The manager of the grocery company tagGrocery wishes to identify which products are most
 * frequently purchased by the customers. He selects N customers that purchase combo bags of
 * products. Each combo bag consists of M products and each product is labeled with a productID.
 * He needs to find the productIDs of the products that are purchased by all the N customers in
 * common.
 *
 * Write an algorithm to help the manager find the lexicographically sorted productIDs of the
 * products that are most frequently purchased by all the N customers.
 *
 * Notes
 *
 * This learning version uses:
 * 1. customerCount bagSize
 * 2. customerCount * bagSize integers
 */
public class Q71LexicographicallySortedCommonProductIds {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int customerCount = scanner.nextInt();
        int bagSize = scanner.nextInt();
        int[][] bags = new int[customerCount][bagSize];
        for (int i = 0; i < customerCount; i++) {
            for (int j = 0; j < bagSize; j++) {
                bags[i][j] = scanner.nextInt();
            }
        }

        Q71LexicographicallySortedCommonProductIds solver =
            new Q71LexicographicallySortedCommonProductIds();
        System.out.println(solver.commonProducts(bags));
    }

    public String commonProducts(int[][] bags) {
        Set<Integer> common = new TreeSet<>();
        for (int value : bags[0]) {
            common.add(value);
        }

        for (int i = 1; i < bags.length; i++) {
            Set<Integer> current = new TreeSet<>();
            for (int value : bags[i]) {
                current.add(value);
            }
            common.retainAll(current);
        }

        if (common.isEmpty()) {
            return "-1";
        }

        StringBuilder builder = new StringBuilder();
        for (int value : common) {
            if (!builder.isEmpty()) {
                builder.append(' ');
            }
            builder.append(value);
        }
        return builder.toString();
    }
}
