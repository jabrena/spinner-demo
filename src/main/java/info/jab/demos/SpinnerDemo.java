package info.jab.demos;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The example uses an Executor Service
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
 */
public class SpinnerDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting demo");

        var spinner = new Spinner();

        var executorService = Executors.newScheduledThreadPool(1);
        var result = executorService.submit((Runnable) new LongProcess());
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

        spinner.stop();

        System.out.println("Finished demo");
    }
}
