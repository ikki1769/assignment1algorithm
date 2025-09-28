package com.algorithms1.algorithms;

import com.algorithms1.metrics.Metrics;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) {
            this.x = x; this.y = y;
        }
    }

    /** Быстрый алгоритм O(n log n) */
    public static double findClosest(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }

        Metrics.reset();

        Point[] sortedByX = points.clone();
        Arrays.sort(sortedByX, Comparator.comparingDouble(p -> p.x));

        return closestRecursive(sortedByX, 0, sortedByX.length - 1);
    }

    private static double closestRecursive(Point[] pts, int left, int right) {
        Metrics.enterRecursion();

        double result;
        if (right - left <= 3) {
            result = bruteForce(pts, left, right);
        } else {
            int mid = (left + right) / 2;
            double leftMin = closestRecursive(pts, left, mid);
            double rightMin = closestRecursive(pts, mid + 1, right);

            Metrics.incComparisons();
            double d = Math.min(leftMin, rightMin);


            Point[] strip = Arrays.stream(pts, left, right + 1)
                    .filter(p -> Math.abs(p.x - pts[mid].x) < d)
                    .sorted(Comparator.comparingDouble(p -> p.y))
                    .toArray(Point[]::new);

            Metrics.incAllocations();

            result = Math.min(d, stripClosest(strip, d));
        }

        Metrics.exitRecursion();
        return result;
    }

    private static double bruteForce(Point[] pts, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                Metrics.incComparisons();
                min = Math.min(min, distance(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, double d) {
        double min = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
                Metrics.incComparisons();
                min = Math.min(min, distance(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double distance(Point a, Point b) {
        return Math.hypot(a.x - b.x, a.y - b.y);
    }

    /** Медленный алгоритм O(n²) */
    public static double bruteForceGlobal(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        }
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Metrics.incComparisons();
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }
}
