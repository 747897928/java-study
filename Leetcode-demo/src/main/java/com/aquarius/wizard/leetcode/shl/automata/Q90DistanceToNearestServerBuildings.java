package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 90
 *
 * An IT park consists of similar buildings. During the initial construction of the IT park, the
 * telecom company “Nazania Communication” used some of the buildings for its servers. Now that
 * the IT park has been fully developed, all the buildings need internet connections. The telecom
 * company wants to connect each building to the nearest building where a server has already been
 * set. To plan for this, the company creates a rectangular aerial view of the buildings in the IT
 * park in the form of a M*N grid. Each cell of the grid represents a building. For all the
 * buildings that do not have servers, the company wants to find the minimum number of buildings
 * (horizontally and/or vertically) that separate the buildings that need internet connections
 * from buildings that have servers.
 *
 * Write an algorithm to find the minimum number of buildings (horizontally and/or vertically)
 * that separate the buildings that need internet connections from buildings that have servers.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. rows cols
 * 2. rows * cols integers, where 1 means server building and 0 means ordinary building
 *
 * Output format:
 * A distance matrix with 0 for server cells.
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q90DistanceToNearestServerBuildings {

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

        Q90DistanceToNearestServerBuildings solver = new Q90DistanceToNearestServerBuildings();
        System.out.print(solver.distanceMatrix(grid));
    }

    public String distanceMatrix(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] distance = new int[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[] {i, j});
                } else {
                    distance[i][j] = -1;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nx = current[0] + DX[d];
                int ny = current[1] + DY[d];
                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || distance[nx][ny] != -1) {
                    continue;
                }
                distance[nx][ny] = distance[current[0]][current[1]] + 1;
                queue.offer(new int[] {nx, ny});
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            if (i > 0) {
                builder.append('\n');
            }
            for (int j = 0; j < cols; j++) {
                if (j > 0) {
                    builder.append(' ');
                }
                builder.append(distance[i][j]);
            }
        }
        return builder.toString();
    }
}
