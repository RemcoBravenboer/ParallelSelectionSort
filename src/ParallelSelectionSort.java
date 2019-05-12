import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParallelSelectionSort {
    private final static int CORES = 4;
    private final static int NUMBERS = 19;
    private static int arrayToUse[];
    private static int splitArray[][];
    private static List<Integer> lowestList = new ArrayList<>();
    private static List<Integer> lowestPos = new ArrayList<>();
    private static List<Integer> sortedList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = Numbers.generateNumber(NUMBERS);
        numbers = Numbers.RandomizeArray(numbers);
        System.out.println("Starting numbers: " + Arrays.toString(numbers));

        for (int i = 0; i < NUMBERS; i++) {
            arrayToUse = Arrays.copyOfRange(numbers, i, numbers.length);
            splitArray = fillSplitArray(CORES, arrayToUse);
            lowestList.removeAll(lowestList);
            lowestPos.removeAll(lowestPos);
            System.out.println(Arrays.deepToString(splitArray));

            for (int j = 0; j < CORES; j++) {
                if(splitArray[j] != null) {
                    ParallelSelectionSortRunnable pssr = new ParallelSelectionSortRunnable(splitArray[j]);
                    Thread t = new Thread(pssr);
                    t.start();
                    t.join();
                    lowestList.add(pssr.getLowest());
                    lowestPos.add(pssr.getLowestPos());
                }
            }
            int lowestIndex = lowestList.indexOf(Collections.min(lowestList));
            System.out.println("Index of subarray that holds min: " + lowestIndex
                    + " on position: " + lowestPos.get(lowestIndex));


            System.out.println("Lowest values in iteration: " + Arrays.toString(lowestList.toArray()));
            System.out.println("Lowest value in array: " + splitArray[lowestIndex][lowestPos.get(lowestIndex)]);

            splitArray = swap(splitArray, lowestIndex, lowestPos.get(lowestIndex));
            numbers = regenerateNumbers(splitArray);

            System.out.println("---------------------------------");
            //numbers = swapValues(numbers[0], splitArray[lowestIndex][lowestPos.get(lowestIndex)], numbers);

            sortedList.add(numbers[0]);
        }
        System.out.println("Sorted list: " + Arrays.toString(sortedList.toArray()));
    }

    private static int[] regenerateNumbers(int[][] splitArray) {
        int[] numbers = new int[NUMBERS];
        int x = 0;

        for (int i = 0; i < splitArray.length; i++) {
            if(splitArray[i] != null) {
                for (int j = 0; j < splitArray[i].length; j++) {
                    numbers[x] = splitArray[i][j];
                    x++;
                }
            }
        }
        return numbers;
    }

    private static int[][] swap(int[][] splitArray, int lowestIndex, Integer lowestPos) {
        System.out.println("Swapping " + splitArray[0][0] + " with " + splitArray[lowestIndex][lowestPos]);
        int tempNumber = splitArray[lowestIndex][lowestPos];
        splitArray[lowestIndex][lowestPos] = splitArray[0][0];
        splitArray[0][0] = tempNumber;

        return splitArray;
    }

    private static int[][] fillSplitArray(int arrayAmount, int[] arrayToUse) {
        if (arrayToUse.length == 0) {
            return new int[0][0];
        }

        int splitLength = (int) Math.ceil((double) arrayToUse.length / (double) arrayAmount);
        int[][] splits = new int[arrayAmount][];

        int j = 0;
        int k = 0;
        for (int i = 0; i < arrayToUse.length; i++) {
            if (k == splitLength) {
                k = 0;
                j++;
            }
            if (splits[j] == null) {
                int remainingNumbers = arrayToUse.length - i;
                splits[j] = new int[Math.min(remainingNumbers, splitLength)];
            }
            splits[j][k++] = arrayToUse[i];
        }
        return splits;
    }
}
