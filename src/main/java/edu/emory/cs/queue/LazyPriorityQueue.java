package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LazyPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public LazyPriorityQueue() {
        this(Comparator.naturalOrder());
    }

    public LazyPriorityQueue(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void add(T key) {
        keys.add(key);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        T max = Collections.max(keys, priority);
        keys.remove(max);
        return max;
    }
}
