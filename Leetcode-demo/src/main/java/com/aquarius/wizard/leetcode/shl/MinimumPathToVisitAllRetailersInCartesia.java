package com.aquarius.wizard.leetcode.shl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Question
 *
 * Gregor is a salesperson employed in the city of Cartesia, which is an infinite plane whose
 * locations follow the Cartesian coordinate system. There are N+1 retailers in the city out of
 * which N retailers, with position 1 to N, have the coordinates (X1, 0), (X2, 0) to (Xn, 0). The
 * head retailer, with position N+1, is located at the coordinate (Xn+1, Yn+1).
 *
 * Gregor needs to find a path such that starting from the given Kth retailer, he can visit all the
 * other retailers covering the shortest total distance. Gregor can visit a retailer twice along his
 * route and the distance between any two retailers is the same as the distance between the two
 * points in the Cartesian coordinate system.
 *
 * Write an algorithm to help Gregor to find the minimum distance of the path to visit all the given
 * retailers.
 *
 * Input
 *
 * The first line of the input consists of two space-separated integers N and K, representing the
 * number of retailers on the X-axis and the position of the starting retailer, respectively.
 * The second line consists of N space-separated integers, representing the coordinates of retailers
 * on the X-axis.
 * The third line consists of two space-separated integers Xn+1, Yn+1, representing the coordinates
 * of the head retailer.
 *
 * Output
 *
 * Print a real number representing the minimum possible length of the path after traveling through
 * all the given points, rounded up to 6 decimal places.
 *
 * Constraints
 *
 * 1 <= K <= N + 1
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何、排序、路径构造。
 * 校对：题面没有样例和完整约束，因此这是基于“所有普通零售点都在 x 轴上，只有总部点离轴”这一可见结构推导出的实现。
 * 当前理解：最优路径不会在 x 轴上的点之间来回折返多次；它等价于先按某个方向覆盖整段 x 轴区间，再把唯一的离轴点作为一次插入。
 * 提示：这个实现已经用小规模暴力枚举做过对拍，但仍建议后续继续复核题面。
 */
public class MinimumPathToVisitAllRetailersInCartesia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int retailerCount = scanner.nextInt();
        int startRetailerPosition = scanner.nextInt();
        long[] retailerX = new long[retailerCount];
        for (int i = 0; i < retailerCount; i++) {
            retailerX[i] = scanner.nextLong();
        }
        long headX = scanner.nextLong();
        long headY = scanner.nextLong();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * int retailerCount = 2;
         * int startRetailerPosition = 3;
         * long[] retailerX = {0L, 10L};
         * long headX = 5L;
         * long headY = 4L;
         */

        MinimumPathToVisitAllRetailersInCartesia solver = new MinimumPathToVisitAllRetailersInCartesia();
        double answer = solver.minimumDistance(retailerX, headX, headY, startRetailerPosition);
        System.out.println(solver.formatDistance(answer));
    }

    public double minimumDistance(long[] retailerX, long headX, long headY, int startRetailerPosition) {
        if (retailerX.length == 0) {
            return 0.0;
        }

        long[] sortedX = uniqueSorted(retailerX);
        if (startRetailerPosition == retailerX.length + 1) {
            long left = sortedX[0];
            long right = sortedX[sortedX.length - 1];
            return (right - left) + Math.min(distance(headX, headY, left), distance(headX, headY, right));
        }

        long startX = retailerX[startRetailerPosition - 1];
        int startIndex = findFirstIndex(sortedX, startX);
        double leftThenRight = evaluate(buildLeftThenRight(sortedX, startIndex), headX, headY);
        double rightThenLeft = evaluate(buildRightThenLeft(sortedX, startIndex), headX, headY);
        return Math.min(leftThenRight, rightThenLeft);
    }

    private int findFirstIndex(long[] sortedX, long target) {
        for (int i = 0; i < sortedX.length; i++) {
            if (sortedX[i] == target) {
                return i;
            }
        }
        throw new IllegalArgumentException("Start x-coordinate not found in retailer list.");
    }

    private long[] uniqueSorted(long[] retailerX) {
        long[] copy = Arrays.copyOf(retailerX, retailerX.length);
        Arrays.sort(copy);
        int uniqueCount = 0;
        for (long value : copy) {
            if (uniqueCount == 0 || value != copy[uniqueCount - 1]) {
                copy[uniqueCount++] = value;
            }
        }
        return Arrays.copyOf(copy, uniqueCount);
    }

    private long[] buildLeftThenRight(long[] sortedX, int startIndex) {
        long[] order = new long[sortedX.length];
        int write = 0;
        order[write++] = sortedX[startIndex];
        for (int i = startIndex - 1; i >= 0; i--) {
            order[write++] = sortedX[i];
        }
        for (int i = startIndex + 1; i < sortedX.length; i++) {
            order[write++] = sortedX[i];
        }
        return order;
    }

    private long[] buildRightThenLeft(long[] sortedX, int startIndex) {
        long[] order = new long[sortedX.length];
        int write = 0;
        order[write++] = sortedX[startIndex];
        for (int i = startIndex + 1; i < sortedX.length; i++) {
            order[write++] = sortedX[i];
        }
        for (int i = startIndex - 1; i >= 0; i--) {
            order[write++] = sortedX[i];
        }
        return order;
    }

    private double evaluate(long[] order, long headX, long headY) {
        double baseline = 0.0;
        for (int i = 0; i + 1 < order.length; i++) {
            baseline += Math.abs(order[i + 1] - order[i]);
        }

        double best = baseline + distance(headX, headY, order[order.length - 1]);
        for (int i = 0; i + 1 < order.length; i++) {
            double extra = distance(headX, headY, order[i])
                    + distance(headX, headY, order[i + 1])
                    - Math.abs(order[i + 1] - order[i]);
            best = Math.min(best, baseline + extra);
        }
        return best;
    }

    private double distance(long headX, long headY, long axisX) {
        return Math.hypot(headX - axisX, headY);
    }

    public String formatDistance(double distance) {
        return BigDecimal.valueOf(distance).setScale(6, RoundingMode.HALF_UP).toPlainString();
    }
}
