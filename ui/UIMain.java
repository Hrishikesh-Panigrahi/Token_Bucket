package ui;

import src.Bucket;
import src.PacketGenerator;
import src.TokenGenerator;
import src.UILogger;

import javax.swing.*;
import java.awt.*;

public class UIMain {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java TerminalMain <bucket_size> <token_rate(ms)> <packet_rate(ms)>");
            System.exit(1);
        }

        int bucketSize = Integer.parseInt(args[0]);
        int tokenRate = Integer.parseInt(args[1]);
        int packetRate = Integer.parseInt(args[2]);

        JFrame frame = new JFrame("Token Bucket Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JTextArea logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        UILogger logger = new UILogger(logArea);
        Bucket bucket = new Bucket(10, logger);

        TokenGenerator tokenGen = new TokenGenerator(bucket, 1000);
        PacketGenerator packetGen = new PacketGenerator(bucket, 2000);

        JButton startButton = new JButton("Start Simulation");
        JButton stopButton = new JButton("Stop Simulation");

        startButton.addActionListener(e -> {
            tokenGen.start();
            packetGen.start();
            logger.log("Simulation started...", "GREEN");
        });

        stopButton.addActionListener(e -> {
            tokenGen.stopRunning();
            packetGen.stopRunning();
            bucket.displaySummary(); // Display summary in UI
            logger.log("Simulation stopped.", "RED");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.setLayout(new BorderLayout());
        frame.add(logScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
