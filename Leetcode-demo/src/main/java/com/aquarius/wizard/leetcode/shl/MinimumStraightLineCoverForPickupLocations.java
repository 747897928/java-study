package com.aquarius.wizard.leetcode.shl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Question
 *
 * A transportation company has begun service in a new city. They have identified several pickup
 * locations in crowded areas of the city. A straight-line route can cover every pickup location
 * that lies on that same straight line.
 *
 * Write an algorithm to calculate the minimum number of straight-line routes required to cover all
 * the pickup locations.
 *
 * Input
 *
 * The first line of the input consists of an integer N, representing the number of pickup
 * locations.
 * The next N lines each consist of two space-separated integers x and y, representing the
 * coordinates of a pickup location.
 *
 * Output
 *
 * Print an integer representing the minimum number of straight-line routes required to cover all
 * the pickup locations.
 *
 * Constraints
 *
 * 0 <= N <= 20
 * -10^3 <= x, y <= 10^3
 *
 * Note
 *
 * If multiple pickup locations have identical coordinates, they may be treated as one location.
 *
 * Example
 *
 * Input:
 * 8
 * 1 4
 * 2 3
 * 2 1
 * 3 2
 * 4 1
 * 5 0
 * 4 3
 * 5 4
 *
 * Output:
 * 2
 *
 * Explanation:
 * The points (2, 1), (3, 2), (4, 3), (5, 4) lie on one straight line.
 * The points (1, 4), (2, 3), (3, 2), (4, 1), (5, 0) lie on another straight line.
 *
 * 我的备注
 *
 * 难度：困难。
 *
 * 考点：几何、状态压缩 DP、点集最少直线覆盖。
 * 校对：这是按 OCR 字面题意写出的学习版定稿。原 OCR 里的 `N <= 10^3` 与精确解模型不匹配，所以这里直接把公开约束改成了学习版可验证规模。
 * 提示：当前主版本按“去重后点数不超过 20”理解。更像同源题的候选重建版见 MinimumStraightLineRoutesFromBaseToPickupLocations。
 */
public class MinimumStraightLineCoverForPickupLocations {

    private static final int MAX_REFERENCE_POINTS = 20;

    public int minimumRoutes(int[][] pickupLocations) {
        Point[] points = uniquePoints(pickupLocations);
        int pointCount = points.length;
        if (pointCount == 0) {
            return 0;
        }
        if (pointCount == 1) {
            return 1;
        }
        if (pointCount > MAX_REFERENCE_POINTS) {
            throw new IllegalArgumentException(
                    "Reference solver supports at most " + MAX_REFERENCE_POINTS + " unique points."
            );
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] optionsByPoint = new List[pointCount];
        for (int i = 0; i < pointCount; i++) {
            optionsByPoint[i] = new ArrayList<>();
        }

        Set<Integer> masks = new HashSet<>();
        for (int i = 0; i < pointCount; i++) {
            masks.add(1 << i);
        }
        for (int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                int mask = 0;
                for (int k = 0; k < pointCount; k++) {
                    if (areCollinear(points[i], points[j], points[k])) {
                        mask |= 1 << k;
                    }
                }
                masks.add(mask);
            }
        }

        for (int mask : masks) {
            for (int i = 0; i < pointCount; i++) {
                if (((mask >> i) & 1) != 0) {
                    optionsByPoint[i].add(mask);
                }
            }
        }

        int fullMask = (1 << pointCount) - 1;
        int[] memo = new int[1 << pointCount];
        Arrays.fill(memo, -1);
        return search(0, fullMask, optionsByPoint, memo);
    }

    private int search(int coveredMask, int fullMask, List<Integer>[] optionsByPoint, int[] memo) {
        if (coveredMask == fullMask) {
            return 0;
        }
        if (memo[coveredMask] != -1) {
            return memo[coveredMask];
        }

        int firstUncovered = Integer.numberOfTrailingZeros(fullMask ^ coveredMask);
        int best = Integer.MAX_VALUE;
        for (int lineMask : optionsByPoint[firstUncovered]) {
            best = Math.min(best, 1 + search(coveredMask | lineMask, fullMask, optionsByPoint, memo));
        }
        memo[coveredMask] = best;
        return best;
    }

    private Point[] uniquePoints(int[][] pickupLocations) {
        Set<Point> unique = new LinkedHashSet<>();
        for (int[] location : pickupLocations) {
            unique.add(new Point(location[0], location[1]));
        }
        return unique.toArray(new Point[0]);
    }

    private boolean areCollinear(Point first, Point second, Point third) {
        long abx = second.x - first.x;
        long aby = second.y - first.y;
        long acx = third.x - first.x;
        long acy = third.y - first.y;
        return abx * acy - aby * acx == 0L;
    }

    private static final class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Point)) {
                return false;
            }
            Point that = (Point) other;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
