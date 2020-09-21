package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;
    private final int D = 3;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    private void swim(int k) {
        for (; 1 < k && compare(((k - 1) / D) + 1, k) < 0; k = ((k - 1) / D) + 1)
            Collections.swap(keys, ((k - 1) / D) + 1, k);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, c = 2; c <= size(); k = c, c = (k - 1) * D + 2) {
            for (int i = (k - 1) * D + 3; i <= size(); i++)
                if (compare(c, i) < 0) c = i;
            if (compare(k, c) >= 0) break;
            Collections.swap(keys, k, c);
        }
    }

    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }
}
