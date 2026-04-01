package com.aquarius.wizard.leetcode.shl.automata;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Question
 *
 * Automata Pro Question Bank(1).docx
 * Question 55
 *
 * An internet service provider company Si-Fi has won a city tender to lay the wire cables for
 * data transmission between N given routes. There is a chance that the two routes may intersect
 * at some point. Because intersecting wires can cause signal distortion, the company decides to
 * place a distortion avoidance device at all such locations. Find the number of devices the
 * company needs to place.
 *
 * Write an algorithm to find the number of distortion avoidance devices the company needs to
 * place.
 *
 * Notes
 *
 * The docx only keeps the statement and does not spell out a standard input format.
 * This learning version uses:
 * 1. routeCount
 * 2. routeCount lines: x1 y1 x2 y2
 *
 * This version treats each route as a line segment and counts unique finite intersection points.
 * It assumes fully overlapping collinear segments do not appear in the input.
 */
public class Q55CountRouteIntersectionDevices {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int routeCount = scanner.nextInt();
        long[][] segments = new long[routeCount][4];
        for (int i = 0; i < routeCount; i++) {
            segments[i][0] = scanner.nextLong();
            segments[i][1] = scanner.nextLong();
            segments[i][2] = scanner.nextLong();
            segments[i][3] = scanner.nextLong();
        }

        Q55CountRouteIntersectionDevices solver = new Q55CountRouteIntersectionDevices();
        System.out.println(solver.countDevices(segments));
    }

    public int countDevices(long[][] segments) {
        Set<String> intersections = new HashSet<>();
        for (int i = 0; i < segments.length; i++) {
            Point a = new Point(segments[i][0], segments[i][1]);
            Point b = new Point(segments[i][2], segments[i][3]);
            for (int j = i + 1; j < segments.length; j++) {
                Point c = new Point(segments[j][0], segments[j][1]);
                Point d = new Point(segments[j][2], segments[j][3]);
                String key = intersectionKey(a, b, c, d);
                if (key != null) {
                    intersections.add(key);
                }
            }
        }
        return intersections.size();
    }

    private String intersectionKey(Point a, Point b, Point c, Point d) {
        long o1 = orientation(a, b, c);
        long o2 = orientation(a, b, d);
        long o3 = orientation(c, d, a);
        long o4 = orientation(c, d, b);

        if (o1 == 0 && onSegment(a, c, b)) {
            return pointKey(c.x, 1L, c.y, 1L);
        }
        if (o2 == 0 && onSegment(a, d, b)) {
            return pointKey(d.x, 1L, d.y, 1L);
        }
        if (o3 == 0 && onSegment(c, a, d)) {
            return pointKey(a.x, 1L, a.y, 1L);
        }
        if (o4 == 0 && onSegment(c, b, d)) {
            return pointKey(b.x, 1L, b.y, 1L);
        }

        if (!intersectsProperly(o1, o2, o3, o4)) {
            return null;
        }

        long rx = b.x - a.x;
        long ry = b.y - a.y;
        long sx = d.x - c.x;
        long sy = d.y - c.y;
        long denominator = cross(rx, ry, sx, sy);
        long tNumerator = cross(c.x - a.x, c.y - a.y, sx, sy);

        long xNumerator = a.x * denominator + rx * tNumerator;
        long yNumerator = a.y * denominator + ry * tNumerator;
        return pointKey(xNumerator, denominator, yNumerator, denominator);
    }

    private boolean intersectsProperly(long o1, long o2, long o3, long o4) {
        return hasOppositeSign(o1, o2) && hasOppositeSign(o3, o4);
    }

    private boolean hasOppositeSign(long a, long b) {
        return (a < 0 && b > 0) || (a > 0 && b < 0);
    }

    private long orientation(Point a, Point b, Point c) {
        return cross(b.x - a.x, b.y - a.y, c.x - a.x, c.y - a.y);
    }

    private long cross(long ax, long ay, long bx, long by) {
        return ax * by - ay * bx;
    }

    private boolean onSegment(Point a, Point p, Point b) {
        return Math.min(a.x, b.x) <= p.x && p.x <= Math.max(a.x, b.x)
            && Math.min(a.y, b.y) <= p.y && p.y <= Math.max(a.y, b.y);
    }

    private String pointKey(long xNumerator, long xDenominator, long yNumerator, long yDenominator) {
        Fraction x = normalize(xNumerator, xDenominator);
        Fraction y = normalize(yNumerator, yDenominator);
        return x.numerator + "/" + x.denominator + "|" + y.numerator + "/" + y.denominator;
    }

    private Fraction normalize(long numerator, long denominator) {
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }
        long gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        return new Fraction(numerator / gcd, denominator / gcd);
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a == 0 ? 1 : a;
    }

    private static class Point {
        private final long x;
        private final long y;

        private Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Fraction {
        private final long numerator;
        private final long denominator;

        private Fraction(long numerator, long denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }
}
