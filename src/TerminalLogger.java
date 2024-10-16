package src;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TerminalLogger implements Logger {
    // ANSI color codes for better visibility
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    // Log messages with timestamp and color
    @Override
    public void log(String message, String color) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println(color + "[" + timestamp + "] " + message + RESET);
    }

    // Helper methods for commonly used log colors
    public void logInfo(String message) {
        log(message, GREEN);
    }

    public void logWarning(String message) {
        log(message, YELLOW);
    }

    public void logError(String message) {
        log(message, RED);
    }
    
    @Override
    public void visualize(String visualization) {
        System.out.println(visualization);
    }

    @Override
    public void displaySummary(int totalTokensAdded, int totalPacketsSent, int totalPacketsDiscarded) {
        System.out.println("\n----- Simulation Summary -----");
        System.out.println(YELLOW + "Total Tokens Added: " + totalTokensAdded + RESET);
        System.out.println(YELLOW + "Total Packets Sent: " + totalPacketsSent + RESET);
        System.out.println(RED + "Total Packets Discarded: " + totalPacketsDiscarded + RESET);
        System.out.println("------------------------------");
    }
}
