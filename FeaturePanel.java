package alumnet.gui;

import alumnet.model.Alumni;
import alumnet.model.Student;
import alumnet.model.User;
import alumnet.util.DummyData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

/** Working, role-aware screens for the sections outside each dashboard home. */
public class FeaturePanel extends JPanel {
    private static final java.util.List<String> CONNECTIONS = new ArrayList<>();
    private static final java.util.List<String> JOBS = new ArrayList<>(Arrays.asList(
            "Software Engineering Intern | Google | Bangalore | Internship",
            "Graduate Software Engineer | Microsoft | Hyderabad | Full-time",
            "Data Analyst Intern | Deloitte | Remote | Internship"));
    private static final java.util.List<String> MESSAGES = new ArrayList<>(Arrays.asList(
            "Welcome to AlumNet. Use Compose to start a conversation."));
    private static final Map<String, Boolean> SETTINGS = new LinkedHashMap<>();
    static {
        SETTINGS.put("Allow new registrations", true);
        SETTINGS.put("Show profiles in search", true);
        SETTINGS.put("Maintenance mode", false);
    }
    private final User user;
    private final String section;
    private final JPanel rows = new JPanel();
    private final JTextField search = new JTextField(22);

    public FeaturePanel(User user, String section) {
        this.user = user;
        this.section = section;
        setLayout(new BorderLayout(0, 14));
        setBackground(UITheme.LIGHT_GREY);
        setBorder(new EmptyBorder(26, 32, 26, 32));
        JLabel title = new JLabel(section);
        title.setFont(UITheme.FONT_TITLE); title.setForeground(UITheme.NAVY);
        JPanel top = new JPanel(new BorderLayout(10, 12)); top.setOpaque(false);
        top.add(title, BorderLayout.NORTH);
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0)); controls.setOpaque(false);
        if (isSearchable()) {
            search.setToolTipText("Search this section"); search.setPreferredSize(new Dimension(240, 34));
            JButton find = LoginFrame.secondaryButton("Search"); find.addActionListener(e -> refresh());
            search.addActionListener(e -> refresh()); controls.add(search); controls.add(find);
        }
        JButton action = primaryAction(); if (action != null) controls.add(action);
        top.add(controls, BorderLayout.CENTER);
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS)); rows.setOpaque(false);
        JScrollPane scroll = new JScrollPane(rows); scroll.setBorder(null); scroll.getViewport().setOpaque(false);
        add(top, BorderLayout.NORTH); add(scroll, BorderLayout.CENTER);
        refresh();
    }

    private boolean isStudent() { return "STUDENT".equals(user.getRole()); }
    private boolean isAdmin() { return "ADMIN".equals(user.getRole()); }
    private boolean isSearchable() { return section.equals("Find Alumni") || section.equals("My Network") || section.equals("Jobs") || section.equals("Jobs & Internships") || section.equals("Students") || section.equals("Alumni"); }

    private JButton primaryAction() {
        String label = null;
        if (section.equals("Messages")) label = "Compose message";
        else if (section.equals("Jobs") && !isStudent()) label = "Post a job";
        else if (section.equals("My Profile")) label = "Edit profile";
        if (label == null) return null;
        JButton b = LoginFrame.primaryButton(label);
        b.addActionListener(e -> {
            if (section.equals("Messages")) composeMessage();
            else if (section.equals("Jobs")) postJob();
            else if (section.equals("My Profile")) editProfile();
            else JOptionPane.showMessageDialog(this, "Settings saved for this session.", "Settings", JOptionPane.INFORMATION_MESSAGE);
        });
        return b;
    }

    private void refresh() {
        rows.removeAll(); String q = search.getText().trim().toLowerCase(Locale.ROOT); int count = 0;
        for (String entry : entries()) {
            if (!entry.toLowerCase(Locale.ROOT).contains(q)) continue;
            rows.add(makeRow(entry)); rows.add(Box.createRigidArea(new Dimension(0, 8))); count++;
        }
        if (count == 0) {
            JLabel empty = new JLabel(q.isEmpty() ? "Nothing here yet." : "No matching results.");
            empty.setFont(UITheme.FONT_LABEL); empty.setForeground(Color.GRAY); empty.setBorder(new EmptyBorder(20, 4, 20, 4)); rows.add(empty);
        }
        rows.revalidate(); rows.repaint();
    }

    private java.util.List<String> entries() {
        java.util.List<String> result = new ArrayList<>();
        if (section.equals("Find Alumni") || section.equals("Alumni")) {
            for (Alumni a : DummyData.ALUMNI) result.add(a.getName()+" | "+a.getJobRole()+" at "+a.getCompany()+" | "+a.getIndustry()+" | "+a.getLocation());
        } else if (section.equals("Students")) {
            for (Student s : DummyData.STUDENTS) result.add(s.getName()+" | "+s.getBranch()+" | "+s.getYear()+" | Goal: "+s.getCareerGoal());
        } else if (section.equals("My Network")) {
            synchronized (CONNECTIONS) { result.addAll(CONNECTIONS); }
            if (result.isEmpty()) result.add("Your network is ready. Find people and send a connection request to get started.");
        } else if (section.equals("Jobs") || section.equals("Jobs & Internships")) {
            synchronized (JOBS) { result.addAll(JOBS); }
        } else if (section.equals("Messages")) {
            synchronized (MESSAGES) { result.addAll(MESSAGES); }
        } else if (section.equals("Mentorship")) {
            for (Student s : DummyData.STUDENTS) result.add(s.getName()+" | "+s.getBranch()+" | Goal: "+s.getCareerGoal()+" | Seeking a mentor");
        } else if (section.equals("Reports")) {
            result.add("Registered students: "+DummyData.STUDENTS.size()); result.add("Registered alumni: "+DummyData.ALUMNI.size()); result.add("Available jobs: "+JOBS.size()); result.add("Messages in this session: "+MESSAGES.size());
        } else if (section.equals("My Profile")) {
            result.add("Name: "+user.getName()); result.add("Email: "+user.getEmail()); result.add("Account type: "+user.getRole());
            if (user instanceof Student) { Student s=(Student)user; result.add("Enrollment: "+s.getEnrollmentNumber()); result.add("Branch / year: "+s.getBranch()+" / "+s.getYear()); result.add("Career goal: "+s.getCareerGoal()); }
            if (user instanceof Alumni) { Alumni a=(Alumni)user; result.add("Company / role: "+a.getCompany()+" / "+a.getJobRole()); result.add("Graduation year: "+a.getGraduationYear()); result.add("Industry: "+a.getIndustry()); }
        } else if (section.equals("Settings")) {
            result.add("Account: "+user.getEmail());
            synchronized (SETTINGS) { for (Map.Entry<String, Boolean> entry : SETTINGS.entrySet()) result.add(entry.getKey()+": "+(entry.getValue()?"Enabled":"Disabled")); }
            result.add("Data storage: in memory for this demo session");
        }
        return result;
    }

    private JPanel makeRow(String text) {
        JPanel row = new JPanel(new BorderLayout(12, 0)); row.setBackground(UITheme.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UITheme.MID_GREY, 1, true), new EmptyBorder(12, 14, 12, 14)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 78));
        JLabel label = new JLabel("<html>"+escape(text)+"</html>"); label.setFont(UITheme.FONT_LABEL); label.setForeground(UITheme.TEXT_DARK); row.add(label, BorderLayout.CENTER);
        String action = rowAction(text);
        if (action != null) { JButton b=LoginFrame.secondaryButton(action); b.addActionListener(e -> rowActionClicked(text, b)); row.add(b, BorderLayout.EAST); }
        return row;
    }

    private String rowAction(String text) {
        if (section.equals("Find Alumni")) return "Connect";
        if (section.equals("Mentorship")) return "Offer mentorship";
        if (section.equals("Jobs") || section.equals("Jobs & Internships")) return isStudent()?"Save job":null;
        if (section.equals("Students")) return "View profile";
        if (section.equals("Settings") && text.contains(":")) {
            synchronized (SETTINGS) { if (SETTINGS.containsKey(text.substring(0, text.indexOf(':')))) return "Toggle"; }
        }
        return null;
    }

    private void rowActionClicked(String text, JButton button) {
        if (section.equals("Find Alumni")) {
            synchronized (CONNECTIONS) { if (!CONNECTIONS.contains(text)) CONNECTIONS.add(text); }
            button.setText("Requested"); button.setEnabled(false);
        } else if (section.equals("Mentorship")) {
            synchronized (CONNECTIONS) { CONNECTIONS.add("Mentorship offered to "+text); }
            button.setText("Offer sent"); button.setEnabled(false);
        } else if (section.contains("Jobs")) {
            synchronized (CONNECTIONS) { CONNECTIONS.add("Saved job: "+text); }
            button.setText("Saved"); button.setEnabled(false);
        } else if (section.equals("Settings")) {
            int colon=text.indexOf(':'); String key=text.substring(0, colon);
            synchronized (SETTINGS) { if (SETTINGS.containsKey(key)) SETTINGS.put(key, !SETTINGS.get(key)); }
            refresh();
        } else JOptionPane.showMessageDialog(this, text, "Student profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void composeMessage() {
        JTextField recipient=new JTextField(), body=new JTextField();
        Object[] fields={"To (name or email):",recipient,"Message:",body};
        if (JOptionPane.showConfirmDialog(this,fields,"Compose message",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        if (recipient.getText().trim().isEmpty()||body.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this,"Enter a recipient and message."); return; }
        synchronized (MESSAGES) { MESSAGES.add("To "+recipient.getText().trim()+": "+body.getText().trim()); } refresh();
    }

    private void postJob() {
        JTextField role=new JTextField(), company=new JTextField(), location=new JTextField();
        Object[] fields={"Role:",role,"Company:",company,"Location:",location};
        if (JOptionPane.showConfirmDialog(this,fields,"Post a job",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        if (role.getText().trim().isEmpty()||company.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this,"Role and company are required."); return; }
        synchronized (JOBS) { JOBS.add(role.getText().trim()+" | "+company.getText().trim()+" | "+location.getText().trim()+" | Full-time"); } refresh();
    }

    private void editProfile() {
        JTextField name=new JTextField(user.getName()), email=new JTextField(user.getEmail());
        Object[] fields={"Name:",name,"Email:",email};
        if (JOptionPane.showConfirmDialog(this,fields,"Edit profile",JOptionPane.OK_CANCEL_OPTION)!=JOptionPane.OK_OPTION) return;
        if (name.getText().trim().isEmpty()||!email.getText().trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) { JOptionPane.showMessageDialog(this,"Enter a name and valid email address."); return; }
        user.setName(name.getText().trim()); user.setEmail(email.getText().trim()); refresh();
    }

    private String escape(String s) { return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;"); }
}
