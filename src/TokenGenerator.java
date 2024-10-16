package src;

import java.util.Timer;
import java.util.TimerTask;

public class TokenGenerator extends Thread {
    private final Bucket bucket;
    private final int tokenRate;
    private boolean running = true;

    public TokenGenerator(Bucket bucket, int tokenRate) {
        this.bucket = bucket;
        this.tokenRate = tokenRate;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!running) {
                    timer.cancel();
                } else {
                    bucket.addToken();
                }
            }
        }, 0, tokenRate);
    }

    public void stopRunning() {
        running = false;
    }

}
