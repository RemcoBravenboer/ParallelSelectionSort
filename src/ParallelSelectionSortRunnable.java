import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ParallelSelectionSortRunnable implements Runnable {

    private int[] numbers;
    private volatile int lowest;
    private volatile int lowestPos;

    public ParallelSelectionSortRunnable(int[] numbers) {
        this.numbers = numbers;
    }

    public void run() {
        try {
            int lowest = getLowestValue(this.numbers);
        } catch (Exception e) {
            System.out.println("The following went wrong: " + e);
        }
    }

    public int getLowestPos() {
        return lowestPos;
    }

    public int getLowest() {
        return lowest;
    }

    private int getLowestValue(int[] numbers) {
        lowest = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < lowest) {
                lowest = numbers[i];
                this.lowestPos = i;
            }
        }
        return lowest;
    }

}
