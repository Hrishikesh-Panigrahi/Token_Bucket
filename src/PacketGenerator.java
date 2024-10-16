package src;

public class PacketGenerator extends Thread {
    private final Bucket bucket;
    private boolean running = true;

    public PacketGenerator(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (running) {
            int packetSize = 1 + (int) (Math.random() * 5); // Random packet size between 1-5
            bucket.sendPacket(packetSize);
            try {
                Thread.sleep(1000 + (int) (Math.random() * 2000)); // Random interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopRunning() {
        running = false;
    }
}
