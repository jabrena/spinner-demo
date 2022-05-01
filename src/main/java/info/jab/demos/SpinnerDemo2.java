package info.jab.demos;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The example uses CompletableFuture
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
 */
public class SpinnerDemo2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting demo");

        var spinner = new Spinner();

        var executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = Executors.callable(new LongProcess(), "");
        CompletableFuture<String> cf2 = CompletableFuture
            .supplyAsync(
                () -> {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                },
                executorService
            )
            .handle((input, exception) -> {
                if (Objects.isNull(exception)) {
                    return input.toString();
                } else {
                    return exception.getMessage();
                }
            });

        try {
            String result = cf2.get(3, TimeUnit.SECONDS);
            System.out.println(String.format("Result: %s", result));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        spinner.stop();

        System.out.println("Finished demo");
    }
}
