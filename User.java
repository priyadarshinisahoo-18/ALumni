package alumnet.model;

/**
 * Base class shared by Student and Alumni.
 * Admin does not need a full profile, so it is handled separately with
 * simple dummy credentials in DummyData.
 */
public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private String role; // "STUDENT", "ALUMNI", "ADMIN"

    public User(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
