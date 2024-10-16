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
            int packetSize = 1 + (int) (Math.random() * 5);  // Packet size 1 to 5
            boolean sent = bucket.sendPacket(packetSize);
            if (sent) {
                System.out.println("Packet of size " + packetSize + " sent.");
            } else {
                System.out.println("Packet of size " + packetSize + " discarded.");
            }
            try {
                Thread.sleep(1000 + (int) (Math.random() * 2000));  // Random intervals
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
