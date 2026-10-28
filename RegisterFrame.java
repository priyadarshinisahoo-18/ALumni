package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.util.DummyData;

import javax.swing.*;
import java.awt.*;

/**
 * Registration screen with two tabs: Student and Alumni.
 * On successful submit, the new user is added to the in-memory DummyData
 * lists (this will later be replaced by an INSERT via JDBC) and the user
 * is sent back to the Login screen.
 */
public class RegisterFrame extends JFrame {

    private final LoginFrame loginFrame;

    // Student fields
    private JTextField stuName, stuEmail, stuEnrollment, stuBranch;
    private JComboBox<String> stuYear, stuCareerGoal;
    private JPasswordField stuPassword, stuConfirmPassword;

    // Alumni fields
    private JTextField alName, alEmail, alGradYear, alCompany, alJobRole;
    private JComboBox<String> alIndustry;
    private JPasswordField alPassword, alConfirmPassword;

    public RegisterFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("AlumNet - Create Account");
        setSize(600, 600);
        setMinimumSize(new Dimension(520, 480));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.WHITE);
        setContentPane(root);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(UITheme.NAVY);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JLabel title = new JLabel("Create your AlumNet account");
        title.setFont(UITheme.FONT_HEADING);
        title.setForeground(UITheme.WHITE);
        JLabel subtitle = new JLabel("Join as a student or as an alumnus");
        subtitle.setFont(UITheme.FONT_LABEL);
        subtitle.setForeground(UITheme.MID_GREY);
        headerPanel.add(title);
        headerPanel.add(subtitle);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(UITheme.FONT_BUTTON);
        tabs.addTab("Student", wrapScroll(buildStudentForm()));
        tabs.addTab("Alumni", wrapScroll(buildAlumniForm()));

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        JButton backToLogin = LoginFrame.secondaryButton("Back to Login");
        backToLogin.addActionListener(e -> goBackToLogin());
        footer.add(backToLogin);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        root.add(footer, BorderLayout.SOUTH);
    }

    private JScrollPane wrapScroll(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JPanel buildStudentForm() {
        JPanel form = new JPanel();
        form.setBackground(UITheme.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        stuName = addField(form, "Full Name");
        stuEmail = addField(form, "Email");
        stuEnrollment = addField(form, "Enrollment Number");
        stuBranch = addField(form, "Branch");
        stuYear = addComboField(form, "Year", new String[]{"1st Year", "2nd Year", "3rd Year", "4th Year"});
        stuCareerGoal = addComboField(form, "Career Goal", new String[]{
                "Software Engineer", "AI/ML Engineer", "Data Scientist", "Web Developer", "Product Manager"});
        stuPassword = addPasswordField(form, "Password");
        stuConfirmPassword = addPasswordField(form, "Confirm Password");

        JButton submit = LoginFrame.primaryButton("Register as Student");
        submit.setAlignmentX(Component.LEFT_ALIGNMENT);
        submit.addActionListener(e -> handleStudentRegister());
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(submit);

        return form;
    }

    private JPanel buildAlumniForm() {
        JPanel form = new JPanel();
        form.setBackground(UITheme.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        alName = addField(form, "Full Name");
        alEmail = addField(form, "Email");
        alGradYear = addField(form, "Graduation Year");
        alCompany = addField(form, "Current Company");
        alJobRole = addField(form, "Job Role");
        alIndustry = addComboField(form, "Industry", new String[]{
                "Technology", "Finance", "Healthcare", "Consulting", "E-commerce", "Education"});
        alPassword = addPasswordField(form, "Password");
        alConfirmPassword = addPasswordField(form, "Confirm Password");

        JButton submit = LoginFrame.primaryButton("Register as Alumni");
        submit.setAlignmentX(Component.LEFT_ALIGNMENT);
        submit.addActionListener(e -> handleAlumniRegister());
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(submit);

        return form;
    }

    private void handleStudentRegister() {
        if (isBlank(stuName.getText()) || isBlank(stuEmail.getText()) || isBlank(stuEnrollment.getText())
                || isBlank(stuBranch.getText())) {
            warn("Please complete all student profile fields.");
            return;
        }
        if (!isValidEmail(stuEmail.getText())) { warn("Enter a valid email address."); return; }
        if (emailExists(stuEmail.getText())) { warn("An account with this email already exists."); return; }
        String pass = new String(stuPassword.getPassword());
        String confirm = new String(stuConfirmPassword.getPassword());
        if (pass.isEmpty() || !pass.equals(confirm)) {
            warn("Passwords are empty or do not match.");
            return;
        }

        String id = "S" + String.format("%03d", DummyData.STUDENTS.size() + 1);
        Student student = new Student(id, stuName.getText().trim(), stuEmail.getText().trim(), pass,
                stuEnrollment.getText().trim(), stuBranch.getText().trim(),
                (String) stuYear.getSelectedItem(), null,
                (String) stuCareerGoal.getSelectedItem());
        DummyData.STUDENTS.add(student);

        showSuccessAndReturn();
    }

    private void handleAlumniRegister() {
        if (isBlank(alName.getText()) || isBlank(alEmail.getText()) || isBlank(alCompany.getText())
                || isBlank(alGradYear.getText()) || isBlank(alJobRole.getText())) {
            warn("Please complete all alumni profile fields.");
            return;
        }
        if (!alGradYear.getText().trim().matches("\\d{4}")) { warn("Graduation year must be a four-digit year."); return; }
        if (!isValidEmail(alEmail.getText())) { warn("Enter a valid email address."); return; }
        if (emailExists(alEmail.getText())) { warn("An account with this email already exists."); return; }
        String pass = new String(alPassword.getPassword());
        String confirm = new String(alConfirmPassword.getPassword());
        if (pass.isEmpty() || !pass.equals(confirm)) {
            warn("Passwords are empty or do not match.");
            return;
        }

        String id = "A" + String.format("%03d", DummyData.ALUMNI.size() + 1);
        Alumni alumni = new Alumni(id, alName.getText().trim(), alEmail.getText().trim(), pass,
                alGradYear.getText().trim(), "", alCompany.getText().trim(), alJobRole.getText().trim(),
                null, (String) alIndustry.getSelectedItem(), "",
                true, true);
        DummyData.ALUMNI.add(alumni);

        showSuccessAndReturn();
    }

    private void showSuccessAndReturn() {
        JOptionPane.showMessageDialog(this, "Registration Successful",
                "Welcome to AlumNet", JOptionPane.INFORMATION_MESSAGE);
        goBackToLogin();
    }

    private void goBackToLogin() {
        loginFrame.setVisible(true);
        this.dispose();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    private boolean emailExists(String email) {
        return DummyData.STUDENTS.stream().anyMatch(s -> s.getEmail().equalsIgnoreCase(email.trim()))
                || DummyData.ALUMNI.stream().anyMatch(a -> a.getEmail().equalsIgnoreCase(email.trim()));
    }

    private void warn(String message) {
        JOptionPane.showMessageDialog(this, message, "Incomplete Form", JOptionPane.WARNING_MESSAGE);
    }

    private JTextField addField(JPanel form, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.FONT_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField field = new JTextField();
        LoginFrame.styleField(field);
        form.add(label);
        form.add(field);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        return field;
    }

    private JPasswordField addPasswordField(JPanel form, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.FONT_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPasswordField field = new JPasswordField();
        LoginFrame.styleField(field);
        form.add(label);
        form.add(field);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        return field;
    }

    private JComboBox<String> addComboField(JPanel form, String labelText, String[] options) {
        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.FONT_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        combo.setFont(UITheme.FONT_LABEL);
        form.add(label);
        form.add(combo);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        return combo;
    }
}
