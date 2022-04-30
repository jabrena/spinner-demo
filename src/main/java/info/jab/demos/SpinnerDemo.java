package info.jab.demos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The example uses an Executor Service
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
 */
public class SpinnerDemo {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting demo");

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(new Spinner());
        taskList.add(new LongProcess());

        var result = executorService.invokeAny(taskList);
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);

        System.out.println(String.format("Result: %s", result));

        System.out.println("Finished demo");
    }
}
