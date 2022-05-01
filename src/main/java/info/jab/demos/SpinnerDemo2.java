package info.jab.demos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The example uses CompletableFuture
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
 */
public class SpinnerDemo2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Starting demo");

        var spinner = new Spinner();

        var executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> new LongProcess().call(), executorService);
        var result = CompletableFuture.anyOf(cf2).join();
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println(String.format("Result: %s", result));

        spinner.stop();

        System.out.println("Finished demo");
    }
}
