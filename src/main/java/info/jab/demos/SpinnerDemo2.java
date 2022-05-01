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

    public void behaviour(Integer timeout, Boolean exception) throws InterruptedException {
        System.out.println("Starting demo");
        System.out.println("Timeout: " + timeout);
        System.out.println("Exception flagd: " + exception);
        System.out.println();

        var spinner = new Spinner();

        var executorService = Executors.newSingleThreadExecutor();
        Callable<String> callable = Executors.callable(new LongProcess(timeout, exception), "");
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
            .handle((input, ex) -> {
                if (Objects.isNull(ex)) {
                    return input.toString();
                } else {
                    return ex.getMessage();
                }
            });

        try {
            String result = cf2.get(3, TimeUnit.SECONDS);
            System.out.println(String.format("Result: %s", result));
        } catch (TimeoutException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        spinner.stop();

        System.out.println("Finished demo");
    }
}
