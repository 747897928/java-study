package com.aquarius.wizard.leetcode.shl.automata;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 95
 *
 * Given a matrix A[1..N][1..M] of integers, the product of a submatrix of A is the product of all
 * the elements of the submatrix.
 *
 * Write an algorithm to find the number of non-empty submatrices that contain the top left
 * element of
 *
 * matrix A (i.e. submatrices B[1..X][1..Y] for 1 ≤ X ≤ N, 1 ≤ Y ≤ M) with a product ≤ K.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. rows cols limitK
 * 2. rows * cols positive integers
 *
 * This version assumes matrix elements are positive so prefix-product division remains exact.
 */
public class Q95CountTopLeftSubmatricesWithProductAtMostK {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        long limit = scanner.nextLong();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        Q95CountTopLeftSubmatricesWithProductAtMostK solver =
            new Q95CountTopLeftSubmatricesWithProductAtMostK();
        System.out.println(solver.countTopLeftSubmatrices(matrix, limit));
    }

    public int countTopLeftSubmatrices(int[][] matrix, long limit) {
        BigInteger[][] prefix = new BigInteger[matrix.length][matrix[0].length];
        BigInteger target = BigInteger.valueOf(limit);
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                BigInteger product = BigInteger.valueOf(matrix[i][j]);
                if (i > 0) {
                    product = product.multiply(prefix[i - 1][j]);
                }
                if (j > 0) {
                    product = product.multiply(prefix[i][j - 1]);
                }
                if (i > 0 && j > 0) {
                    product = product.divide(prefix[i - 1][j - 1]);
                }
                prefix[i][j] = product;
                if (product.compareTo(target) <= 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
