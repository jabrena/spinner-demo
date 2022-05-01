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

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting demo");

        var spinner = new Spinner();

        try (var scope = new StructuredTaskScope<>()) {
            Callable<String> callable = Executors.callable(new LongProcess(), "");
            var future1 = scope.fork(callable);
            try {
                scope.joinUntil(Instant.now().plus(Duration.ofSeconds(3)));

                System.out.println(String.format("Result: %s", future1.get()));
            } catch (TimeoutException e) {
                scope.shutdown();
            } catch (CancellationException e) {
                e.printStackTrace();
            }
        }

        spinner.stop();

        System.out.println("Finished demo");
    }
}
