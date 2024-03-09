package Exercise_2;

public class Main {
    public static void main(String[] args) {

        // Using byte since we are using small numbers.
        byte[][] matrixOne = {
                {2,3,1},
                {7,1,6},
                {9,2,4}
        };
        byte[][] matrixTwo = {
                {8,5,3},
                {3,9,2},
                {2,7,3}
        };
        addMatrices(matrixOne, matrixTwo);
        System.out.println();
        multiplyMatrices(matrixOne, matrixTwo);
    }

    public static void addMatrices(byte[][] matrixOne, byte[][] matrixTwo) {
        System.out.println("The sum of the two matrices is:");

        for(int i = 0; i < matrixOne.length; i++) {
            for(int j = 0; j < matrixTwo.length; j++) {
                System.out.print("\t\t" + matrixOne[i][j] + matrixTwo[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void multiplyMatrices(byte[][] matrixOne, byte[][] matrixTwo) {
        System.out.println("The multiplication of the two matrices is:");

        for(int i = 0; i < matrixOne.length; i++) {
            for(int j = 0; j < matrixTwo.length; j++) {
                int sum = 0;
                for(int k = 0; k < matrixOne.length; k++){
                    sum += matrixOne[i][k] * matrixTwo[k][j];
                    if(k == 2) {
                        System.out.print("\t\t" + sum + " ");
                    }
                }
            }
            System.out.println();
        }
    }
}
