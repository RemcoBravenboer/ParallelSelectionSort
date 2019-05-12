import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ParallelSelectionSortRunnable implements Runnable {

    private int[] numbers;
    private volatile int lowest;

    public ParallelSelectionSortRunnable(int[] numbers) {
        this.numbers = numbers;
    }

    public void run() {
        try {
            int lowest = getLowestValue(this.numbers);

            System.out.println("\nThread " + Thread.currentThread().getId()
                    + " is running with data: " + Arrays.toString(this.numbers));
            System.out.println("The lowest number in thread "
                    + Thread.currentThread().getId() + ": " + lowest);


        } catch (Exception e) {
            System.out.println("The following went wrong: " + e);
        }
    }

    public int getLowest() {
        return lowest;
    }

    private int getLowestValue(int[] numbers) {
        lowest = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < lowest) {
                lowest = numbers[i];
            }
        }
        return lowest;
    }

}
