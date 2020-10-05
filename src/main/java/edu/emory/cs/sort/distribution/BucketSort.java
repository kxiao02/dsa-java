
package edu.emory.cs.sort.distribution;

import edu.emory.cs.sort.AbstractSort;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BucketSort<T extends Comparable<T>> extends AbstractSort<T> {
    protected List<Deque<T>> buckets;

    public BucketSort(int numBuckets) {
        super(null);
        buckets = Stream.generate(ArrayDeque<T>::new).limit(numBuckets).collect(Collectors.toList());
    }

    protected void sort(T[] array, int beginIndex, int endIndex, Function<T, Integer> bucketIndex) {
        // add each element in the input array to the corresponding bucket
        for (int i = beginIndex; i < endIndex; i++)
            buckets.get(bucketIndex.apply(array[i])).add(array[i]);

        // merge elements in all buckets to the input array
        for (Deque<T> bucket : buckets) {
            while (!bucket.isEmpty())
                array[beginIndex++] = bucket.remove();
        }
    }
}
