package com.aquarius.wizard.leetcode.shl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * Candidate reconstruction for the corrupted A25 problem.
 *
 * Higher-confidence external evidence from an SHL Automata Pro code replay shows a sibling question
 * with the same business theme, but with one crucial detail that is missing from the OCR text: all
 * straight-line routes start from a base location, and every pickup point is covered by exactly one
 * route if it lies on that same ray/line through the base.
 *
 * Reconstructed question
 *
 * A transportation company has begun service in a new city. The company has a base location where
 * it parks all its vehicles. It has identified some pickup locations where the vehicles will
 * collect passengers. The company wants to identify the minimum number of straight-line routes from
 * the base location to the pickup locations, ensuring that all pickup locations are covered.
 *
 * Input
 *
 * The first line contains an integer N, representing the number of pickup locations.
 * The next N lines contain two space-separated integers x and y, representing one pickup location.
 * The last two inputs are baseX and baseY, representing the base location.
 *
 * Output
 *
 * Print an integer representing the minimum number of straight-line routes needed from the base to
 * cover all pickup locations.
 *
 * 备注
 *
 * 难度：中等。
 *
 * 考点：几何、方向归一化、gcd。
 * 校对：这不是当前题库 A25 的直接定稿，而是基于更高可信外部证据补出的“候选原题版本”。
 * 提示：如果两个 pickup 点和 base 共线且在同一方向上，它们可以由同一条 route 覆盖。
 */
public class MinimumStraightLineRoutesFromBaseToPickupLocations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pickupLocationCount = scanner.nextInt();
        int[][] pickupLocations = new int[pickupLocationCount][2];
        for (int i = 0; i < pickupLocationCount; i++) {
            pickupLocations[i][0] = scanner.nextInt();
            pickupLocations[i][1] = scanner.nextInt();
        }
        int baseX = scanner.nextInt();
        int baseY = scanner.nextInt();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int pickupLocationCount = 4;
         * int[][] pickupLocations = {{1, 1}, {2, 2}, {1, 0}, {2, 0}};
         * int baseX = 0;
         * int baseY = 0;
         */

        MinimumStraightLineRoutesFromBaseToPickupLocations solver =
                new MinimumStraightLineRoutesFromBaseToPickupLocations();
        System.out.println(solver.minimumRoutes(pickupLocations, baseX, baseY));
    }

    public int minimumRoutes(int[][] pickupLocations, int baseX, int baseY) {
        Set<Direction> directions = new HashSet<>();
        for (int[] point : pickupLocations) {
            int dx = point[0] - baseX;
            int dy = point[1] - baseY;
            if (dx == 0 && dy == 0) {
                continue;
            }
            int g = gcd(Math.abs(dx), Math.abs(dy));
            directions.add(new Direction(dx / g, dy / g));
        }
        return directions.size();
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int next = a % b;
            a = b;
            b = next;
        }
        return a;
    }

    private static final class Direction {
        private final int dx;
        private final int dy;

        private Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Direction)) {
                return false;
            }
            Direction that = (Direction) other;
            return dx == that.dx && dy == that.dy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(dx, dy);
        }
    }
}
