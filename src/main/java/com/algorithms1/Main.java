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
            System.out.println("Usage: java -jar app.jar <algorithm> <n> <trials> <csvfile>");
            System.out.println("Algorithms: mergesort, quicksort, select, closest");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        String csvFile = args[3];

        Random rand = new Random();

        for (int t = 1; t <= trials; t++) {
            Metrics.reset();
            long start = System.nanoTime();

            switch (algorithm) {
                case "mergesort" -> {
                    int[] arr = rand.ints(n, 0, 100000).toArray();
                    new MergeSort().sort(arr);
                }
                case "quicksort" -> {
                    int[] arr = rand.ints(n, 0, 100000).toArray();
                    new QuickSort().sort(arr);
                }
                case "select" -> {
                    int[] arr = rand.ints(n, 0, 100000).toArray();
                    int k = n / 2; // ищем медиану
                    DeterministicSelect.select(arr, k);
                }
                case "closest" -> {
                    ClosestPair.Point[] pts = new ClosestPair.Point[n];
                    for (int i = 0; i < n; i++) {
                        pts[i] = new ClosestPair.Point(
                                rand.nextDouble() * 10000,
                                rand.nextDouble() * 10000
                        );
                    }
                    ClosestPair.findClosest(pts);
                }
                default -> {
                    System.err.println(" Unknown algorithm: " + algorithm);
                    return;
                }
            }

            long elapsedNs = System.nanoTime() - start;
            long elapsedMs = elapsedNs / 1_000_000;
            Metrics.writeToCSV(csvFile, algorithm, n, t, elapsedMs);
        }

        System.out.println(" Done. Results written to " + csvFile);
    }
}
