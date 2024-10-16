package src;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UILogger implements Logger {
    private final JTextArea logArea;

    public UILogger(JTextArea logArea) {
        this.logArea = logArea;
    }

    @Override
    public void log(String message, String color) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        SwingUtilities.invokeLater(() -> logArea.append("[" + timestamp + "] " + message + "\n"));
    }
}
