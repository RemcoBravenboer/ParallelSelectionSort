import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParallelSelectionSort {
    private final static int CORES = Runtime.getRuntime().availableProcessors();
    private final static int NUMBERS = 10000;
    private static List<Integer> lowestList = new ArrayList<>();
    private static List<Integer> lowestPos = new ArrayList<>();
    private static List<Integer> sortedList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = Numbers.generateNumber(NUMBERS);
        System.out.println("-----------------------------------");
        System.out.println("Starting numbers: " + Arrays.toString(numbers));

        for (int i = 0; i < NUMBERS; i++) {
            System.out.println("------ Iteration " + i + " ------");
            System.out.println("New numbers: " + Arrays.toString(numbers));
            int[][] splitArray = fillSplitArray(CORES, numbers);
            lowestList.clear();
            lowestPos.clear();

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

            splitArray = swap(splitArray, lowestIndex, lowestPos.get(lowestIndex));

            numbers = regenerateNumbers(splitArray);
            sortedList.add(numbers[0]);
            numbers = removeFirst(numbers);
        }

        System.out.println("Sorted list: " + Arrays.toString(sortedList.toArray()));
    }

    private static int[] removeFirst(int[] numbers) {
        int[] temp = new int[numbers.length - 1];
        for (int i = 1; i < numbers.length; i++) {
            temp[(i - 1)] = numbers[i];
        }

        return temp;
    }

    private static int[] regenerateNumbers(int[][] splitArray) {
        List<Integer> listNumbers = new ArrayList<Integer>();

        for (int i = 0; i < splitArray.length; i++) {
            if(splitArray[i] != null) {
                    for (int j = 0; j < splitArray[i].length; j++) {
                        listNumbers.add(splitArray[i][j]);
                    }

            }
        }

        int[] arr = new int[listNumbers.size()];
        for (int i = 0; i < listNumbers.size(); i++)
            arr[i] = listNumbers.get(i);


        return arr;
    }

    private static int[][] swap(int[][] splitArray, int lowestIndex, Integer lowestPos) {
        //System.out.println("Swapping " + splitArray[0][0] + " with " + splitArray[lowestIndex][lowestPos]);
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
