package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EagerPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public EagerPriorityQueue() {
        this(Comparator.naturalOrder());
    }

    public EagerPriorityQueue(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void add(T key) {
        int index = Collections.binarySearch(keys, key, priority);
        if (index < 0) index = -(index + 1);
        keys.add(index, key);
    }

    @Override
    public T remove() {
        return isEmpty() ? null : keys.remove(keys.size() - 1);
    }
}
