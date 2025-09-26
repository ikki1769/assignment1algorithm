package com.algorithms1;
import com.algorithms1.algorithms.MergeSort;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class MergeSortTest {

    @Test
    void testEmptyArray() {
        int[] arr = {};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testRandomArray() {
        int[] arr = {7, 2, 9, 1, 5};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{1, 2, 5, 7, 9}, arr);
    }

    @Test
    void testLargeRandomArray() {
        int[] arr = new java.util.Random(42).ints(1000, 0, 10000).toArray();
        int[] copy = arr.clone();

        new MergeSort().sort(arr);
        Arrays.sort(copy);

        assertArrayEquals(copy, arr, "Large random array should be sorted");
    }

    @Test
    void testWithDuplicates() {
        int[] arr = {3, 1, 3, 1, 3, 1};
        new MergeSort().sort(arr);
        assertArrayEquals(new int[]{1, 1, 1, 3, 3, 3}, arr);
    }
}
