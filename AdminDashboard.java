package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.model.User;
import alumnet.util.DummyData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Home screen shown after an admin logs in.
 * Includes read-only tables listing every registered student and alumnus,
 * built straight from the DummyData lists.
 */
public class AdminDashboard extends JFrame {

    private static final String[] MENU_ITEMS = {
            "Dashboard", "Students", "Alumni", "Reports", "Settings", "Logout"
    };

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel();
    private final User admin = new User("ADMIN", "Administrator", DummyData.ADMIN_EMAIL, "", "ADMIN");

    public AdminDashboard() {
        setTitle("AlumNet - Admin Dashboard");
        setSize(1150, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        SidebarPanel sidebar = new SidebarPanel("ALUMNET", "Admin Portal", MENU_ITEMS, this::handleMenuSelection);

        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(UITheme.LIGHT_GREY);
        contentPanel.add(buildHomeView(), "Dashboard");
        contentPanel.add(buildStudentsView(), "Students");
        contentPanel.add(buildAlumniView(), "Alumni");
        for (String item : MENU_ITEMS) {
            if (!item.equals("Dashboard") && !item.equals("Students")
                    && !item.equals("Alumni") && !item.equals("Logout")) {
                contentPanel.add(new FeaturePanel(admin, item), item);
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

    private JScrollPane buildHomeView() {
        JPanel home = new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBackground(UITheme.LIGHT_GREY);
        home.setBorder(BorderFactory.createEmptyBorder(30, 36, 30, 36));

        JLabel welcome = new JLabel("Admin Overview");
        welcome.setFont(UITheme.FONT_TITLE);
        welcome.setForeground(UITheme.NAVY);
        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Platform-wide statistics and management tools");
        subtitle.setFont(UITheme.FONT_LABEL);
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        statsRow.add(new StatCard("Total Students", String.valueOf(DummyData.STUDENTS.size()), UITheme.INDIGO));
        statsRow.add(new StatCard("Total Alumni", String.valueOf(DummyData.ALUMNI.size()), UITheme.ELECTRIC_BLUE));
        statsRow.add(new StatCard("Jobs Posted", "6", UITheme.WARNING_ORANGE));

        home.add(welcome);
        home.add(Box.createRigidArea(new Dimension(0, 4)));
        home.add(subtitle);
        home.add(Box.createRigidArea(new Dimension(0, 22)));
        home.add(statsRow);

        JScrollPane scrollPane = new JScrollPane(home);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    // Read-only table of every student currently in DummyData.STUDENTS.
    private JPanel buildStudentsView() {
        String[] columns = {"ID", "Name", "Email", "Enrollment No.", "Branch", "Year", "Career Goal"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (Student s : DummyData.STUDENTS) {
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getEmail(), s.getEnrollmentNumber(),
                    s.getBranch(), s.getYear(), s.getCareerGoal()
            });
        }
        return buildTablePanel("Manage Students", model);
    }

    // Read-only table of every alumnus currently in DummyData.ALUMNI.
    private JPanel buildAlumniView() {
        String[] columns = {"ID", "Name", "Email", "Company", "Job Role", "Grad. Year", "Industry", "Location"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (Alumni a : DummyData.ALUMNI) {
            model.addRow(new Object[]{
                    a.getId(), a.getName(), a.getEmail(), a.getCompany(),
                    a.getJobRole(), a.getGraduationYear(), a.getIndustry(), a.getLocation()
            });
        }
        return buildTablePanel("Manage Alumni", model);
    }

    // Wraps a JTable in a titled, scrollable panel. Reused by both the
    // Students view and the Alumni view above.
    private JPanel buildTablePanel(String title, DefaultTableModel model) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.LIGHT_GREY);
        panel.setBorder(BorderFactory.createEmptyBorder(26, 32, 26, 32));

        JLabel heading = new JLabel(title);
        heading.setFont(UITheme.FONT_TITLE);
        heading.setForeground(UITheme.NAVY);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(UITheme.FONT_LABEL);
        table.getTableHeader().setFont(UITheme.FONT_BUTTON);
        table.getTableHeader().setBackground(UITheme.NAVY);
        table.getTableHeader().setForeground(UITheme.WHITE);
        table.setSelectionBackground(UITheme.ELECTRIC_BLUE);
        table.setSelectionForeground(UITheme.WHITE);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }
}
