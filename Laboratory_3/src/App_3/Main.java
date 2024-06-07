package App_3;

public class Main {
    public static void main(String[] args){
        Integer monitor = 0;

        new ExecutionThread(5,monitor,6,3).start();
        new ExecutionThread(3,monitor,7,4).start();
        new ExecutionThread(6,monitor,7,5).start();
    }
}