package src;

import java.util.Timer;
import java.util.TimerTask;

public class PacketGenerator extends Thread {
    private final Bucket bucket;
    private final int packetRate;
    private boolean running = true;

    public PacketGenerator(Bucket bucket, int packetRate) {
        this.bucket = bucket;
        this.packetRate = packetRate;
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
                    int packetSize = 1 + (int) (Math.random() * 3); // Random packet size between 1-3
                    bucket.sendPacket(packetSize);
                }
            }
        }, 0, packetRate);
    }

    public void stopRunning() {
        running = false;
    }
}
