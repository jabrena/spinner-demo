package info.jab.demos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

class LongProcess implements Callable<String> {

    @Override
    public String call() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("Long Process Performed");

        try {
            throw new RuntimeException("Katakroker");
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        //return "Done";
    }
}

class Spinner implements Callable<String> {

    private String lastLine = "";

    public void print(String line) {
        if (lastLine.length() > line.length()) {
            String temp = "";
            for (int i = 0; i < lastLine.length(); i++) {
                temp += " ";
            }
            if (temp.length() > 1) {
                System.out.print("\r" + temp);
            }
        }
        System.out.print("\r" + line);
        lastLine = line;
    }

    private byte anim;

    public void animate() {
        switch (anim) {
            case 1:
                print("[ \\ ] ");
                break;
            case 2:
                print("[ | ] ");
                break;
            case 3:
                print("[ / ] ");
                break;
            default:
                anim = 0;
                print("[ - ] ");
        }
        anim++;
    }

    @Override
    public String call() throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            this.animate();
            Thread.sleep(400);
        }
        return "";
    }
}
