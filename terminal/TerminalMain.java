package terminal;

import src.Bucket;
import src.TokenGenerator;
import src.PacketGenerator;

public class TerminalMain {
    public static void main(String[] args) {
        Bucket bucket = new Bucket(10);
        TokenGenerator tokenGen = new TokenGenerator(bucket);
        PacketGenerator packetGen = new PacketGenerator(bucket);

        // Start the simulation
        tokenGen.start();
        packetGen.start();

        // Stop the simulation after 20 seconds
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        tokenGen.stopRunning();
        packetGen.stopRunning();
        System.out.println("Simulation stopped.");
    }
}
