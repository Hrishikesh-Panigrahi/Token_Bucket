package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimationPanel extends JPanel {
    private int bucketSize;
    private int currentTokens = 0;
    private List<Packet> packets = new ArrayList<>();
    private JTextArea logArea = new JTextArea(8, 30);

    public AnimationPanel(int bucketSize) {
        this.bucketSize = bucketSize;
        this.setBackground(Color.BLACK);
        logArea.setEditable(false);
        logArea.setBackground(Color.DARK_GRAY);
        logArea.setForeground(Color.WHITE);
    }

    public JTextArea getLogArea() {
        return logArea;
    }

    // Add token (visualized as a blue circle)
    public void addToken() {
        if (currentTokens < bucketSize) {
            currentTokens++;
            repaint();
        }
    }

    // Remove token when a packet passes
    public void removeToken() {
        if (currentTokens > 0) {
            currentTokens--;
            repaint();
        }
    }

    // Add packet (visualized as a green square)
    public void addPacket() {
        packets.add(new Packet(0, 50));
        repaint();
    }

    // Move packets from left to right
    public void movePackets() {
        for (Packet packet : packets) {
            packet.move();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the bucket
        g.setColor(Color.BLUE);
        g.fillRect(250, 150, 100, 200); // Bucket

        // Draw tokens inside the bucket
        g.setColor(Color.CYAN);
        for (int i = 0; i < currentTokens; i++) {
            g.fillOval(270, 330 - (i * 20), 20, 20); // Tokens as small circles
        }

        // Draw packets moving from left to bucket
        for (Packet packet : packets) {
            packet.draw(g);
        }
    }
}
