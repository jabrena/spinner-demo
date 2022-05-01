package info.jab.demos;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The example uses an Executor Service
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
 */
public class SpinnerDemo {

    public void behaviour(Integer timeout, Boolean exception) {
        System.out.println("Starting SpinnerDemo ExecutorService");
        System.out.println("Timeout: " + timeout);
        System.out.println("Exception flag: " + exception);
        System.out.println();

        var spinner = new Spinner();

        try {
            var executorService = Executors.newScheduledThreadPool(1);
            var result = executorService.submit(new LongProcess(timeout, exception));
            executorService.schedule(
                () -> {
                    result.cancel(true);
                },
                3,
                TimeUnit.SECONDS
            );
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);

            System.out.println(String.format("Result: %s", result));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        spinner.stop();

        System.out.println("Finished demo");
    }

    public static void main(String[] args) {
        SpinnerDemo spinnerDemo = new SpinnerDemo();
        spinnerDemo.behaviour(2000, false);
    }
}
