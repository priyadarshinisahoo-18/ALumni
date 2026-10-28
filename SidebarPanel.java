package alumnet.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Reusable vertical navigation sidebar used by all three dashboards.
 * Takes a header title/subtitle and a list of menu item names, and
 * tells the given listener which item was clicked.
 */
public class SidebarPanel extends JPanel {

    public interface SidebarListener {
        void onMenuItemSelected(String menuItem);
    }

    private final List<JButton> menuButtons = new ArrayList<>();
    private JButton activeButton;

    public SidebarPanel(String headerTitle, String headerSubtitle, String[] menuItems, SidebarListener listener) {
        setLayout(new BorderLayout());
        setBackground(UITheme.NAVY);
        setPreferredSize(new Dimension(200, 0));

        // --- Header / brand area ---
        JPanel header = new JPanel();
        header.setBackground(UITheme.NAVY);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(24, 20, 24, 20));

        JLabel title = new JLabel(headerTitle);
        title.setFont(UITheme.FONT_HEADING);
        title.setForeground(UITheme.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(headerSubtitle);
        subtitle.setFont(UITheme.FONT_LABEL);
        subtitle.setForeground(UITheme.MID_GREY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(title);
        header.add(Box.createRigidArea(new Dimension(0, 4)));
        header.add(subtitle);

        // --- Menu items ---
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(UITheme.NAVY);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        for (String item : menuItems) {
            JButton button = createMenuButton(item);
            button.addActionListener(e -> {
                setActiveButton(button);
                if (listener != null) {
                    listener.onMenuItemSelected(item);
                }
            });
            menuButtons.add(button);
            menuPanel.add(button);
        }

        if (!menuButtons.isEmpty()) {
            setActiveButton(menuButtons.get(0));
        }

        add(header, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.FONT_SIDEBAR);
        button.setForeground(UITheme.WHITE);
        button.setBackground(UITheme.NAVY);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        return button;
    }

    // Highlights the clicked button and un-highlights the previously active one.
    private void setActiveButton(JButton button) {
        if (activeButton != null) {
            activeButton.setBackground(UITheme.NAVY);
        }
        activeButton = button;
        activeButton.setBackground(UITheme.ELECTRIC_BLUE);
    }
}
