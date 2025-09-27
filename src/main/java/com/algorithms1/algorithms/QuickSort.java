package com.algorithms1.algorithms;

import com.algorithms1.metrics.Metrics;
import com.algorithms1.utils.SortUtils;

import java.util.Random;

public class QuickSort {
    private static final int CUTOFF = 16;
    private static final Random rnd = new Random();

    public void sort(int[] a) {
        if (a == null || a.length < 2) return;
        Metrics.reset();
        Metrics.enterRecursion();        // считаем корень
        quickSort(a, 0, a.length - 1);
        Metrics.exitRecursion();
    }

    private void quickSort(int[] a, int left, int right) {
        while (left < right) {
            int size = right - left + 1;
            if (size <= CUTOFF) {
                insertionSort(a, left, right);
                return;
            }

            // randomized pivot
            int pivotIndex = left + rnd.nextInt(size);
            SortUtils.swap(a, pivotIndex, right);

            int p = SortUtils.partition(a, left, right);

            int leftSize = p - left;
            int rightSize = right - p;

            if (leftSize < rightSize) {
                Metrics.enterRecursion();
                quickSort(a, left, p - 1);
                Metrics.exitRecursion();
                left = p + 1;
            } else {
                Metrics.enterRecursion();
                quickSort(a, p + 1, right);
                Metrics.exitRecursion();
                right = p - 1;
            }
        }
    }

    private void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                Metrics.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    Metrics.incAllocations();
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}
