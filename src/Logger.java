package src;

public interface Logger {
    void log(String message, String color);
    void visualize(String visualization);
    void displaySummary(int totalTokensAdded, int totalPacketsSent, int totalPacketsDiscarded);
}
