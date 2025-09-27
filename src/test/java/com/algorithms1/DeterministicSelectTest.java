package com.algorithms1;

import com.algorithms1.algorithms.DeterministicSelect;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeterministicSelectTest {

    @Test
    void testRandomTrials() {
        Random rnd = new Random(123);

        for (int trial = 0; trial < 100; trial++) {
            int n = 200 + rnd.nextInt(301); // массивы 200–500
            int[] arr = rnd.ints(n, 0, 1000).toArray();
            int k = rnd.nextInt(n);

            int result = DeterministicSelect.select(arr, k);

            int[] sortedCopy = arr.clone();
            Arrays.sort(sortedCopy);
            int expected = sortedCopy[k];

            assertEquals(expected, result, "Mismatch on trial " + trial);
        }
    }
}