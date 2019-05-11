import java.util.Random;

public class Numbers {
    private static int randomNumberGenerator() {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    public static int[] generateNumber(int amount) {
        int[] randomArray = new int[amount];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = randomNumberGenerator();
        }

        return randomArray;
    }
}
