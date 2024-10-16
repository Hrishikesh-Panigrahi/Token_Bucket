package src;

import java.util.concurrent.Semaphore;

public class Bucket {
    private final Semaphore tokens;
    private final int maxSize;
    private final Logger logger;
    
    private int totalTokensAdded = 0;
    private int totalPacketsSent = 0;
    private int totalPacketsDiscarded = 0;

    public Bucket(int maxSize, Logger logger) {
        this.maxSize = maxSize;
        this.tokens = new Semaphore(0);
        this.logger = logger;
    }

    // ANSI color codes for better visibility
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    public synchronized boolean addToken() {
        if (tokens.availablePermits() < maxSize) {
            tokens.release();
            totalTokensAdded++;
            logger.log("Added a token. Total tokens: " + tokens.availablePermits(), GREEN);
            visualize();
            return true;
        }
        logger.log("Bucket is full. No token added.", YELLOW);
        return false;
    }

    public synchronized boolean sendPacket(int size) {
        logger.log("Packet of size " + size + " arrived.", YELLOW);

        if (size > tokens.availablePermits()) {
            totalPacketsDiscarded++;
            logger.log("Packet is non-conformant, discarded.", RED);
            return false;
        }

        for (int i = 0; i < size; i++) {
            tokens.acquireUninterruptibly();
        }
        totalPacketsSent++;
        logger.log("Packet of size " + size + " sent successfully.", GREEN);
        logger.log("Remaining tokens: " + tokens.availablePermits(), GREEN);
        visualize();
        return true;
    }

    // Visualize the bucket state with alternative Unicode circles
    private void visualize() {
        int currentTokens = tokens.availablePermits();
        StringBuilder visual = new StringBuilder("Bucket: [");

        for (int i = 0; i < maxSize; i++) {
            visual.append(i < currentTokens ? "⬤" : "◯");
        }

        visual.append("] (").append(currentTokens).append("/").append(maxSize).append(" tokens)");
        logger.visualize(visual.toString());
    }

    // Display simulation summary
    public void displaySummary() {
        logger.displaySummary(totalTokensAdded, totalPacketsSent, totalPacketsDiscarded);
    }
}
