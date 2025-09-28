package com.algorithms1.bench;

import com.algorithms1.algorithms.MergeSort;
import com.algorithms1.algorithms.QuickSort;
import com.algorithms1.algorithms.DeterministicSelect;
import com.algorithms1.metrics.Metrics;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(1)
@State(Scope.Thread)
public class SelectVsSortBenchmark {

    @Param({"1000", "10000"}) // размеры массивов
    public int size;

    private int[] array;
    private Random rnd = new Random(42);

    @Setup(Level.Invocation)
    public void setup() {
        array = rnd.ints(size, -1_000_000, 1_000_000).toArray();
    }

    @Benchmark
    public int quickSortBenchmark() {
        int[] copy = array.clone();
        Metrics.reset();
        new QuickSort().sort(copy);
        return copy[0];
    }

    @Benchmark
    public int mergeSortBenchmark() {
        int[] copy = array.clone();
        Metrics.reset();
        new MergeSort().sort(copy);
        return copy[0];
    }

    @Benchmark
    public int deterministicSelectBenchmark() {
        int[] copy = array.clone();
        Metrics.reset();
        return DeterministicSelect.select(copy, copy.length / 2);
    }
}
