package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.util.DummyData;

import javax.swing.*;
import java.awt.*;

/**
 * Home screen shown after an alumnus logs in.
 * Same layout pattern as StudentDashboard: a sidebar for navigation and
 * a CardLayout content area on the right.
 */
public class AlumniDashboard extends JFrame {

    private static final String[] MENU_ITEMS = {
            "Dashboard", "My Profile", "My Network", "Mentorship", "Jobs", "Messages", "Logout"
    };

    private final Alumni alumni;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel();

    public AlumniDashboard(Alumni alumni) {
        this.alumni = alumni;

        setTitle("AlumNet - Alumni Dashboard");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SidebarPanel sidebar = new SidebarPanel("ALUMNET", "Alumni Portal", MENU_ITEMS, this::handleMenuSelection);

        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(UITheme.LIGHT_GREY);
        contentPanel.add(buildHomeView(), "Dashboard");
        for (String item : MENU_ITEMS) {
            if (!item.equals("Dashboard") && !item.equals("Logout")) {
                contentPanel.add(new FeaturePanel(alumni, item), item);
            }
        }

        JPanel root = new JPanel(new BorderLayout());
        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);
    }

    private void handleMenuSelection(String item) {
        if (item.equals("Logout")) {
            new LoginFrame().setVisible(true);
            this.dispose();
            return;
        }
        cardLayout.show(contentPanel, item);
    }

    // Builds the "Dashboard" panel: a welcome message, a row of stat
    // cards, and a list of students who are currently looking for a mentor.
    private JScrollPane buildHomeView() {
        JPanel home = new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBackground(UITheme.LIGHT_GREY);
        home.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));

        JLabel welcome = new JLabel("Welcome back, " + alumni.getName().split(" ")[0]);
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.NAVY);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel role = new JLabel(alumni.getJobRole() + " at " + alumni.getCompany()
                + " | Class of " + alumni.getGraduationYear());
        role.setFont(UITheme.FONT_LABEL);
        role.setForeground(Color.GRAY);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsRow.add(new StatCard("Total Connections", "8", UITheme.INDIGO));
        statsRow.add(new StatCard("Students Mentored", "3", UITheme.ELECTRIC_BLUE));
        statsRow.add(new StatCard("Jobs Posted", "2", UITheme.WARNING_ORANGE));

        JLabel lookingLabel = sectionLabel("Students Looking for Mentors");
        JPanel lookingList = new JPanel();
        lookingList.setLayout(new BoxLayout(lookingList, BoxLayout.Y_AXIS));
        lookingList.setOpaque(false);
        lookingList.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (Student s : DummyData.STUDENTS) {
            lookingList.add(buildStudentRow(s));
            lookingList.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        home.add(welcome);
        home.add(Box.createRigidArea(new Dimension(0, 4)));
        home.add(role);
        home.add(Box.createRigidArea(new Dimension(0, 22)));
        home.add(statsRow);
        home.add(Box.createRigidArea(new Dimension(0, 26)));
        home.add(lookingLabel);
        home.add(Box.createRigidArea(new Dimension(0, 10)));
        home.add(lookingList);

        JScrollPane scrollPane = new JScrollPane(home);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel buildStudentRow(Student s) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(UITheme.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        JLabel info = new JLabel(s.getName() + " | " + s.getBranch() + " | Goal: " + s.getCareerGoal());
        info.setFont(UITheme.FONT_LABEL);
        info.setForeground(UITheme.TEXT_DARK);

        JButton acceptButton = new JButton("Offer Mentorship");
        acceptButton.setFont(UITheme.FONT_LABEL);
        acceptButton.setFocusPainted(false);
        acceptButton.setBackground(UITheme.ELECTRIC_BLUE);
        acceptButton.setForeground(UITheme.WHITE);
        acceptButton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        row.add(info, BorderLayout.CENTER);
        row.add(acceptButton, BorderLayout.EAST);
        return row;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_HEADING);
        label.setForeground(UITheme.NAVY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}
