package alumnet.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Small reusable card that shows a single statistic, e.g. "12 Connections".
 */
public class StatCard extends JPanel {

    public StatCard(String label, String value, Color accentColor) {
        setLayout(new BorderLayout());
        setBackground(UITheme.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(UITheme.FONT_STAT_NUMBER);
        valueLabel.setForeground(accentColor);

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(UITheme.FONT_LABEL);
        textLabel.setForeground(UITheme.TEXT_DARK);

        JPanel textWrap = new JPanel();
        textWrap.setOpaque(false);
        textWrap.setLayout(new BoxLayout(textWrap, BoxLayout.Y_AXIS));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textWrap.add(valueLabel);
        textWrap.add(Box.createRigidArea(new Dimension(0, 4)));
        textWrap.add(textLabel);

        add(textWrap, BorderLayout.CENTER);
    }
}
