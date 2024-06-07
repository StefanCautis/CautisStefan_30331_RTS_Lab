package App_2;


public class ExecutionThread extends Thread {
    Integer[] monitor;
    int[] activity1,activity2;
    int sleep;

    public ExecutionThread(Integer[] monitor, int sleep, int[] activity1, int[] activity2) {

        this.monitor = monitor;
        this.sleep = sleep;
        this.activity1 = activity1;
        this.activity2=activity2;

    }

    public void run() {
        System.out.println(this.getName() + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activity1[1] - activity1[0]) + activity1[1]);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }
        System.out.println(this.getName() + " - TRANSITION 1 - 2");
        if (this.getName().equals("Thread-0")) {
            synchronized (monitor[0]) {
                System.out.println(this.getName() + " - STATE 2");
                k = (int) Math.round(Math.random() * (activity2[1] - activity2[0]) + activity2[1]);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                System.out.println(this.getName() + " - TRANSITION 2 - 3");
                synchronized (monitor[1]) {
                    System.out.println(this.getName() + " - STATE 3");
                }

                System.out.println(this.getName() + " - TRANSITION 3 - 4");
                try {
                    Thread.sleep(sleep);
                } catch (Exception e) {

                }
            }
        }

        if (this.getName().equals("Thread-1")) {
            synchronized (monitor[1]) {
                System.out.println(this.getName() + " - STATE 2");
                k = (int) Math.round(Math.random() * (activity2[1] - activity2[0]) + activity2[1]);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                System.out.println(this.getName() + " - TRANSITION 2 - 3");

                synchronized (monitor[0]) {
                    System.out.println(this.getName() + " - STATE 3");
                }

                System.out.println(this.getName() + " - TRANSITION 3 - 4");
                try {
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println(this.getName() + " - STATE 4");
    }
}
