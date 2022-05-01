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

    public void behaviour(Integer timeout, Boolean exception) {
        System.out.println("Starting SpinnerDemo2 CompletableFuture");
        System.out.println("Timeout: " + timeout);
        System.out.println("Exception flag: " + exception);
        System.out.println();

        var spinner = new Spinner();

        try {
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

            String result = cf2.get(3, TimeUnit.SECONDS);
            System.out.println(String.format("Result: %s", result));

            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        spinner.stop();

        System.out.println("Finished demo");
    }

    public static void main(String[] args) {
        SpinnerDemo2 spinnerDemo2 = new SpinnerDemo2();
        spinnerDemo2.behaviour(2000, false);
    }
}
