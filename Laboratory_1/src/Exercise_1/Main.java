package Exercise_1;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);
        String flag = "Loop";

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        while(!flag.equals("Exit")) {

            if(a == 0 && b == 0 && c == 0 && d == 0) {
                System.out.println("Please choose the real part for the first number: ");
                a = scanner.nextInt();

                System.out.println("Please choose the imaginary part for the first number: ");
                b = scanner.nextInt();

                System.out.println("Please choose the real part for the second number: ");
                c = scanner.nextInt();

                System.out.println("Please choose the imaginary part for the second number: ");
                d = scanner.nextInt();


            }

            Complex numberOne = new Complex(a,b);
            Complex numberTwo = new Complex(c,d);

            System.out.println("\nPlease choose one of the below and type what you want:\n" +
                    "\"Numbers\" to choose some new value for the numbers.\n" +
                    "\"Add\" in order to add the two numbers.\n" +
                    "\"Difference\" in order to differentiate the two numbers.\n" +
                    "\"Multiplication\" in order to multiply the two numbers.\n" +
                    "\"Exit\" in order to exit the menu.");
            flag = scannerString.nextLine();

            int real;
            int imag;

            switch(flag) {
                case "Numbers", "numbers":
                    System.out.println("Please choose the real part for the first number: ");
                    a = scanner.nextInt();

                    System.out.println("Please choose the imaginary part for the first number: ");
                    b = scanner.nextInt();

                    System.out.println("Please choose the real part for the second number: ");
                    c = scanner.nextInt();

                    System.out.println("Please choose the imaginary part for the second number: ");
                    d = scanner.nextInt();
                    break;

                case "Add", "add":
                    real = numberOne.getReal() + numberTwo.getReal();
                    imag = numberOne.getImag() + numberTwo.getImag();
                    System.out.println("\nThe addition of \"a\" and \"b\" is equal to: " + real + " + " + imag + "j\n");
                    break;

                case "Difference", "difference":
                    real = numberOne.getReal() - numberTwo.getReal();
                    imag = numberOne.getImag() - numberTwo.getImag();
                    if(imag > 0) {
                        System.out.println("\nThe difference of \"a\" and \"b\" is equal to: " + real + " + " + imag + "j\n");
                    } else {
                        System.out.println("\nThe difference of \"a\" and \"b\" is equal to: " + real + " - " + Math.abs(imag) + "j\n");
                    }
                    break;

                case "Multiplication", "multiplication":
                    int multiplicationReal = numberOne.getReal() * numberTwo.getReal() - numberOne.getImag() * numberTwo.getImag();
                    int multiplicationImag = numberOne.getReal() * numberTwo.getReal() + numberOne.getReal() * numberTwo.getImag();

                    System.out.println("\nThe multiplication of \"a\" and \"b\" is equal to: " + multiplicationReal + " + " + multiplicationImag + "j\n");
                    break;

                case "Exit", "exit":
                    flag = "Exit";
                    System.out.println("\nGood-bye!");
                    break;

                default:
                    System.out.println("\nYou choose an invalid option.");
                    break;
            }
        }
    }
}