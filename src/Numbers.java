import java.util.Random;

public class Numbers {
    private static int randomNumberGenerator() {
        Random rand = new Random();
        return rand.nextInt(100) + 1;
    }

    public static int[] generateNumber(int amount) {
        int[] randomArray = new int[amount];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = randomNumberGenerator();
        }

        return randomArray;
    }

    public static int[] RandomizeArray(int[] array){
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<array.length; i++) {
            int randomPosition = rgen.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }

        return array;
    }
}
