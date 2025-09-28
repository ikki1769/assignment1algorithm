# Assignment 1 – Divide and Conquer Algorithms

## Overview

This project implements and analyzes classic **Divide and Conquer (D&C) algorithms**, focusing on:

* Safe recursion patterns with bounded stack depth
* Runtime recurrence analysis (Master Theorem & Akra–Bazzi intuition)
* Experimental validation with collected metrics: execution time, recursion depth, comparisons, allocations
* Clean GitHub workflow (branches, commits, CI)

---

## Implemented Algorithms

### 1. MergeSort

* Linear merge using a reusable buffer
* Cut-off to Insertion Sort for small arrays
* Recursion depth tracked via `Metrics`

### 2. QuickSort

* Randomized pivot selection
* Recurse on smaller partition, iterate on larger (stack depth ≈ O(log n))
* Metrics integrated

### 3. Deterministic Select (Median-of-Medians)

* Groups of 5 → median-of-medians pivot
* In-place partition
* Recurse only on one side (prefer smaller side)
* Guarantees linear time complexity

### 4. Closest Pair of Points (2D)

* Sort by x, recursive split
* “Strip” check with neighbors sorted by y (≤ 7–8 scans)
* Brute-force validation for small `n ≤ 2000`
* Fast O(n log n) version for large n

---

## Collected Metrics

For each run, the following metrics are tracked:

* **Execution time** (nanoseconds)
* **Recursion depth**
* **Comparisons**
* **Allocations**

Metrics are written to `metrics.csv` for plotting.

---

## Recurrence Analysis

### MergeSort

* Recurrence: `T(n) = 2T(n/2) + Θ(n)`
* Master Theorem, Case 2 → **Θ(n log n)**

### QuickSort

* Recurrence: `T(n) = T(k) + T(n-k-1) + Θ(n)`
* Akra–Bazzi intuition → **Θ(n log n)** (average case)

### Deterministic Select

* Recurrence: `T(n) = T(n/5) + T(7n/10) + Θ(n)`
* Median-of-Medians method → **Θ(n)**

### Closest Pair

* Recurrence: `T(n) = 2T(n/2) + Θ(n)`
* Divide-and-Conquer → **Θ(n log n)**

---

## Experimental Results

### Example Benchmark Results (ns/op)

| Algorithm            | n=1000          | n=10000           |
| -------------------- | --------------- | ----------------- |
| Deterministic Select | 39,685 ± 27,615 | 394,678 ± 108,756 |
| MergeSort            | 64,873 ± 50,522 | 937,052 ± 105,598 |
| QuickSort            | 56,017 ± 6,712  | 732,632 ± 585,548 |

---

## Architecture Notes

* **Metrics** → comparisons, allocations, recursion depth
* **Reusable buffer** in MergeSort → fewer allocations
* **QuickSort** recurses on smaller partition → bounded depth
* **Deterministic Select** → only one recursive branch → linear
* **Closest Pair** → balanced recursion + strip check

---

## Benchmarks (JMH)

Includes `SelectVsSortBenchmark` harness for MergeSort, QuickSort, and Deterministic Select.

**Run with Maven:**

```bash
mvn clean install
java -jar target/benchmarks.jar
```

Results can be exported as CSV (`results.csv`).

---

## Running the CLI

Build the JAR:

```bash
mvn clean package
```

Run experiments:

```bash
java -jar target/assignment1algor-1.0-SNAPSHOT.jar quicksort 10000 3 metrics.csv
```

This will run QuickSort on arrays of size 10000 for 3 trials and save results to `metrics.csv`.

---

## Summary

* **Theory matches practice**: QuickSort and MergeSort scale as `Θ(n log n)`, Select as `Θ(n)`.
* **Metrics confirm** recursion depth bounds and allocation behavior.
* **Closest Pair** validated against brute-force.
* Constant-factor effects (cache, GC, pivot randomness) explain deviations.
