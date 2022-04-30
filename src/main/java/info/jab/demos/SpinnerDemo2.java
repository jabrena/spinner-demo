package info.jab.demos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The example uses CompletableFuture
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html
 */
public class SpinnerDemo2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Starting demo");

        ExecutorService pool = Executors.newFixedThreadPool(2);

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(
            () -> {
                Spinner spinner = new Spinner();
                return spinner.call();
            },
            pool
        );
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(
            () -> {
                LongProcess longProcess = new LongProcess();
                return longProcess.call();
            },
            pool
        );
        var list = CompletableFuture.anyOf(cf1, cf2);
        var result = list.join();

        System.out.println(String.format("Result: %s", result));

        System.out.println("Finished demo");
        System.exit(0);
    }
}
