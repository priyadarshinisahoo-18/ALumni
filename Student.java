package alumnet.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String enrollmentNumber;
    private String branch;
    private String year;
    private List<String> skills;
    private String careerGoal;

    public Student(String id, String name, String email, String password,
                    String enrollmentNumber, String branch, String year,
                    List<String> skills, String careerGoal) {
        super(id, name, email, password, "STUDENT");
        this.enrollmentNumber = enrollmentNumber;
        this.branch = branch;
        this.year = year;
        this.skills = (skills != null) ? skills : new ArrayList<>();
        this.careerGoal = careerGoal;
    }

    public String getEnrollmentNumber() { return enrollmentNumber; }
    public void setEnrollmentNumber(String enrollmentNumber) { this.enrollmentNumber = enrollmentNumber; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getCareerGoal() { return careerGoal; }
    public void setCareerGoal(String careerGoal) { this.careerGoal = careerGoal; }
}
