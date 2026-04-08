package com.aquarius.wizard.leetcode.shl.automata;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 75
 *
 * A mouse is placed in a maze. There is a huge chunk of cheese somewhere in the maze. The maze is
 * represented as an N x M grid of integers where 0 represents a wall, 1 represents the path where
 * the mouse can move and 9 represents the chunk of cheese. The mouse starts at the top left corner
 * at (0,0).
 *
 * Write an algorithm to output 1 if the mouse can reach the chunk of cheese, else output 0.
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
public class Q75MouseCanReachCheese {

    private static final int[] DX = {1, -1, 0, 0};
    private static final int[] DY = {0, 0, 1, -1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] maze = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测输入：
         *
         * int[][] maze = {
         *     {1, 1, 0},
         *     {0, 1, 9},
         *     {0, 1, 0}
         * };
         */

        Q75MouseCanReachCheese solver = new Q75MouseCanReachCheese();
        System.out.println(solver.canReachCheese(maze) ? 1 : 0);
    }

    public boolean canReachCheese(int[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        if (maze[0][0] == 0) {
            return false;
        }

        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            if (maze[x][y] == 9) {
                return true;
            }
            for (int d = 0; d < 4; d++) {
                int nx = x + DX[d];
                int ny = y + DY[d];
                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols) {
                    continue;
                }
                if (visited[nx][ny] || maze[nx][ny] == 0) {
                    continue;
                }
                visited[nx][ny] = true;
                queue.offer(new int[]{nx, ny});
            }
        }
        return false;
    }
}
