package info.jab.demos;

import java.util.concurrent.Callable;

class Spinner implements Callable<String>, Runnable {

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

    private void sleep(Integer milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String action() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            this.animate();
            sleep(400);
        }
        return "";
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
