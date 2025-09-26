package com.algorithms1.algorithms;

import com.algorithms1.metrics.Metrics;

import java.util.Random;

public class QuickSort {
    private static final int CUTOFF = 16; // use insertion sort for small partitions
    private final Random rnd = new Random();

    public QuickSort() {}

    public QuickSort(long seed) {
        rnd.setSeed(seed);
    }

    public void sort(int[] a) {
        if (a == null || a.length < 2) return;
        sortRec(a, 0, a.length - 1);
    }

    // recursive on smaller part, loop on larger part
    private void sortRec(int[] a, int left, int right) {
        while (left < right) {
            int size = right - left + 1;
            if (size <= CUTOFF) {
                insertionSort(a, left, right);
                return;
            }

            // choose randomized pivot and partition (Lomuto)
            int pivotIndex = left + rnd.nextInt(size);
            swap(a, pivotIndex, right); // move pivot to end
            int p = partition(a, left, right); // p is final pivot position

            // decide which side is smaller and recurse on it
            int leftSize = p - 1 - left + 1; // = p - left
            int rightSize = right - (p + 1) + 1; // = right - p

            if (leftSize < rightSize) {
                // recurse on left (smaller), iterate on right (larger)
                Metrics.enterRecursion();
                sortRec(a, left, p - 1);
                Metrics.exitRecursion();
                left = p + 1; // tail-iteration on right
            } else {
                Metrics.enterRecursion();
                sortRec(a, p + 1, right);
                Metrics.exitRecursion();
                right = p - 1; // tail-iteration on left
            }
        }
    }

    // Lomuto partition: pivot at right
    private int partition(int[] a, int left, int right) {
        int pivot = a[right];
        int i = left; // place for next smaller-or-equal
        for (int j = left; j < right; j++) {
            Metrics.incComparisons();
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, right);
        return i;
    }

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        Metrics.incAllocations(); // count swap as an "allocation/operation"
    }

    private void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                Metrics.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    Metrics.incAllocations(); // shift
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}
