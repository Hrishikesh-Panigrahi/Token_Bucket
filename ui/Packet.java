package ui;

import java.awt.*;

public class Packet {
    private int x;
    private int y;
    private static final int SIZE = 20;

    public Packet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Move the packet towards the right (simulating movement)
    public void move() {
        x += 5;
    }

    // Draw the packet as a green square
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, SIZE, SIZE);
    }
}
