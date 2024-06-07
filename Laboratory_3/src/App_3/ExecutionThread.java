package App_3;

public class ExecutionThread extends Thread {

    final Integer monitor;
    int activitymax, activitymin;
    int sleep;

    public ExecutionThread(int sleep, Integer monitor, int activitymax, int activitymin) {
        this.monitor = monitor;
        this.activitymax = activitymax;
        this.activitymin = activitymin;
        this.sleep = sleep;
    }

    public void run() {

        while(true) {


            System.out.println(this.getName() + " - STATE 1");
            System.out.println(this.getName() + " - TRANSITION 1-2");
            synchronized (monitor) {
                System.out.println(this.getName() + " - STATE 2");
                int k = (int) Math.round(Math.random() * (activitymax - activitymin) + activitymax);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
            }

            System.out.println(this.getName() + " - STATE 3");

            try {
                Thread.sleep(sleep);
            } catch (Exception e) {
                // exception handling
            }

            System.out.println(this.getName() + " - STATE 4");

        }
    }

}
