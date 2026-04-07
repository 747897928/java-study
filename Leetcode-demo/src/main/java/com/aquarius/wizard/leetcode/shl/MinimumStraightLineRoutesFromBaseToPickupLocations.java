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

    /**
     * 这题表面看像“很多坐标点，要连很多线”，
     * 但真正的关键不是距离，而是“方向”。
     *
     * 一条从 base 出发的直线，能覆盖哪些 pickup 点？
     *
     * 答案是：
     *
     * - 和 base 共线
     * - 并且在同一方向上的所有点
     *
     * 例如 base = (0, 0)：
     *
     * - (1, 1) 和 (2, 2) 可以共用一条路线
     * - (1, 0) 和 (2, 0) 也可以共用一条路线
     *
     * 所以问题被简化成：
     *
     * “从 base 指向各个 pickup 点，会出现多少种不同方向？”
     *
     * 每一种不同方向，就至少需要一条新路线。
     *
     * 怎么判断两个点是不是同一方向？
     *
     * 看向量 (dx, dy) 归一化后的结果是否相同。
     *
     * 例如：
     *
     * - (2, 2) 归一化后是 (1, 1)
     * - (6, 6) 归一化后也是 (1, 1)
     *
     * 所以它们属于同一条路线方向。
     *
     * 这里的归一化做法就是：
     *
     * - 先算 dx, dy
     * - 再除以 gcd(|dx|, |dy|)
     */
    public int minimumRoutes(int[][] pickupLocations, int baseX, int baseY) {
        Set<Direction> directions = new HashSet<>();
        for (int[] point : pickupLocations) {
            int dx = point[0] - baseX;
            int dy = point[1] - baseY;

            // 如果点和 base 重合，它本身不需要额外路线。
            if (dx == 0 && dy == 0) {
                continue;
            }

            // 用 gcd 把方向向量约成最简形式。
            int g = gcd(Math.abs(dx), Math.abs(dy));
            directions.add(new Direction(dx / g, dy / g));
        }

        // 不同最简方向的个数，就是最少路线数。
        return directions.size();
    }

    /**
     * 欧几里得算法求最大公约数。
     * 这里用它来做方向向量约分。
     */
    private int gcd(int a, int b) {
        while (b != 0) {
            int next = a % b;
            a = b;
            b = next;
        }
        return a;
    }

    /**
     * 最简方向向量。
     *
     * 放进 HashSet 之后，
     * 只要两个点归一化后方向一样，就只会保留一个。
     */
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
