package App_1;
public class ExecutionThread extends Thread {

    Integer[] monitor;
    int activity_min, activity_max;
    int sleep;

    public ExecutionThread(Integer[] monitor, int sleep, int activity_min, int activity_max) {
        this.monitor = monitor;
        this.sleep = sleep;
        this.activity_min = activity_min;
        this.activity_max = activity_max;
    }
    @Override
    public void run() {
        System.out.println(this.getName() + " - STATE 1");

        System.out.println(this.getName() + " - TRANSITION 1 - 2");
        if (this.getName().equals("Thread-0"))
        {
            synchronized (monitor[0])
            {
                System.out.println(this.getName() + " - STATE 2");
                int k = (int)Math.round( Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }

                try{
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.getName().equals("Thread-1")) {
            synchronized (monitor[0]) {
                synchronized(monitor[1]) {
                    System.out.println(this.getName() + " - STATE 2");
                    int k = (int)Math.round( Math.random() * (activity_max - activity_min) + activity_min);
                    for (int i = 0; i < k * 100000; i++) {
                        i++;
                        i--;
                    }
                    try{
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (this.getName().equals("Thread-2")) {
            synchronized (monitor[1]) {
                System.out.println(this.getName() + " - STATE 2");
                int k = (int)Math.round( Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }

                try{
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(this.getName() + " - TRANSITION 2-3");
        System.out.println(this.getName() + " - STATE 3");
    }
}
