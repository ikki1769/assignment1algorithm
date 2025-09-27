package com.algorithms1.algorithms;

import com.algorithms1.metrics.Metrics;
import com.algorithms1.utils.SortUtils;

import java.util.Arrays;

public class DeterministicSelect {

    /** Public API: k-th smallest element (0-based) */
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length)
            throw new IllegalArgumentException("Invalid input");

        Metrics.reset(); // сбросим все счётчики перед запуском
        return selectHelper(a, 0, a.length - 1, k);
    }

    /** Helper: select k-th smallest in a[left..right] */
    private static int selectHelper(int[] a, int left, int right, int k) {
        Metrics.enterRecursion();
        try {
            while (left < right) {
                int pivotIndex = medianOfMedians(a, left, right);
                pivotIndex = partition(a, left, right, pivotIndex);

                if (k == pivotIndex) return a[k];
                else if (k < pivotIndex) right = pivotIndex - 1;
                else left = pivotIndex + 1;
            }
            return a[left];
        } finally {
            Metrics.exitRecursion();
        }
    }

    /** Partition around pivotIndex; returns final pivot position */
    private static int partition(int[] a, int left, int right, int pivotIndex) {
        int pivotValue = a[pivotIndex];
        SortUtils.swap(a, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            Metrics.incComparisons();
            if (a[i] < pivotValue) {
                SortUtils.swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        SortUtils.swap(a, storeIndex, right);
        return storeIndex;
    }

    /** Find pivot using median-of-medians (groups of 5) */
    private static int medianOfMedians(int[] a, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(a, left, right + 1);
            return left + n / 2;
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(a, subLeft, subRight + 1);
            int medianIndex = subLeft + (subRight - subLeft) / 2;
            SortUtils.swap(a, left + i, medianIndex);
        }


        int mediansRight = left + numMedians - 1;
        int mediansK = left + numMedians / 2;
        selectHelper(a, left, mediansRight, mediansK);
        return mediansK;
    }
}