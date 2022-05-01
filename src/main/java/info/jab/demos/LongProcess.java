package info.jab.demos;

class LongProcess implements Runnable {

    private void sleep(Integer milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sleep(6000);
        System.out.println("Long Process Performed");

        throw new RuntimeException("Katakroker");
    }
}
