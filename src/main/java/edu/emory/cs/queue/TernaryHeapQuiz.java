package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;
//  private final int D = 3; // Support D-ary heap

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

    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    //(k + D - 1) / D

    private void swim(int k) {
        for (; 1 < k && compare((k + 2) / 3, k) < 0; k = (k + 2) / 3)
            Collections.swap(keys, (k + 2) / 3, k);
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
        for (int k = 1, c = 2; c <= size(); k = c, c = 3 * c - 1){
            if (c <= size() - 2) {
                if (compare(c, c + 1) < 0) {
                    if (compare(c + 1, c + 2) < 0) c++;
                    c++;
                }
                else if (compare(c, c + 2) < 0) c += 2;
            }
            else if (c <= size() - 1) {
                if (compare(c, c + 1) < 0) c++;
            }
            if (compare(k, c) >= 0) break;
            Collections.swap(keys, k, c);
        }
    }

    private void sink2(){
        for (int k = 0, i = 1; i <= size()-1; k = i, i = i * 3 + 1) {
            i = findMax_idx(i);
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }

    private int findMax_idx(int i) {
        if (i < size()-1 && compare(i, i + 1) < 0) {
            i++;
            if (i < size()-1 && compare(i, i + 1) < 0) {
                i++;
            }
        } else if (i < size()-2 && compare(i, i + 2) < 0){
            i = i + 2;
        }
        return i;
    }

   // private void sink() {
   //     int i, k, c;
   //     int size = size();
   //     for (k = 1, c = 2; c <= size; k = c, c = 3 * c - 1) {
   //         for (i = 1; i < 3 && c + i <= size; i++)
   //             if (compare(c, c + i) < 0) {
   //                 c += i;
   //                 i = 0;
   //             }
   //         if (compare(k, c) >= 0) break;
   //         Collections.swap(keys, k, c);
   //     }
   // }
}

