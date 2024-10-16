package src;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Bucket {
    private final Semaphore tokens;
    private final int maxSize;

    public Bucket(int maxSize) {
        this.maxSize = maxSize;
        this.tokens = new Semaphore(0);
    }

    public void log(String message) {
        // Format the current time into HH:mm:ss for readability
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

    public synchronized boolean addToken() {
        if (tokens.availablePermits() < maxSize) {
            tokens.release();
            log("Added a token. Total tokens: " + tokens.availablePermits());
            return true; // Token added
        }
        log("Bucket is full. No token added.");
        return false; // Bucket is full
    }

    public synchronized boolean sendPacket(int size) {
        log("Packet of size " + size + " arrived.");

        if (size > tokens.availablePermits()) {
            log("Packet is non-conformant, discarded.");
            return false; // Not enough tokens, packet discarded
        }
        for (int i = 0; i < size; i++) {
            tokens.acquireUninterruptibly();
        }
        log("Forwarded packet. Remaining tokens: " + tokens.availablePermits());

        visualize();
        return true; // Packet sent
    }

    private void visualize() {
        int currentTokens = tokens.availablePermits();
        StringBuilder visual = new StringBuilder("Bucket: [");

        for (int i = 0; i < maxSize; i++) {
            // ● for filled, ○ for empty
            visual.append(i < currentTokens ? "●" : "○");
        }

        visual.append("] (").append(currentTokens).append("/").append(maxSize).append(" tokens)");
        System.out.println(visual);
        System.out.println();
    }

    public int getTokenCount() {
        return tokens.availablePermits();
    }

    public int getMaxSize() {
        return maxSize;
    }
}
