package ui;

import src.Bucket;
import src.PacketGenerator;
import src.TokenGenerator;
import src.UILogger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIMain {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
    
    public static void main(String[] args) {
        int bucketSize = 10;
        int tokenRate = 1000;
        int packetRate = 1500;

        if (args.length == 3) {
            bucketSize = Integer.parseInt(args[0]);
            tokenRate = Integer.parseInt(args[1]);
            packetRate = Integer.parseInt(args[2]);
        }

        JFrame frame = new JFrame("Token Bucket Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        AnimationPanel animationPanel = new AnimationPanel(bucketSize);
        JScrollPane scrollPane = new JScrollPane(animationPanel);

        UILogger logger = new UILogger(animationPanel.getLogArea());
        Bucket bucket = new Bucket(bucketSize, logger);
        TokenGenerator tokenGen = new TokenGenerator(bucket, tokenRate);
        PacketGenerator packetGen = new PacketGenerator(bucket, packetRate);

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
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
