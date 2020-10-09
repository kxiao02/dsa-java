
package edu.emory.cs.sort.hybrid;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T>{

    @SuppressWarnings("unchecked")
    // Merge method similar to that of mergesort
    // This is where most of the time are consumed!!!!
    // !!! PERFORM BETTER !!! if optimized for redundant comparisons
    // eg. insert 7 or 6 into ascending array of 1~6 would be a time waste
    // Also it would be better if merging it self is optimized
    // by comparing the first and last element of each array
    // But I'll just chill
    private T[] merge (Type arrayType, T[] a1, T[] a2) {
        int i = 0, j = 0, k = 0;
        int size1 = a1.length;
        int size2 = a2.length;
        T[] bigArray = (T[]) Array.newInstance((Class<?>) arrayType, size1 + size2);

        while (i < size1 && j < size2) {
            if (!(a1[i].compareTo(a2[j]) > 0)) bigArray[k++] = a1[i++];
            else bigArray[k++] = a2[j++];
        }

        while (i < size1) bigArray[k++] = a1[i++];

        while (j < size2) bigArray[k++] = a2[j++];

        return bigArray;
    }

    // Reverse the consecutive descending elements
    private void reverse (T[] array, int start, int end){
        T tmp;
        end--;

        while (start < end) {
            tmp = array[start];
            array[start++] = array[end];
            array[end--] = tmp;
        }
    }

    // check if the array is ascending.
    private void makeAscend (T[] array, int size){
        int i;
//        boolean sorted = true;

        // Check if the third element is larger than the first
        // It would be a waste of time to check and reverse every reversed pairs
        for (int j = 2; j < size; j++) {
            if (array[j].compareTo(array[j - 2]) < 0) {
//                sorted = false;
                i = j - 2;
                j++;

                while (j < size && array[j].compareTo(array[j - 1]) < 0) {
                    j++;
                }

                reverse(array, i, j);
            }
        }

//        return sorted;
    }

    @Override
    public T[] sort (T[][] array2D) {
        int rowNum = array2D.length;
        int arraySize = array2D[0].length;
        Type arrayType = array2D[0][0].getClass();

        for (T[] array: array2D) {
            // Can be optimized if the arrays already in ascending order don't get sorted
            // Would perform better if take arrays in complete descending order into account

            // Binary insertion sort perform better than intro in case of almost ascending arrays
//            engine.sort(array);
//            if (makeAscend(array, arraySize)) continue;
            makeAscend(array, arraySize);
            binarySort(array, arraySize);
        }

        // Merge to the first array
        for (int size = rowNum; size >= 2; size /= 2) {
            int i = 0;
            int j = 1;

            for (int index = 0; index < size / 2; index++) {
                array2D[index] = merge(arrayType, array2D[i], array2D[j]);
                i += 2;
                j += 2;
            }
            // Merge the remaining array into the first array
            if (size % 2 == 1) array2D[0] = merge(arrayType, array2D[0], array2D[size - 1]);
        }
        return array2D[0];
    }

    // Binary sort
    private void binarySort (T[] array, int size) {
        int i = 1;

        // Find the pivot of the array
        while (i < size) {
            T p = array[i];
            int left = 0;
            int right = i;

            while (left < right) {
                int mid = (left + right) / 2;
                if (p.compareTo(array[mid]) < 0) right = mid;
                else left = mid + 1;
            }
        // rearrange the elements
            int j = i - left;

            if (j == 2) {
                array[left + 2] = array[left + 1];
                array[left + 1] = array[left];
            }
            else if (j == 1) array[left + 1] = array[left];
            else System.arraycopy(array, left, array, left + 1, j);
            array[left] = p;
            i++;
        }
    }



//    static void testRobustness(Integer[][] input, HybridSort<Integer> mine) {
//        Integer[] auto = mine.sort(input);
//        System.out.println(Arrays.toString(auto));
//    }
//
//    public static void main(String[] args) {
//        Integer[][] array = new Integer[][]{
////                {0, 1, 2, 3},
////                {7, 6, 5, 4},
////                {0, 3, 1, 2},
////                {4, 7, 6, 5},
////                {9, 8, 11, 10}
//                  {-1823967115, -190904610, 128836290, 1157708394, 1300780505, 1322734038, 1484326119, 1488100804, 1591958137, 1618246270},
//                  {-1838579681, -1647676149, -1303587417, -1245976854, -982534427, -923865621, 108349607, 1157531605, 1185927641, 1621319800},
//                  {-1285964880, -827532438, -88240257, 325804921, 476167658, 524771771, 1207877156, 1547934812, 1828318856, 1872273213},
//                  {-2076916246, -1987205722, -1494206933, -546836069, -449383339, 404551180, 780559336, 821386148, 1014792067, 1629782154},
//                  {-1760556738, -1153117089, -191458346, -32873341, 411271973, 413614240, 687747542, 1093804072, 1110998816, 1614544898},
//                  {-1964171920, -1635764993, -1365167258, -1203539671, -1184599566, -804100111, 640366639, 824412400, 1738471551, 1922774200},
//                  {-1718609655, -1250658213, -853293277, -89924956, 63339639, 314927607, 1123232583, 1163602465, 1170900964, 2097531950},
//                  {-2105543406, -1556676648, -1383112296, -1109399637, 53236135, 157792382, 827612549, 1657040946, 1809993567, 1974874286},
//                  {-1837621497, -1375132203, -1277152130, -1011654739, -727328998, -543267839, 417027476, 612013980, 1333180676, 1623297243},
//                  {-1649593461, -1590540109, -1464189432, -815550933, -707754698, 109594640, 850943122, 1127816042, 1275456738, 2023025642}
//        };
//        HybridSort<Integer> mine = new HybridSortHW<>();
//        testRobustness(array, mine);
//    }
}
