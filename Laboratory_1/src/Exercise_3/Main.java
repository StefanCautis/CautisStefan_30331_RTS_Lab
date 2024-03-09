package Exercise_3;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many elements would you like to have in the ArrayList: ");
        int size = scanner.nextInt();

        ArrayList<Integer> array = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            array.add(random.nextInt(888));
        }

        Collections.sort(array); //Sorting the array.

        System.out.println("The sorted array: ");
        for(Integer i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}