package edu.emory.cs.queue;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<T extends Comparable<T>> {
    protected final Comparator<T> priority;

    public AbstractPriorityQueue(Comparator<T> priority) {
        this.priority = priority;
    }

    abstract public void add(T key);

    abstract public T remove();

    abstract public int size();

    public boolean isEmpty() {
        return size() == 0;
    }
}


