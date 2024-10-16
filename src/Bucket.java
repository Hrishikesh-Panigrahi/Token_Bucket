package src;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Bucket {
    private final Semaphore tokens;
    private final int maxSize;
    private int totalTokensAdded = 0;
    private int totalPacketsSent = 0;
    private int totalPacketsDiscarded = 0;

    public Bucket(int maxSize) {
        this.maxSize = maxSize;
        this.tokens = new Semaphore(0);
    }

    // Logging method with timestamp and separator
    public void log(String message, String color) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(color + "[" + timestamp + "] " + message + RESET);
    }

    // ANSI color codes for output
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    // Method to add a token to the bucket
    public synchronized boolean addToken() {
        if (tokens.availablePermits() < maxSize) {
            tokens.release();
            totalTokensAdded++;
            log("Added a token. Total tokens: " + tokens.availablePermits(), GREEN);
            visualize();
            return true;
        }
        log("Bucket is full. No token added.", YELLOW);
        return false;
    }

    // Method to send a packet of a given size
    public synchronized boolean sendPacket(int size) {
        log("Packet of size " + size + " arrived.", YELLOW);

        if (size > tokens.availablePermits()) {
            totalPacketsDiscarded++;
            log("Packet is non-conformant, discarded.", RED);
            return false;
        }

        for (int i = 0; i < size; i++) {
            tokens.acquireUninterruptibly();
        }
        totalPacketsSent++;
        log("Forwarded packet. Remaining tokens: " + tokens.availablePermits(), GREEN);
        visualize();
        return true;
    }

    // Method to visualize the bucket
    private void visualize() {
        int currentTokens = tokens.availablePermits();
        StringBuilder visual = new StringBuilder("Bucket: [");

        for (int i = 0; i < maxSize; i++) {
            visual.append(i < currentTokens ? "●" : "○");
        }

        visual.append("] (").append(currentTokens).append("/").append(maxSize).append(" tokens)");
        System.out.println(visual);
        System.out.println();
    }

    // Method to display a summary of the simulation
    public void displaySummary() {
        System.out.println("\n----- Simulation Summary -----");
        System.out.println("Total Tokens Added: " + totalTokensAdded);
        System.out.println("Total Packets Sent: " + totalPacketsSent);
        System.out.println("Total Packets Discarded: " + totalPacketsDiscarded);
        System.out.println("------------------------------");
    }
}
