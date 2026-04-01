package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Question
 *
 * The city bus stations are located at equal distances (unit distance) from each other along a
 * straight road. Each station has a unique station ID. The buses do not travel to all of the bus
 * stations. The highway administration needs to determine the total distance that the buses cover.
 *
 * Given the IDs of the bus stations that have a bus operating between them, write an algorithm to
 * help the administration find the distance covered by all the city buses.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers num and constM, representing
 * the number of buses (N) and constM is always 2.
 * Next N lines consist of constM space-separated integers - busStop0 and busStop1 representing the
 * IDs of the bus stations that have a bus operating between them.
 *
 * Output
 *
 * Print an integer representing the distance covered by the buses.
 *
 * Example
 *
 * Input:
 * 3 2
 * 2 4
 * 3 5
 * 6 7
 *
 * Output:
 * 4
 *
 * Explanation:
 * Their union on the road is [2,5] and [6,7], so the total covered distance is 3 + 1 = 4.
 *
 * 备注
 *
 * 难度：简单。
 *
 * 考点：区间合并。
 * 校对：题意稳定。
 * 提示：站点等距时区间 [l,r] 的覆盖距离是 r-l，不是端点数量。
 */
public class TotalBusRouteCoverageDistance {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int busCount = scanner.nextInt();
        int routeWidth = scanner.nextInt();
        int[][] busRoutes = new int[busCount][routeWidth];
        for (int i = 0; i < busCount; i++) {
            for (int j = 0; j < routeWidth; j++) {
                busRoutes[i][j] = scanner.nextInt();
            }
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int busCount = 3;
         * int routeWidth = 2;
         * int[][] busRoutes = {{2, 4}, {3, 5}, {6, 7}};
         */

        TotalBusRouteCoverageDistance solver = new TotalBusRouteCoverageDistance();
        System.out.println(solver.totalDistance(busRoutes));
    }

    public int totalDistance(int[][] busRoutes) {
        if (busRoutes.length == 0) {
            return 0;
        }

        int[][] intervals = new int[busRoutes.length][2];
        for (int i = 0; i < busRoutes.length; i++) {
            int left = Math.min(busRoutes[i][0], busRoutes[i][1]);
            int right = Math.max(busRoutes[i][0], busRoutes[i][1]);
            intervals[i][0] = left;
            intervals[i][1] = right;
        }
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        int total = 0;
        int currentLeft = intervals[0][0];
        int currentRight = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= currentRight) {
                currentRight = Math.max(currentRight, intervals[i][1]);
            } else {
                total += currentRight - currentLeft;
                currentLeft = intervals[i][0];
                currentRight = intervals[i][1];
            }
        }
        total += currentRight - currentLeft;
        return total;
    }
}
