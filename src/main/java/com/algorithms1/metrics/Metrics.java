package com.algorithms1.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Metrics class for counting comparisons, allocations, and recursion depth.
 * Also writes results directly into a CSV file.
 */
public final class Metrics {
    private static long comparisons = 0;
    private static long allocations = 0;
    private static int maxDepth = 0;

    private static final ThreadLocal<Integer> currentDepth =
            ThreadLocal.withInitial(() -> 0);

    private Metrics() {}

    /** Reset all counters before each run */
    public static void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
        currentDepth.set(0);
    }

    // --- Comparisons ---
    public static void incComparisons() { comparisons++; }
    public static long getComparisons() { return comparisons; }

    // --- Allocations ---
    public static void incAllocations() { allocations++; }
    public static long getAllocations() { return allocations; }

    // --- Recursion depth ---
    public static void enterRecursion() {
        int d = currentDepth.get() + 1;
        currentDepth.set(d);
        if (d > maxDepth) maxDepth = d;
    }

    public static void exitRecursion() {
        int d = currentDepth.get() - 1;
        if (d < 0) d = 0;
        currentDepth.set(d);
    }

    public static int getMaxDepth() {
        return maxDepth; }

    // --- Write metrics into CSV file ---
    public static void writeToCSV(String file, String algorithm,
                                  int n, int trial, long timeMs) {
        boolean exists = new java.io.File(file).exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            if (!exists) {
                pw.println("algorithm,n,trial,time_ms,comparisons,allocations,max_depth");
            }
            pw.printf("%s,%d,%d,%d,%d,%d,%d%n",
                    algorithm, n, trial, timeMs,
                    comparisons, allocations, maxDepth);
        } catch (IOException e) {
            System.err.println("Error writing metrics: " + e.getMessage());
        }
    }
}
