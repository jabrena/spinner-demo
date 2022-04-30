package info.jab.demos;

import java.util.concurrent.Callable;

class LongProcess implements Callable<String>, Runnable {

    private String action() {
        sleep(2000);
        System.out.println("Long Process Performed");

        try {
            throw new RuntimeException("Katakroker");
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private void sleep(Integer milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String call() {
        return action();
    }

    @Override
    public void run() {
        action();
    }
}
