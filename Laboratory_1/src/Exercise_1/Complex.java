package Exercise_1;

public class Complex {
    private int real;
    private int imag;

    public Complex(int real, int imag){
        this.real = real;
        this.imag = imag;
    }
    public void displayNumber(){
        // This method is for test purpose only.
        System.out.println("The complex number is: " + this.real + " + j" + this.imag + ".\n");
    }
    public int getReal(){

        return this.real;
    }
    public int getImag(){

        return this.imag;
    }
}
