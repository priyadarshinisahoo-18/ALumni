package alumnet;

import alumnet.gui.LoginFrame;

import javax.swing.*;

/**
 * Application entry point.
 * Launches the Login screen inside the Swing event dispatch thread.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fall back to the default cross-platform look and feel.
            }
            new LoginFrame().setVisible(true);
        });
    }
}
