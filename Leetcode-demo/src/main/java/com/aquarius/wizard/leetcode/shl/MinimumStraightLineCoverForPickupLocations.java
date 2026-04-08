package com.aquarius.wizard.leetcode.shl;

import java.util.Scanner;
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
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何、状态压缩 DP、点集最少直线覆盖。
 * 校对：这是按 OCR 字面题意写出的学习版定稿。原 OCR 里的 `N <= 10^3` 与精确解模型不匹配，所以这里直接把公开约束改成了学习版可验证规模。
 * 提示：当前主版本按“去重后点数不超过 20”理解。更像同源题的候选重建版见 MinimumStraightLineRoutesFromBaseToPickupLocations。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class MinimumStraightLineCoverForPickupLocations {

    private static final int MAX_REFERENCE_POINTS = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pickupLocationCount = scanner.nextInt();
        int[][] pickupLocations = new int[pickupLocationCount][2];
        /*
         * 这里虽然题面写的是：
         *
         * The next N lines each consist of two space-separated integers x and y
         *
         * 但不表示代码里必须先 nextLine()，再手动 split。
         *
         * Scanner.nextInt() 是按“整数 token”读取的，
         * 它会自动跳过前面的空白字符。
         *
         * 这里的空白字符不只是空格，还包括：
         * 1. 空格
         * 2. Tab
         * 3. 换行
         *
         * 所以对这种“后面跟着一串纯数字”的题，
         * 连续写 nextInt() 往往比 nextLine() + split() 更稳。
         *
         * 原因主要有三个：
         *
         * 1. 不用额外处理上一轮 nextInt() 留下来的换行
         * 2. 不怕一行里出现多个空格或 Tab
         * 3. 就算评测端本质上只保证 token 顺序正确，而不是严格按你理解的分行方式给数据，
         *    nextInt() 也照样能把这 2N 个整数读出来
         *
         * 所以这里的写法虽然看起来不像“按行读”，
         * 但它读到的仍然就是题面里的每一对坐标。
         */
        for (int i = 0; i < pickupLocationCount; i++) {
            pickupLocations[i][0] = scanner.nextInt();
            pickupLocations[i][1] = scanner.nextInt();
        }

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int pickupLocationCount = 8;
         * int[][] pickupLocations = {{1, 4}, {2, 3}, {2, 1}, {3, 2}, {4, 1}, {5, 0}, {4, 3}, {5, 4}};
         */

        MinimumStraightLineCoverForPickupLocations solver = new MinimumStraightLineCoverForPickupLocations();
        System.out.println(solver.minimumRoutes(pickupLocations));
    }

    /**
     * 这题是“点集最少直线覆盖”的精确解版本。
     *
     * 如果点数大，这题会很难；
     * 但当前学习版把规模收成了“去重后点数不超过 20”，
     * 所以可以直接做状态压缩 DP。
     *
     * 思路分三步：
     *
     * 1. 先把所有可能成为“候选直线”的点集 mask 枚举出来
     *    - 单个点也算一条线
     *    - 任取两个点，可以唯一确定一条直线
     *    - 再把所有落在这条直线上的点一起打成一个 mask
     *
     * 2. 对每个点 i，预处理“有哪些 lineMask 可以覆盖到它”
     *
     * 3. 做搜索 / 记忆化：
     *    - 当前已经覆盖 coveredMask
     *    - 找第一个还没覆盖的点 firstUncovered
     *    - 枚举所有能覆盖这个点的 lineMask
     *    - 选最少条数的方案
     *
     * 为什么只看“第一个还没覆盖的点”就够了？
     *
     * 因为任何合法答案都必须覆盖它。
     * 所以与其枚举所有直线，不如只枚举“能盖住这个点的直线”，搜索会小很多。
     */
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
            // 一个单点自己也可以单独作为一条直线覆盖。
            masks.add(1 << i);
        }
        for (int i = 0; i < pointCount; i++) {
            for (int j = i + 1; j < pointCount; j++) {
                int mask = 0;
                // 枚举由 points[i] 和 points[j] 唯一确定的那条直线，
                // 把所有共线点一起收进 mask。
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
                // 预处理：这个点 i 能被哪些直线 mask 覆盖到。
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

    /**
     * search(coveredMask)：
     * 表示“从当前已覆盖状态 coveredMask 出发，最少还要几条线”
     *
     * 记忆化后，每个状态只算一次。
     */
    private int search(int coveredMask, int fullMask, List<Integer>[] optionsByPoint, int[] memo) {
        if (coveredMask == fullMask) {
            return 0;
        }
        if (memo[coveredMask] != -1) {
            return memo[coveredMask];
        }

        // 找第一个没被覆盖到的点。
        int firstUncovered = Integer.numberOfTrailingZeros(fullMask ^ coveredMask);
        int best = Integer.MAX_VALUE;
        for (int lineMask : optionsByPoint[firstUncovered]) {
            // 当前如果选择这条线，就能新增覆盖 lineMask 里的所有点。
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
