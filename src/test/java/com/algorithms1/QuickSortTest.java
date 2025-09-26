package com.algorithms1;
import com.algorithms1.algorithms.QuickSort;
import com.algorithms1.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    void testRandomCorrectness() {
        Random rnd = new Random(123);
        QuickSort sorter = new QuickSort();
        for (int t = 0; t < 20; t++) { // 20 разных массивов
            int[] arr = rnd.ints(1000, 0, 10000).toArray();
            int[] copy = arr.clone();

            Metrics.reset();
            sorter.sort(arr);

            Arrays.sort(copy);
            assertArrayEquals(copy, arr, "Mismatch on trial " + t);
        }
    }

    @Test
    void testAdversarialArrays() {
        QuickSort sorter = new QuickSort();

        // sorted array
        int[] sorted = new int[1000];
        for (int i = 0; i < sorted.length; i++) sorted[i] = i;
        int[] copy1 = sorted.clone();
        Metrics.reset();
        sorter.sort(sorted);
        Arrays.sort(copy1);
        assertArrayEquals(copy1, sorted);

        // reverse sorted array
        int[] reversed = new int[1000];
        for (int i = 0; i < reversed.length; i++) reversed[i] = reversed.length - i;
        int[] copy2 = reversed.clone();
        Metrics.reset();
        sorter.sort(reversed);
        Arrays.sort(copy2);
        assertArrayEquals(copy2, reversed);

        // duplicates
        int[] duplicates = new int[1000];
        Arrays.fill(duplicates, 7);
        int[] copy3 = duplicates.clone();
        Metrics.reset();
        sorter.sort(duplicates);
        Arrays.sort(copy3);
        assertArrayEquals(copy3, duplicates);

    }
    @Test
    void testDepthIsBounded() {
        int n = 1024;
        QuickSort sorter = new QuickSort();
        Random rnd = new Random(42);

        for (int t = 0; t < 50; t++) {
            int[] arr = rnd.ints(n, 0, 10000).toArray();
            Metrics.reset();
            sorter.sort(arr);
            int depth = Metrics.getMaxDepth();

            int allowed = 2 * (int) Math.floor(Math.log(n) / Math.log(2)) + 20; // запас
            assertTrue(depth <= allowed,
                    String.format("Depth %d exceeded allowed %d on trial %d", depth, allowed, t));
        }
    }
}
