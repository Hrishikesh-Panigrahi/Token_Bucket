package UI;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

class Bucket {
    private final Semaphore tokens;
    private final int maxSize;
    private final JLabel tokenLabel;
    private final JTextArea logArea;

    Bucket(int max, JLabel tokenLabel, JTextArea logArea) {
        this.maxSize = max;
        this.tokens = new Semaphore(0);
        this.tokenLabel = tokenLabel;
        this.logArea = logArea;
        updateTokenLabel();
    }

    void addToken() {
        if (tokens.availablePermits() < maxSize) {
            tokens.release();
            log("Added 1 token.");
        } else {
            log("Bucket is full!");
        }
        updateTokenLabel();
    }

    void sendPacket(int size) {
        log("Incoming packet of size " + size + "...");
        if (size > tokens.availablePermits()) {
            log("Not enough tokens! Packet dropped.");
        } else {
            for (int i = 0; i < size; i++) {
                tokens.acquireUninterruptibly();
            }
            log("Packet sent successfully!");
        }
        updateTokenLabel();
    }

    private void updateTokenLabel() {
        tokenLabel.setText("Tokens: " + tokens.availablePermits() + "/" + maxSize);
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }
}

class TokenGenerator extends Thread {
    private final Bucket bucket;
    private boolean running = true;

    TokenGenerator(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (running) {
            bucket.addToken();
            try {
                Thread.sleep(500);  // Add a token every 0.5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopRunning() {
        running = false;
    }
}

class PacketGenerator extends Thread {
    private final Bucket bucket;
    private boolean running = true;

    PacketGenerator(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000 + (int) (Math.random() * 2000));  // Random interval
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            int packetSize = 1 + (int) (Math.random() * 5);  // Packet size between 1 and 5
            bucket.sendPacket(packetSize);
        }
    }

    public void stopRunning() {
        running = false;
    }
}

public class FancyTokenBucket {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fancy Token Bucket Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Title
        JLabel titleLabel = new JLabel("Token Bucket Simulator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 87, 34));  // Orange

        // Token Label
        JLabel tokenLabel = new JLabel("Tokens: 0/10", SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Log Area
        JTextArea logArea = new JTextArea(8, 30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Activity Log"));

        // Start/Stop Buttons
        JButton startButton = new JButton("Start Simulation");
        JButton stopButton = new JButton("Stop Simulation");

        // Bucket Instance
        Bucket bucket = new Bucket(10, tokenLabel, logArea);
        TokenGenerator tokenGenerator = new TokenGenerator(bucket);
        PacketGenerator packetGenerator = new PacketGenerator(bucket);

        // Button Actions
        startButton.addActionListener(e -> {
            if (!tokenGenerator.isAlive()) {
                tokenGenerator.start();
                packetGenerator.start();
                logArea.append("Simulation started...\n");
            }
        });

        stopButton.addActionListener(e -> {
            tokenGenerator.stopRunning();
            packetGenerator.stopRunning();
            logArea.append("Simulation stopped.\n");
        });

        // Layout Setup
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(titleLabel);
        topPanel.add(tokenLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(logScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the Frame
        frame.setVisible(true);
    }
}
