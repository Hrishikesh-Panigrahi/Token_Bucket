import java.util.concurrent.Semaphore;

class Bucket {
    private final Semaphore tokens;
    private final int maxsize;

    Bucket(int max) {
        this.maxsize = max;
        this.tokens = new Semaphore(0); 
    }

    void addToken() {
        // Check if there is space to add a token
        if (tokens.availablePermits() < maxsize) {
            tokens.release();
            log("Added a token. Total tokens: " + tokens.availablePermits());
        } else {
            log("Bucket is full. No token added.");
        }
    }

    void sendPacket(int n) {
        log("Packet of size " + n + " arrived.");
        // Check if there are enough tokens
        if (n > tokens.availablePermits()) {
            log("Packet is non-conformant, discarded.");
        } else {
            for (int i = 0; i < n; i++) {
                tokens.acquireUninterruptibly();
            }
            log("Forwarded packet. Remaining tokens: " + tokens.availablePermits());
        }
        visualize();
    }

    private void log(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] " + message);
    }

    private void visualize() {
        int currentTokens = tokens.availablePermits();
        StringBuilder visual = new StringBuilder("Bucket: [");
        for (int i = 0; i < maxsize; i++) {
            visual.append(i < currentTokens ? "●" : "○");
        }
        visual.append("] (").append(currentTokens).append("/").append(maxsize).append(" tokens)");
        System.out.println(visual.toString());
        System.out.println(); 
    }
}

class AddTokenThread extends Thread {
    private final Bucket bucket;

    AddTokenThread(Bucket b) {
        this.bucket = b;
    }

    public void run() {
        while (true) {
            bucket.addToken();
            try {
                Thread.sleep(300); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class AddPacketThread extends Thread {
    private final Bucket bucket;

    AddPacketThread(Bucket b) {
        this.bucket = b;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500 + (int) (Math.random() * 3000)); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            int packetSize = 1 + (int) (Math.random() * 9); 
            bucket.sendPacket(packetSize);
        }
    }
}

public class TokenBucket {
    public static void main(String[] args) {
        Bucket b = new Bucket(10); 
        Thread tokens = new AddTokenThread(b);
        Thread packets = new AddPacketThread(b);

        tokens.start();
        packets.start();

        // Optionally, add a way to stop the threads gracefully if desired
    }
}
