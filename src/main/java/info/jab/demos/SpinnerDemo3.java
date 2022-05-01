package info.jab.demos;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * The example uses Structural Concurrency
 * https://download.java.net/java/early_access/loom/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/StructuredTaskScope.html
 */
public class SpinnerDemo3 {

    public void behaviour(Integer timeout, Boolean exception) {
        System.out.println("Starting SpinnerDemo3 Structural Concurrency");
        System.out.println("Timeout: " + timeout);
        System.out.println("Exception flag: " + exception);
        System.out.println();

        var spinner = new Spinner();

        try (var scope = new StructuredTaskScope<>()) {
            Callable<String> callable = Executors.callable(new LongProcess(timeout, exception), "");
            var future1 = scope.fork(callable);
            try {
                scope.joinUntil(Instant.now().plus(Duration.ofSeconds(3)));

                System.out.println(String.format("Result: %s", future1.get()));
            } catch (TimeoutException | CancellationException | InterruptedException | ExecutionException e) {
                scope.shutdown();
                e.printStackTrace();
            }
        }

        spinner.stop();

        System.out.println("Finished demo");
    }

    public static void main(String[] args) {
        SpinnerDemo3 spinnerDemo3 = new SpinnerDemo3();
        spinnerDemo3.behaviour(2000, false);
    }
}
