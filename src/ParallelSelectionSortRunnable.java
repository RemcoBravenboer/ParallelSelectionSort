import java.util.Arrays;

public class ParallelSelectionSortRunnable implements Runnable {

    private int[] numbers;

    public ParallelSelectionSortRunnable(int[] numbers) {
        this.numbers = numbers;
    }

    public void run() {
        try {
            System.out.print("\nThread " + Thread.currentThread().getId()
                    + " is running with data: " + Arrays.toString(this.numbers));
        } catch (Exception e) {
            System.out.println("The following went wrong: " + e);
        }
    }
}
