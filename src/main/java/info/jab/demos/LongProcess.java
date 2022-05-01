package info.jab.demos;

class LongProcess implements Runnable {

    final Integer timeout;
    final Boolean exceptionFlag;

    public LongProcess(Integer timeout, Boolean exceptionFlag) {
        this.timeout = timeout;
        this.exceptionFlag = exceptionFlag;
    }

    private void sleep(Integer milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sleep(timeout);
        System.out.println("Long Process Performed");

        if (exceptionFlag) {
            throw new RuntimeException("Katakroker");
        }
    }
}
