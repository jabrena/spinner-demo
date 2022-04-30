package info.jab.demos;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import jdk.incubator.concurrent.StructuredTaskScope;

/**
 * The example uses Structural Concurrency
 * https://download.java.net/java/early_access/loom/docs/api/jdk.incubator.concurrent/jdk/incubator/concurrent/StructuredTaskScope.html
 */
public class SpinnerDemo3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Starting demo");

        try (var scope = new StructuredTaskScope<>()) {
            var future1 = scope.fork(new Spinner());
            var future2 = scope.fork(new LongProcess());
            try {
                scope.joinUntil(Instant.now().plus(Duration.ofSeconds(3)));
            } catch (TimeoutException e) {
                scope.shutdown();
            }
            var end = System.currentTimeMillis();

            System.out.println(String.format("Result: %s", future2.get()));
        }

        System.out.println("Finished demo");
    }
}
