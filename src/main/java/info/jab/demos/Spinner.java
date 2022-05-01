package info.jab.demos;

class Spinner implements Runnable {

    private Boolean flag = false;
    private String lastLine = "";
    private Thread t1;

    public Spinner() {
        t1 = new Thread(this);
        t1.start();
    }

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
            //
        }
    }

    public void stop() {
        this.flag = true;
    }

    @Override
    public void run() {
        while (true) {
            if (this.flag == true) {
                break;
            }
            this.animate();
            sleep(400);
        }
    }
}
