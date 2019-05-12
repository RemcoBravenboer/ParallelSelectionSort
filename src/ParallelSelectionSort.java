import java.util.Arrays;

public class ParallelSelectionSort {
    private final static int CORES = 2;
    private final static int NUMBERS = 3;
    private static int arrayToUse[];
    private static int splitArray[][];

    public static void main(String[] args) throws InterruptedException {
        int[] numbers = Numbers.generateNumber(NUMBERS);
        System.out.println("Starting numbers: " + Arrays.toString(numbers));

        for (int i = 0; i < NUMBERS; i++) {
            arrayToUse = Arrays.copyOfRange(numbers, i, numbers.length);
            splitArray = fillSplitArray(CORES, arrayToUse);
            System.out.println(Arrays.deepToString(splitArray));

            for (int j = 0; j < CORES; j++) {
                if(splitArray[j] != null) {
                    ParallelSelectionSortRunnable pssr = new ParallelSelectionSortRunnable(splitArray[j]);
                    Thread t = new Thread(pssr);
                    t.start();
                    t.join();
                    System.out.println("Lowest printed from main: " + pssr.getLowest());
                }
            }
        }
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
