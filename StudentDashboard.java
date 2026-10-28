package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.util.DummyData;

import javax.swing.*;
import java.awt.*;

/**
 * Home screen shown after a student logs in.
 * A sidebar on the left drives navigation; a CardLayout on the right
 * switches between the dashboard and working student feature panels.
 */
public class StudentDashboard extends JFrame {

    private static final String[] MENU_ITEMS = {
            "Dashboard", "Find Alumni", "My Network", "Jobs & Internships", "Messages", "My Profile", "Logout"
    };

    private final Student student;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel();

    public StudentDashboard(Student student) {
        this.student = student;

        setTitle("AlumNet - Student Dashboard");
        setSize(1100, 700);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SidebarPanel sidebar = new SidebarPanel("ALUMNET", "Student Portal", MENU_ITEMS, this::handleMenuSelection);

        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(UITheme.LIGHT_GREY);
        contentPanel.add(buildHomeView(), "Dashboard");
        for (String item : MENU_ITEMS) {
            if (!item.equals("Dashboard") && !item.equals("Logout")) {
                contentPanel.add(new FeaturePanel(student, item), item);
            }
        }

        JPanel root = new JPanel(new BorderLayout());
        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);
    }

    // Called by the sidebar whenever a menu button is clicked.
    private void handleMenuSelection(String item) {
        if (item.equals("Logout")) {
            new LoginFrame().setVisible(true);
            this.dispose();
            return;
        }
        cardLayout.show(contentPanel, item);
    }

    // Builds the "Dashboard" panel: a welcome message, a row of stat
    // cards, and a short list of alumni recommended to this student.
    private JScrollPane buildHomeView() {
        JPanel home = new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBackground(UITheme.LIGHT_GREY);
        home.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));

        JLabel welcome = new JLabel("Welcome back, " + student.getName().split(" ")[0]);
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.NAVY);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel goal = new JLabel(student.getBranch() + " | " + student.getYear()
                + " | Goal: " + student.getCareerGoal());
        goal.setFont(UITheme.FONT_LABEL);
        goal.setForeground(Color.GRAY);
        goal.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsRow.add(new StatCard("My Connections", "3", UITheme.INDIGO));
        statsRow.add(new StatCard("Mentors", "1", UITheme.ELECTRIC_BLUE));
        statsRow.add(new StatCard("Saved Jobs", "2", UITheme.WARNING_ORANGE));

        JLabel recAlumniLabel = sectionLabel("Recommended Alumni");
        JPanel recAlumniRow = new JPanel(new GridLayout(1, 3, 14, 0));
        recAlumniRow.setOpaque(false);
        recAlumniRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        recAlumniRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        int count = 0;
        for (Alumni a : DummyData.ALUMNI) {
            if (count >= 3) break;
            recAlumniRow.add(buildMiniAlumniCard(a));
            count++;
        }

        home.add(welcome);
        home.add(Box.createRigidArea(new Dimension(0, 4)));
        home.add(goal);
        home.add(Box.createRigidArea(new Dimension(0, 22)));
        home.add(statsRow);
        home.add(Box.createRigidArea(new Dimension(0, 26)));
        home.add(recAlumniLabel);
        home.add(Box.createRigidArea(new Dimension(0, 10)));
        home.add(recAlumniRow);

        JScrollPane scrollPane = new JScrollPane(home);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel buildMiniAlumniCard(Alumni a) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel name = new JLabel(a.getName());
        name.setFont(UITheme.FONT_BUTTON);
        name.setForeground(UITheme.NAVY);

        JLabel role = new JLabel(a.getJobRole() + " at " + a.getCompany());
        role.setFont(UITheme.FONT_LABEL);
        role.setForeground(Color.GRAY);

        card.add(name);
        card.add(Box.createRigidArea(new Dimension(0, 4)));
        card.add(role);
        return card;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_HEADING);
        label.setForeground(UITheme.NAVY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
}
