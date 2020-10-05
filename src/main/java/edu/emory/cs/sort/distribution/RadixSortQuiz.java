
package edu.emory.cs.sort.distribution;

import java.util.Deque;

public class RadixSortQuiz extends RadixSort {

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex) - 1;
        sortMSD(array, beginIndex, endIndex, maxBit);
    }

    protected void sortMSD(Integer[] array, int beginIndex, int endIndex, int digit) {
        if (digit < 0) return;
        int div = (int) Math.pow(10, digit);
        int index = beginIndex;
        int bIndex = 0;
        int[] bSizes = new int[10];

        for (int i = beginIndex; i < endIndex; i++) {
            buckets.get((array[i] % (div * 10)) / div).add(array[i]);
        }

        for (Deque<Integer> bucket : buckets) {
            bSizes[bIndex++] = bucket.size();
            while (!bucket.isEmpty())
                array[beginIndex++] = bucket.remove();
        }

        for (int bSize : bSizes) {
            if (index >= endIndex) break;
            if (bSize > 1) sortMSD(array, index, index + bSize, digit - 1);
            index += bSize;
        }
    }
}
