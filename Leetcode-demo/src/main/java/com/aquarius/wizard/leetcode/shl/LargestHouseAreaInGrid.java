package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;


/**
 * Question
 *
 * The city authorities conduct a study of the houses in a residential area for a city planning
 * scheme. The area is depicted in an aerial view and divided into an N x M grid. If a grid cell
 * contains some part of a house roof, then it is assigned the value 1; otherwise, the cell
 * represents a vacant plot and is assigned the value 0. Clusters of adjacent grid cells with value
 * 1 represent a single house. Diagonally placed grids with value 1 do not represent a single
 * house. The area of a house is the number of 1s that it spans.
 *
 * Write an algorithm to find the area of the largest house.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers - rows and cols representing
 * the number of rows (N) and the number of columns in the grid (M), respectively.
 * The next N lines consist of M space-separated integers representing the grid.
 *
 * Output
 *
 * Print an integer representing the area of the largest house.
 *
 * Example
 *
 * Input:
 * 5 5
 * 0 0 0 0 0
 * 0 1 1 0 0
 * 0 0 0 0 0
 * 0 0 1 1 0
 * 0 0 1 0 0
 *
 * Output:
 * 3
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：网格连通块、DFS/BFS。
 * 校对：题面稳定。
 * 提示：只算四连通，不算对角线。
 */
public class LargestHouseAreaInGrid {

    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rowCount = scanner.nextInt();
        int columnCount = scanner.nextInt();
        int[][] grid = new int[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int rowCount = 5;
         * int columnCount = 5;
         * int[][] grid = {{0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 1, 0}, {0, 0, 1, 0, 0}};
         */

        LargestHouseAreaInGrid solver = new LargestHouseAreaInGrid();
        System.out.println(solver.largestArea(grid));
    }

    public int largestArea(int[][] grid) {
        int rows = grid.length;
        int cols = rows == 0 ? 0 : grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int best = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1 && !visited[row][col]) {
                    best = Math.max(best, dfs(grid, visited, row, col));
                }
            }
        }
        return best;
    }

    private int dfs(int[][] grid, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        int area = 1;
        for (int[] direction : DIRECTIONS) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (nextRow < 0 || nextRow >= grid.length || nextCol < 0 || nextCol >= grid[0].length) {
                continue;
            }
            if (grid[nextRow][nextCol] == 1 && !visited[nextRow][nextCol]) {
                area += dfs(grid, visited, nextRow, nextCol);
            }
        }
        return area;
    }
}
