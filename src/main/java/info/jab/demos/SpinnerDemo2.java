package info.jab.demos;

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

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("Starting demo");

        var spinner = new Spinner();

        var executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> new LongProcess().call(), executorService);
        var result = cf2.get(3, TimeUnit.SECONDS);
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(String.format("Result: %s", result));

        spinner.stop();

        System.out.println("Finished demo");
    }
}
