package animelord.entities;

import java.sql.Timestamp;

public class User {

    private int userId;

    private String username;

    private String email;

    private String passwordHash;

    private String role;

    private Timestamp createdAt;
    
    private boolean emailVerified;

    private String verificationToken;
    /*
        DEFAULT CONSTRUCTOR
    */
    public User() {
    }

    /*
        PARAMETERIZED CONSTRUCTOR
    */
    public User(
            int userId,
            String username,
            String email,
            String passwordHash,
            String role,
            Timestamp createdAt) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }

    /*
        GETTERS & SETTERS
    */

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isEmailVerified() {
    return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    /*
        HELPER METHODS
    */

    public boolean isAdmin() {

        return "ADMIN".equalsIgnoreCase(role);

    }

    public boolean isUser() {

        return "USER".equalsIgnoreCase(role);

    }

    @Override
    public String toString() {

        return "User{"
                + "userId=" + userId
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", role='" + role + '\''
                + ", createdAt=" + createdAt
                + '}';
    }

}