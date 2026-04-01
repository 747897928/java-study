package com.aquarius.wizard.leetcode.shl.automata;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 27
 *
 * Given a route in a straight line. N buses operate between various bus stations. There is a
 * workstation at the start of the route. The distances of the bus stations from the workstation
 * are calculated. The transportation authority wishes to decrease the number of buses that it
 * operates in the city. If any buses are found to have overlapping routes, then these buses will
 * be replaced by a single bus. The authority wishes to determine how many buses will remain after
 * the buses with overlapping routes have been eliminated.
 *
 * Write an algorithm to find how many buses will remain after the buses with overlapping routes
 * have been eliminated.
 *
 * Notes
 *
 * This learning version uses:
 * 1. routeCount
 * 2. routeCount lines of start end
 */
public class Q27RemainingBusRoutesAfterMergingOverlaps {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int routeCount = scanner.nextInt();
        int[][] routes = new int[routeCount][2];
        for (int i = 0; i < routeCount; i++) {
            routes[i][0] = scanner.nextInt();
            routes[i][1] = scanner.nextInt();
        }

        Q27RemainingBusRoutesAfterMergingOverlaps solver = new Q27RemainingBusRoutesAfterMergingOverlaps();
        System.out.println(solver.remainingRoutes(routes));
    }

    public int remainingRoutes(int[][] routes) {
        if (routes.length == 0) {
            return 0;
        }
        Arrays.sort(routes, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0])
            : Integer.compare(a[1], b[1]));

        int groups = 1;
        int currentEnd = routes[0][1];
        for (int i = 1; i < routes.length; i++) {
            if (routes[i][0] <= currentEnd) {
                currentEnd = Math.max(currentEnd, routes[i][1]);
            } else {
                groups++;
                currentEnd = routes[i][1];
            }
        }
        return groups;
    }
}
