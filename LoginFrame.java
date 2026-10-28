package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.util.DummyData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The very first screen the user sees.
 * Left side shows the app brand, right side has the login form.
 * User picks a role (Student / Alumni / Admin), enters email + password,
 * and is routed to the matching dashboard on success.
 */
public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton studentRadio, alumniRadio, adminRadio;

    public LoginFrame() {
        setTitle("AlumNet - Connect. Network. Grow.");
        setSize(850, 520);
        setMinimumSize(new Dimension(700, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(buildBrandPanel(), BorderLayout.WEST);
        root.add(buildFormPanel(), BorderLayout.CENTER);
        setContentPane(root);
    }

    // Left panel: app name, tagline and a short description on a navy background.
    private JPanel buildBrandPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 0));
        panel.setBackground(UITheme.NAVY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        JLabel appName = new JLabel("ALUMNET");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 32));
        appName.setForeground(UITheme.WHITE);
        appName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tagline = new JLabel("Connect. Network. Grow.");
        tagline.setFont(UITheme.FONT_SUBTITLE);
        tagline.setForeground(UITheme.ELECTRIC_BLUE);
        tagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea description = new JTextArea(
                "A single place where students can find mentors, "
                        + "internships and jobs through alumni who have "
                        + "already walked the same path.");
        description.setFont(UITheme.FONT_LABEL);
        description.setForeground(UITheme.MID_GREY);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setOpaque(false);
        description.setEditable(false);
        description.setFocusable(false);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        description.setMaximumSize(new Dimension(240, 150));

        panel.add(appName);
        panel.add(Box.createRigidArea(new Dimension(0, 6)));
        panel.add(tagline);
        panel.add(Box.createRigidArea(new Dimension(0, 24)));
        panel.add(description);

        return panel;
    }

    // Right panel: role choice, email/password fields and action buttons.
    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(UITheme.WHITE);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel heading = new JLabel("Welcome back");
        heading.setFont(UITheme.FONT_TITLE);
        heading.setForeground(UITheme.NAVY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subheading = new JLabel("Login to continue to your dashboard");
        subheading.setFont(UITheme.FONT_LABEL);
        subheading.setForeground(Color.GRAY);
        subheading.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Role selection - exactly one of the three can be selected at a time.
        studentRadio = new JRadioButton("Student", true);
        alumniRadio = new JRadioButton("Alumni");
        adminRadio = new JRadioButton("Admin");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(studentRadio);
        roleGroup.add(alumniRadio);
        roleGroup.add(adminRadio);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        rolePanel.setOpaque(false);
        rolePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        for (JRadioButton rb : new JRadioButton[]{studentRadio, alumniRadio, adminRadio}) {
            rb.setOpaque(false);
            rb.setFont(UITheme.FONT_LABEL);
            rolePanel.add(rb);
        }

        emailField = new JTextField();
        styleField(emailField);

        passwordField = new JPasswordField();
        styleField(passwordField);

        JLabel hint = new JLabel("<html>Demo logins &mdash; Student: priya@nietstudent.in / student123<br>"
                + "Alumni: rahul@alumni.com / alumni123 &nbsp; Admin: admin@alumnet.com / admin123</html>");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton loginButton = primaryButton("Login");
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.addActionListener(this::handleLogin);

        JButton registerButton = secondaryButton("Register");
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.addActionListener((ActionEvent e) -> {
            new RegisterFrame(this).setVisible(true);
            this.setVisible(false);
        });

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonRow.setOpaque(false);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonRow.add(loginButton);
        buttonRow.add(registerButton);

        form.add(heading);
        form.add(Box.createRigidArea(new Dimension(0, 4)));
        form.add(subheading);
        form.add(Box.createRigidArea(new Dimension(0, 24)));
        form.add(sectionLabel("I am a"));
        form.add(rolePanel);
        form.add(Box.createRigidArea(new Dimension(0, 12)));
        form.add(sectionLabel("Email"));
        form.add(emailField);
        form.add(Box.createRigidArea(new Dimension(0, 12)));
        form.add(sectionLabel("Password"));
        form.add(passwordField);
        form.add(Box.createRigidArea(new Dimension(0, 16)));
        form.add(buttonRow);
        form.add(Box.createRigidArea(new Dimension(0, 16)));
        form.add(hint);

        outer.add(form);
        return outer;
    }

    // Checks the entered email/password against DummyData for the selected role.
    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (studentRadio.isSelected()) {
            Student student = DummyData.authenticateStudent(email, password);
            if (student != null) {
                openDashboard(new StudentDashboard(student));
                return;
            }
        } else if (alumniRadio.isSelected()) {
            Alumni alumni = DummyData.authenticateAlumni(email, password);
            if (alumni != null) {
                openDashboard(new AlumniDashboard(alumni));
                return;
            }
        } else if (adminRadio.isSelected()) {
            if (DummyData.authenticateAdmin(email, password)) {
                openDashboard(new AdminDashboard());
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid credentials for the selected role.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    private void openDashboard(JFrame dashboard) {
        dashboard.setVisible(true);
        this.dispose();
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.FONT_LABEL);
        label.setForeground(UITheme.TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // --- Shared styling helpers (also used by RegisterFrame) ---

    static void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.setFont(UITheme.FONT_LABEL);
    }

    static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.FONT_BUTTON);
        button.setBackground(UITheme.ELECTRIC_BLUE);
        button.setForeground(UITheme.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        return button;
    }

    static JButton secondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.FONT_BUTTON);
        button.setBackground(UITheme.WHITE);
        button.setForeground(UITheme.INDIGO);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.INDIGO, 1, true),
                BorderFactory.createEmptyBorder(9, 20, 9, 20)
        ));
        return button;
    }
}
