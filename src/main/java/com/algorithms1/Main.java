package com.algorithms1;

import com.algorithms1.algorithms.MergeSort;
import com.algorithms1.algorithms.QuickSort;
import com.algorithms1.algorithms.DeterministicSelect;
import com.algorithms1.algorithms.ClosestPair;
import com.algorithms1.metrics.Metrics;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java -jar app.jar <algorithm|all> <n> <trials> <csvfile>");
            System.out.println("Algorithms: mergesort, quicksort, select, closest, all");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        String csvFile = args[3];

        Random rand = new Random();

        for (int t = 1; t <= trials; t++) {
            switch (algorithm) {
                case "mergesort" -> runMergeSort(n, t, csvFile, rand);
                case "quicksort" -> runQuickSort(n, t, csvFile, rand);
                case "select"    -> runSelect(n, t, csvFile, rand);
                case "closest"   -> runClosest(n, t, csvFile, rand);
                case "all" -> {
                    runMergeSort(n, t, csvFile, rand);
                    runQuickSort(n, t, csvFile, rand);
                    runSelect(n, t, csvFile, rand);
                    runClosest(n, t, csvFile, rand);
                }
                default -> {
                    System.err.println("Unknown algorithm: " + algorithm);
                    return;
                }
            }
        }

        System.out.println("Done. Results written to " + csvFile);
    }

    private static void runMergeSort(int n, int trial, String csvFile, Random rand) {
        Metrics.reset();
        int[] arr = rand.ints(n, 0, 100000).toArray();
        long start = System.nanoTime();
        new MergeSort().sort(arr);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        Metrics.writeToCSV(csvFile, "mergesort", n, trial, elapsedMs);
    }

    private static void runQuickSort(int n, int trial, String csvFile, Random rand) {
        Metrics.reset();
        int[] arr = rand.ints(n, 0, 100000).toArray();
        long start = System.nanoTime();
        new QuickSort().sort(arr);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        Metrics.writeToCSV(csvFile, "quicksort", n, trial, elapsedMs);
    }

    private static void runSelect(int n, int trial, String csvFile, Random rand) {
        Metrics.reset();
        int[] arr = rand.ints(n, 0, 100000).toArray();
        int k = n / 2;
        long start = System.nanoTime();
        DeterministicSelect.select(arr, k);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        Metrics.writeToCSV(csvFile, "select", n, trial, elapsedMs);
    }

    private static void runClosest(int n, int trial, String csvFile, Random rand) {
        Metrics.reset();
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
        }
        long start = System.nanoTime();
        ClosestPair.findClosest(pts);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        Metrics.writeToCSV(csvFile, "closest", n, trial, elapsedMs);
    }
}
