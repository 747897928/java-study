package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 22
 *
 * Max does not like any line segment that he sees on the X-axis. Today, his brother drew N line
 * segments on it. Max has a magical marker that can erase all the line segments that pass through
 * a point on the X-axis when he places the marker on that point. For example, he wishes to erase
 * two line segments — one with endpoints (1,0) and (4,0), and another with endpoints (3,0) and
 * (7,0). He can clear both lines at once by placing the marker either at point (3,0) or (4,0) but
 * he cannot do so if he places the marker anywhere else.
 *
 * Write an algorithm to find the minimum number of times Max must use the marker to clear the
 * X-axis.
 *
 * 补充说明
 *
 * 这份代码里我先约定下面这种输入格式：
 * 1. segmentCount
 * 2. segmentCount lines of left right
 *
 * <p>create: 2026-04-01 23:10:02</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class Q22MinimumMarkerUsesToEraseSegments {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int segmentCount = scanner.nextInt();
        int[][] segments = new int[segmentCount][2];
        for (int i = 0; i < segmentCount; i++) {
            segments[i][0] = scanner.nextInt();
            segments[i][1] = scanner.nextInt();
        }

        Q22MinimumMarkerUsesToEraseSegments solver = new Q22MinimumMarkerUsesToEraseSegments();
        System.out.println(solver.minimumMarkerUses(segments));
    }

    public int minimumMarkerUses(int[][] segments) {
        Arrays.sort(segments, (a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1])
            : Integer.compare(a[0], b[0]));

        int uses = 0;
        Integer lastPoint = null;
        for (int[] segment : segments) {
            if (lastPoint != null && segment[0] <= lastPoint && lastPoint <= segment[1]) {
                continue;
            }
            uses++;
            lastPoint = segment[1];
        }
        return uses;
    }
}
