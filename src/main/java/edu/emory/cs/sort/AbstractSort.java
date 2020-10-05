
package edu.emory.cs.sort;

import java.util.Comparator;

public abstract class AbstractSort<T extends Comparable<T>> {
    private final Comparator<T> comparator;
    protected long comparisons;
    protected long assignments;

    public AbstractSort(Comparator<T> comparator) {
        this.comparator = comparator;
        resetCounts();
    }

    public long getComparisonCount() {
        return comparisons;
    }

    public long getAssignmentCount() {
        return assignments;
    }

    public void resetCounts() {
        comparisons = assignments = 0;
    }

    protected int compareTo(T[] array, int i, int j) {
        comparisons++;
        return comparator.compare(array[i], array[j]);
    }

    protected void assign(T[] array, int index, T value) {
        assignments++;
        array[index] = value;
    }

    protected void swap(T[] array, int i, int j) {
        T t = array[i];
        assign(array, i, array[j]);
        assign(array, j, t);
    }

    public void sort(T[] array) {
        sort(array, 0, array.length);
    }

    abstract public void sort(T[] array, int beginIndex, int endIndex);
}
