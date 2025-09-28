package com.algorithms1;
import com.algorithms1.algorithms.ClosestPair;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {

    @Test
    void testTwoPoints() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4)
        };
        assertEquals(5.0, ClosestPair.findClosest(pts));
    }

    @Test
    void testMultiplePoints() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(5, 6),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(7, 8)
        };
        assertEquals(2.828, ClosestPair.findClosest(pts), 0.001);
    }

    @Test
    void testAdversarialPoints() {
        ClosestPair.Point[] pts = new ClosestPair.Point[1000];
        for (int i = 0; i < 1000; i++) {
            pts[i] = new ClosestPair.Point(i, i);
        }
        assertEquals(Math.sqrt(2), ClosestPair.findClosest(pts), 1e-9);
    }

    @Test
    void testValidateAgainstBruteForceSmallN() {
        int n = 1000; // ≤ 2000
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(Math.random() * 10000, Math.random() * 10000);
        }

        double fast = ClosestPair.findClosest(pts);
        double slow = ClosestPair.bruteForceGlobal(pts);

        assertEquals(slow, fast, 1e-9);
    }

    @Test
    void testLargeNOnlyFast() {
        int n = 50_000; // > 2000
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(Math.random() * 1_000_000, Math.random() * 1_000_000);
        }

        double result = ClosestPair.findClosest(pts);
        assertTrue(result >= 0); // просто проверка, что работает
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> ClosestPair.findClosest(new ClosestPair.Point[]{}));
    }
}
