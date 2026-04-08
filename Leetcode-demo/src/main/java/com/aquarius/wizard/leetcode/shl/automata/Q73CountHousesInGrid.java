package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 73
 *
 * The city authorities need to know the number of houses in a residential area for future
 * planning. The area is depicted in an aerial view and divided into an N x M grid. If a
 * particular grid cell contains some part of the house roof, then it is given a value 1. If the
 * cell is vacant, then it is given a value 0. Clusters of adjacent grid cells with value 1
 * represent a single house. Diagonal grids with value 1 do not represent the same house.
 *
 * Write an algorithm to find the total number of houses in the area.
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
public class Q73CountHousesInGrid {

    private static final int[] DX = {1, -1, 0, 0};
    private static final int[] DY = {0, 0, 1, -1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测输入：
         *
         * int[][] grid = {
         *     {1, 0, 0, 1},
         *     {1, 1, 0, 0},
         *     {0, 0, 1, 1}
         * };
         */

        Q73CountHousesInGrid solver = new Q73CountHousesInGrid();
        System.out.println(solver.countHouses(grid));
    }

    public int countHouses(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int houses = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    houses++;
                    dfs(grid, i, j, visited);
                }
            }
        }
        return houses;
    }

    private void dfs(int[][] grid, int x, int y, boolean[][] visited) {
        visited[x][y] = true;
        for (int d = 0; d < 4; d++) {
            int nx = x + DX[d];
            int ny = y + DY[d];
            if (nx < 0 || ny < 0 || nx >= grid.length || ny >= grid[0].length) {
                continue;
            }
            if (grid[nx][ny] == 1 && !visited[nx][ny]) {
                dfs(grid, nx, ny, visited);
            }
        }
    }
}
