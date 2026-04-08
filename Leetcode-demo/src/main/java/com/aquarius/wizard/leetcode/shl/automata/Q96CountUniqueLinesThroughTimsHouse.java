package com.aquarius.wizard.leetcode.shl.automata;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 96
 *
 * Tim, a seventh grade student, is introduced to the concept of lines in basic geometry class. He
 * applies this concept in real life. If he considers his house as one point and his friend Bill's
 * house as another point, he can draw a line between these two points. Similarly, if the houses of
 * all his friends are considered as different points, he can draw multiple lines with his own
 * house as the common point in each line. By taking his school as the reference, Tim marks the
 * coordinates of his N friends' houses.
 *
 * Write an algorithm to help Tim that takes the coordinates of his house (x , y ) and his friends'
 * houses (x , y ) and outputs the number of unique lines that can be drawn with Tim's house as the
 * common point in each line.
 *
 * 补充说明
 *
 * docx 里只保留了题干，没有给出特别标准的输入模板。
 * 这份代码里我先约定下面这种输入格式：
 * 1. friendCount
 * 2. homeX homeY
 * 3. friendCount lines of x y
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q96CountUniqueLinesThroughTimsHouse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int friendCount = scanner.nextInt();
        int homeX = scanner.nextInt();
        int homeY = scanner.nextInt();
        int[][] friends = new int[friendCount][2];
        for (int i = 0; i < friendCount; i++) {
            friends[i][0] = scanner.nextInt();
            friends[i][1] = scanner.nextInt();
        }

        /*
         * 本地自测输入：
         *
         * int homeX = 0;
         * int homeY = 0;
         * int[][] friends = {
         *     {1, 1},
         *     {2, 2},
         *     {1, 0},
         *     {0, 3}
         * };
         */

        Q96CountUniqueLinesThroughTimsHouse solver = new Q96CountUniqueLinesThroughTimsHouse();
        System.out.println(solver.countUniqueLines(homeX, homeY, friends));
    }

    public int countUniqueLines(int homeX, int homeY, int[][] friends) {
        Set<String> slopes = new HashSet<>();
        for (int[] friend : friends) {
            int dx = friend[0] - homeX;
            int dy = friend[1] - homeY;
            if (dx == 0 && dy == 0) {
                continue;
            }
            int gcd = gcd(Math.abs(dx), Math.abs(dy));
            dx /= gcd;
            dy /= gcd;
            if (dx < 0) {
                dx = -dx;
                dy = -dy;
            } else if (dx == 0) {
                dy = dy > 0 ? 1 : -1;
            } else if (dy == 0) {
                dx = 1;
            }
            slopes.add(dx + "/" + dy);
        }
        return slopes.size();
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a == 0 ? 1 : a;
    }
}
