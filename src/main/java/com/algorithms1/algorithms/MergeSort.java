package com.algorithms1.algorithms;
import com.algorithms1.metrics.Metrics;

/**
 * MergeSort implementation with:
 *  - reusable buffer
 *  - cutoff to InsertionSort for small arrays
 *  - integrated Metrics (comparisons, allocations, recursion depth)
 */
public class MergeSort {
    private static final int CUTOFF = 16; // threshold for insertion sort

    public void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] aux = new int[a.length]; // reusable buffer
        sortRec(a, aux, 0, a.length - 1);
    }

    private void sortRec(int[] a, int[] aux, int left, int right) {
        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right);
            return;
        }

        Metrics.enterRecursion();

        int mid = left + (right - left) / 2;
        sortRec(a, aux, left, mid);
        sortRec(a, aux, mid + 1, right);
        merge(a, aux, left, mid, right);

        Metrics.exitRecursion();
    }

    private void merge(int[] a, int[] aux, int left, int mid, int right) {
        // copy into aux (counts as allocations)
        for (int i = left; i <= right; i++) {
            aux[i] = a[i];
            Metrics.incAllocations();
        }

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > right) a[k] = aux[i++];
            else {
                Metrics.incComparisons();
                if (aux[i] <= aux[j]) a[k] = aux[i++];
                else a[k] = aux[j++];
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
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }
}
