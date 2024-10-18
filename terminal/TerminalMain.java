package terminal;

import src.Bucket;
import src.TokenGenerator;
import src.PacketGenerator;
import src.TerminalLogger;

public class TerminalMain {
    public static void main(String[] args) {
        int bucketSize = 10;
        int tokenRate = 1000;
        int packetRate = 1000;
        
        if (args.length == 3) {
            bucketSize = Integer.parseInt(args[0]);
            tokenRate = Integer.parseInt(args[1]);
            packetRate = Integer.parseInt(args[2]);
        }

        Bucket bucket = new Bucket(bucketSize, new TerminalLogger());
        TokenGenerator tokenGen = new TokenGenerator(bucket, tokenRate);
        PacketGenerator packetGen = new PacketGenerator(bucket, packetRate);

        // Start the simulation
        tokenGen.start();
        packetGen.start();

        System.out.println("Starting simulation with:");
        System.out.println("Bucket Size: " + bucketSize);
        System.out.println("Token Rate: " + tokenRate + "ms");
        System.out.println("Packet Rate: " + packetRate + "ms");

        // Stop the simulation after 20 seconds
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        tokenGen.stopRunning();
        packetGen.stopRunning();

        // Display summary
        bucket.displaySummary();
        System.out.println("Simulation stopped.");
    }
}
