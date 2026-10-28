package alumnet.model;

import java.util.ArrayList;
import java.util.List;

public class Alumni extends User {

    private String graduationYear;
    private String branch;
    private String company;
    private String jobRole;
    private List<String> skills;
    private String industry;
    private String location;
    private boolean openToMentorship;
    private boolean openToReferrals;

    public Alumni(String id, String name, String email, String password,
                   String graduationYear, String branch, String company, String jobRole,
                   List<String> skills, String industry, String location,
                   boolean openToMentorship, boolean openToReferrals) {
        super(id, name, email, password, "ALUMNI");
        this.graduationYear = graduationYear;
        this.branch = branch;
        this.company = company;
        this.jobRole = jobRole;
        this.skills = (skills != null) ? skills : new ArrayList<>();
        this.industry = industry;
        this.location = location;
        this.openToMentorship = openToMentorship;
        this.openToReferrals = openToReferrals;
    }

    public String getGraduationYear() { return graduationYear; }
    public void setGraduationYear(String graduationYear) { this.graduationYear = graduationYear; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getJobRole() { return jobRole; }
    public void setJobRole(String jobRole) { this.jobRole = jobRole; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isOpenToMentorship() { return openToMentorship; }
    public void setOpenToMentorship(boolean openToMentorship) { this.openToMentorship = openToMentorship; }

    public boolean isOpenToReferrals() { return openToReferrals; }
    public void setOpenToReferrals(boolean openToReferrals) { this.openToReferrals = openToReferrals; }
}
