package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 49
 *
 * A square matrix A[1..n][1..n] is called palindromic if A[i][j] = A[n + 1 - i][n + 1 - j] for
 * all 1 ≤ i, j ≤ n.
 *
 * Given a matrix inputMat[1..N][1..M], find the number of elements in its largest palindromic
 * square sub-matrix.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. rows cols
 * 2. rows * cols integers
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q49LargestPalindromicSquareElementCount {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        Q49LargestPalindromicSquareElementCount solver =
            new Q49LargestPalindromicSquareElementCount();
        System.out.println(solver.largestPalindromicSquareElementCount(matrix));
    }

    public int largestPalindromicSquareElementCount(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int size = Math.min(rows, cols); size >= 1; size--) {
            for (int top = 0; top + size <= rows; top++) {
                for (int left = 0; left + size <= cols; left++) {
                    if (isPalindromicSquare(matrix, top, left, size)) {
                        return size * size;
                    }
                }
            }
        }
        return 0;
    }

    private boolean isPalindromicSquare(int[][] matrix, int top, int left, int size) {
        for (int dx = 0; dx < size; dx++) {
            for (int dy = 0; dy < size; dy++) {
                int mirrorX = size - 1 - dx;
                int mirrorY = size - 1 - dy;
                if (dx > mirrorX || (dx == mirrorX && dy > mirrorY)) {
                    continue;
                }
                if (matrix[top + dx][left + dy] != matrix[top + mirrorX][left + mirrorY]) {
                    return false;
                }
            }
        }
        return true;
    }
}
