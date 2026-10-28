package alumnet.util;

import alumnet.model.Alumni;
import alumnet.model.Student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    public static final List<Student> STUDENTS = new ArrayList<>();
    public static final List<Alumni> ALUMNI = new ArrayList<>();

    // Fixed admin login for the prototype.
    public static final String ADMIN_EMAIL = "admin@alumnet.com";
    public static final String ADMIN_PASSWORD = "admin123";

    private static final String[] FIRST_NAMES = {
            "Aarav", "Aditi", "Akash", "Ananya", "Arjun",
            "Diya", "Ishaan", "Kavya", "Neha", "Rohan",
            "Rahul", "Priya", "Sana", "Vikram", "Meera",
            "Karan", "Nisha", "Aditya", "Pooja", "Varun"
    };

    private static final String[] LAST_NAMES = {
            "Sharma", "Verma", "Gupta", "Singh", "Mehta",
            "Rao", "Kumar", "Patel", "Das", "Nair",
            "Jain", "Reddy", "Malhotra", "Shah", "Mishra"
    };

    private static final String[] BRANCHES = {
            "CSE", "CSE (AI)", "Information Technology",
            "Electronics", "Electrical Engineering", "Mechanical Engineering"
    };

    private static final String[] YEARS = {
            "1st Year", "2nd Year", "3rd Year", "4th Year"
    };

    private static final String[] CAREER_GOALS = {
            "Software Engineer", "AI/ML Engineer", "Data Scientist",
            "Web Developer", "Product Manager", "Cybersecurity Analyst",
            "Cloud Engineer", "UI/UX Designer"
    };

    private static final String[][] SKILLS = {
            {"Java", "Python", "Machine Learning"},
            {"JavaScript", "React", "HTML"},
            {"Python", "Data Science", "SQL"},
            {"C++", "Algorithms", "Problem Solving"},
            {"Java", "Spring", "Databases"},
            {"Cloud", "Linux", "Networking"},
            {"Figma", "UI/UX", "Prototyping"},
            {"Python", "AI", "Statistics"}
    };

    private static final String[] COMPANIES = {
            "Google", "Microsoft", "Amazon", "TCS", "Infosys",
            "Wipro", "Adobe", "Accenture", "Deloitte", "IBM",
            "Oracle", "Flipkart", "Paytm", "Zoho", "Cognizant"
    };

    private static final String[] ALUMNI_ROLES = {
            "Software Engineer", "AI/ML Engineer", "Data Scientist",
            "Product Manager", "Cloud Engineer", "Business Analyst",
            "Frontend Developer", "Cybersecurity Analyst",
            "Data Engineer", "UX Designer"
    };

    private static final String[] INDUSTRIES = {
            "Technology", "Finance", "Healthcare", "Consulting",
            "E-commerce", "Education"
    };

    private static final String[] LOCATIONS = {
            "Bangalore", "Hyderabad", "Delhi NCR", "Mumbai",
            "Chennai", "Pune", "Remote"
    };

    static {
        addOriginalStudents();
        addGeneratedStudents(250);

        addOriginalAlumni();
        addGeneratedAlumni(250);
    }

    private static void addOriginalStudents() {
        STUDENTS.add(new Student(
                "S001",
                "Priyadarshini Rao",
                "priya@nietstudent.in",
                "student123",
                "NIET2024001",
                "CSE (AI)",
                "3rd Year",
                Arrays.asList("Java", "Python", "Machine Learning"),
                "AI/ML Engineer"
        ));

        STUDENTS.add(new Student(
                "S002",
                "Rohan Mehta",
                "rohan@nietstudent.in",
                "student123",
                "NIET2024002",
                "CSE",
                "3rd Year",
                Arrays.asList("Java", "React", "SQL"),
                "Software Engineer"
        ));
    }

    private static void addGeneratedStudents(int totalStudents) {
        for (int i = STUDENTS.size() + 1; i <= totalStudents; i++) {
            String firstName = FIRST_NAMES[(i - 1) % FIRST_NAMES.length];
            String lastName = LAST_NAMES[(i * 3) % LAST_NAMES.length];

            String id = String.format("S%03d", i);
            String name = firstName + " " + lastName;
            String email = "student" + i + "@nietstudent.in";
            String enrollmentNumber = String.format("NIET2024%04d", i);

            String branch = BRANCHES[(i - 1) % BRANCHES.length];
            String year = YEARS[(i - 1) % YEARS.length];
            String careerGoal = CAREER_GOALS[(i - 1) % CAREER_GOALS.length];
            List<String> skills = Arrays.asList(SKILLS[(i - 1) % SKILLS.length]);

            STUDENTS.add(new Student(
                    id,
                    name,
                    email,
                    "student123",
                    enrollmentNumber,
                    branch,
                    year,
                    skills,
                    careerGoal
            ));
        }
    }

    private static void addOriginalAlumni() {
        ALUMNI.add(new Alumni(
                "A001",
                "Rahul Sharma",
                "rahul@alumni.com",
                "alumni123",
                "2022",
                "CSE (AI)",
                "Google",
                "AI/ML Engineer",
                Arrays.asList("Python", "Machine Learning", "Cloud"),
                "Technology",
                "Bangalore",
                true,
                true
        ));

        ALUMNI.add(new Alumni(
                "A002",
                "Ananya Verma",
                "ananya@alumni.com",
                "alumni123",
                "2021",
                "CSE",
                "Microsoft",
                "Software Engineer",
                Arrays.asList("Java", "C++", "Cloud"),
                "Technology",
                "Hyderabad",
                true,
                false
        ));

        ALUMNI.add(new Alumni(
                "A003",
                "Aman Gupta",
                "aman@alumni.com",
                "alumni123",
                "2020",
                "IT",
                "Deloitte",
                "Data Scientist",
                Arrays.asList("Python", "Data Science", "SQL"),
                "Consulting",
                "Delhi NCR",
                false,
                true
        ));

        ALUMNI.add(new Alumni(
                "A004",
                "Neha Singh",
                "neha@alumni.com",
                "alumni123",
                "2023",
                "CSE",
                "Amazon",
                "Product Manager",
                Arrays.asList("SQL", "UI/UX", "Cloud"),
                "E-commerce",
                "Remote",
                true,
                true
        ));
    }

    private static void addGeneratedAlumni(int totalAlumni) {
        for (int i = ALUMNI.size() + 1; i <= totalAlumni; i++) {
            String firstName = FIRST_NAMES[(i + 4) % FIRST_NAMES.length];
            String lastName = LAST_NAMES[(i * 2 + 1) % LAST_NAMES.length];

            String id = String.format("A%03d", i);
            String name = firstName + " " + lastName;
            String email = "alumni" + i + "@alumni.com";
            String graduationYear = String.valueOf(2015 + (i % 10));

            String branch = BRANCHES[(i - 1) % BRANCHES.length];
            String company = COMPANIES[(i - 1) % COMPANIES.length];
            String jobRole = ALUMNI_ROLES[(i - 1) % ALUMNI_ROLES.length];
            String industry = INDUSTRIES[(i - 1) % INDUSTRIES.length];
            String location = LOCATIONS[(i - 1) % LOCATIONS.length];
            List<String> skills = Arrays.asList(SKILLS[(i - 1) % SKILLS.length]);

            boolean openToMentorship = i % 3 != 0;
            boolean openToReferrals = i % 4 != 0;

            ALUMNI.add(new Alumni(
                    id,
                    name,
                    email,
                    "alumni123",
                    graduationYear,
                    branch,
                    company,
                    jobRole,
                    skills,
                    industry,
                    location,
                    openToMentorship,
                    openToReferrals
            ));
        }
    }

    /** Returns a student when the email and password match; otherwise returns null. */
    public static Student authenticateStudent(String email, String password) {
        for (Student student : STUDENTS) {
            if (student.getEmail().equalsIgnoreCase(email)
                    && student.getPassword().equals(password)) {
                return student;
            }
        }
        return null;
    }

    /** Returns an alumnus when the email and password match; otherwise returns null. */
    public static Alumni authenticateAlumni(String email, String password) {
        for (Alumni alumni : ALUMNI) {
            if (alumni.getEmail().equalsIgnoreCase(email)
                    && alumni.getPassword().equals(password)) {
                return alumni;
            }
        }
        return null;
    }

    /** Checks the fixed admin email and password. */
    public static boolean authenticateAdmin(String email, String password) {
        return ADMIN_EMAIL.equalsIgnoreCase(email)
                && ADMIN_PASSWORD.equals(password);
    }
}