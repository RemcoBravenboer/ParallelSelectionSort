import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ParallelSelectionSortRunnable implements Runnable {

    private int[] numbers;
    private int lowestNumberInArray;

    public ParallelSelectionSortRunnable(int[] numbers) {
        this.numbers = numbers;
    }

    public void run() {
        try {
            int lowest = getLowestValue(this.numbers);
            System.out.println("The lowest number in this array is " + lowest);

            System.out.print("\nThread " + Thread.currentThread().getId()
                    + " is running with data: " + Arrays.toString(this.numbers));


        } catch (Exception e) {
            System.out.println("The following went wrong: " + e);
        }
    }

    private int getLowestValue(int[] numbers) {
        lowestNumberInArray = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < lowestNumberInArray) {
                lowestNumberInArray = numbers[i];
            }
        }
        return lowestNumberInArray;
    }

}
