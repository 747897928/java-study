package com.aquarius.wizard.leetcode.shl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * Question
 *
 * A student must solve an entire workbook of problems related to finding the area of intersection
 * of two circles. Because the problems are all very similar, the student decides to write a program
 * that can solve all these similar problems.
 *
 * Input
 *
 * The first line of the input consists of three space-separated integers x1, y1 and r1 where x1
 * and y1 represent the x and y coordinates of the center of the first circle and r1 represents the
 * radius of the first circle.
 * The second line of the input consists of three space-separated integers x2, y2 and r2 where x2
 * and y2 represent the x and y coordinates of the center of the second circle and r2 represents
 * the radius of the second circle.
 *
 * Output
 *
 * Print a real number representing the area of intersection of two circles rounded up to 6 decimal
 * places.
 *
 * Constraints
 *
 * 0 < r1, r2 < 10^4
 *
 * 备注
 *
 * 难度：困难。
 *
 * 考点：几何分类讨论、圆相交面积公式。
 * 校对：当前题库没有样例，所以实现时只能依赖标准公式；“rounded up”更像平台常见的“保留 6 位小数”，这里按 HALF_UP 处理。
 * 提示：要先分清三类情况：相离、包含、普通相交。
 *
 * <p>create: 2026-03-28 18:11:29</p>
 * @author zhaoyijie(AquariusGenius)
 */
public class AreaOfIntersectionOfTwoCircles {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double x1 = scanner.nextDouble();
        double y1 = scanner.nextDouble();
        double r1 = scanner.nextDouble();
        double x2 = scanner.nextDouble();
        double y2 = scanner.nextDouble();
        double r2 = scanner.nextDouble();

        /*
         * 本地自测时直接打开这一段，改上面的 Scanner 就行。
         *
         * double x1 = 0.0;
         * double y1 = 0.0;
         * double r1 = 2.0;
         * double x2 = 1.0;
         * double y2 = 0.0;
         * double r2 = 1.0;
         */

        AreaOfIntersectionOfTwoCircles solver = new AreaOfIntersectionOfTwoCircles();
        double area = solver.intersectionArea(x1, y1, r1, x2, y2, r2);
        System.out.println(solver.formatArea(area));
    }

    /**
     * 这题关键不是死背公式，而是先分清几何上的三种情况：
     *
     * 1. 相离
     *    两圆根本没碰到，交集面积 = 0
     *
     * 2. 包含
     *    一个圆完全包在另一个圆里面，交集面积 = 小圆面积
     *
     * 3. 普通相交
     *    这时交集是一个“透镜形”区域
     *    面积 = 两个扇形面积 - 两个三角形面积
     *
     * 代码里的公式：
     *
     * 0.5 * r^2 * (angle - sin(angle))
     *
     * 正是“扇形减三角形”化简后的结果。
     *
     * 所以做这题时正确顺序应该是：
     *
     * - 先看圆心距 distance 和半径关系，判断属于哪一类
     * - 只有普通相交时，才上标准公式
     */
    public double intersectionArea(double x1, double y1, double r1, double x2, double y2, double r2) {
        double distance = Math.hypot(x1 - x2, y1 - y2);

        // 情况 1：相离或外切。
        // 交集面积为 0。
        if (distance >= r1 + r2) {
            return 0.0;
        }

        // 情况 2：包含或内切。
        // 交集就是较小那个圆的完整面积。
        if (distance <= Math.abs(r1 - r2)) {
            double radius = Math.min(r1, r2);
            return Math.PI * radius * radius;
        }

        // 情况 3：普通相交。
        // 先用余弦定理求出两个圆心对应的圆心角。
        double cos1 = clamp((distance * distance + r1 * r1 - r2 * r2) / (2.0 * distance * r1));
        double cos2 = clamp((distance * distance + r2 * r2 - r1 * r1) / (2.0 * distance * r2));
        double angle1 = 2.0 * Math.acos(cos1);
        double angle2 = 2.0 * Math.acos(cos2);

        // 每一部分面积都是“扇形 - 三角形”的透镜片面积。
        double area1 = 0.5 * r1 * r1 * (angle1 - Math.sin(angle1));
        double area2 = 0.5 * r2 * r2 * (angle2 - Math.sin(angle2));
        return area1 + area2;
    }

    public String formatArea(double area) {
        return BigDecimal.valueOf(area).setScale(6, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 浮点误差下，理论上应该在 [-1, 1] 的 cos 值，
     * 可能会轻微越界到 1.0000000002 这类值。
     *
     * 不夹回去的话，acos 可能出 NaN。
     */
    private double clamp(double value) {
        return Math.max(-1.0, Math.min(1.0, value));
    }
}
