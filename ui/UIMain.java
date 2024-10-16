package ui;

import src.Bucket;
import src.TokenGenerator;
import src.PacketGenerator;

import javax.swing.*;
import java.awt.*;

public class UIMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Token Bucket Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JLabel tokenLabel = new JLabel("Tokens: 0/10", SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        JButton startButton = new JButton("Start Simulation");
        JButton stopButton = new JButton("Stop Simulation");

        Bucket bucket = new Bucket(10);
        TokenGenerator tokenGen = new TokenGenerator(bucket);
        PacketGenerator packetGen = new PacketGenerator(bucket);

        startButton.addActionListener(e -> {
            if (!tokenGen.isAlive()) tokenGen.start();
            if (!packetGen.isAlive()) packetGen.start();
            logArea.append("Simulation started...\n");
        });

        stopButton.addActionListener(e -> {
            tokenGen.stopRunning();
            packetGen.stopRunning();
            logArea.append("Simulation stopped.\n");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.setLayout(new BorderLayout());
        frame.add(tokenLabel, BorderLayout.NORTH);
        frame.add(logScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
