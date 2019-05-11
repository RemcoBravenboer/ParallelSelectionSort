import java.util.Arrays;
import java.util.Random;

public class ParallelSelectionSort {
    private final static int CORES = 2;
    private final static int NUMBERS = 3;
    private static int x = 0;

    public static void main(String[] args) {
        int[] numbers = Numbers.generateNumber(NUMBERS);
        System.out.println("Starting numbers: " + Arrays.toString(numbers));

        for (int i = 0; i < NUMBERS; i++) {
            System.out.print("\nNumbers (iteration " + i +"): ");
            for (int j = i; j < NUMBERS; j++) {
                //Use j for numbers index
                System.out.print(numbers[j] + " ");
            }
        }

        for (int i = 0; i < CORES; i++) {
            Thread t = new Thread(new ParallelSelectionSortRunnable(numbers));
            t.start();
        }
    }
}
