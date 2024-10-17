package ca.nscc.userregistrationapp;

import jakarta.persistence.*;

// Sates user
@Entity
@Table(name = "users")
public class User {
// Generates User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Default constructor
    public User() {}

    // Getter for the user ID
    public Long getId() {
        return id;
    }

    // Setter for the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Setter for the email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter for the password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for the username
    public String getUsername() {
        return username;
    }

    // Getter for the email
    public String getEmail() {
        return email;
    }

    // Getter for the password
    public String getPassword() {
        return password;
    }

    // Override toString method User objects
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Override equals method to compare id, username, and email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the same object
        if (!(o instanceof User)) return false; // Check object of User type

        User user = (User) o;

        // Check for id, username, and email
        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        return email.equals(user.email);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
