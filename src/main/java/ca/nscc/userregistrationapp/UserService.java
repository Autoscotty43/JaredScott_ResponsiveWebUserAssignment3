package ca.nscc.userregistrationapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Automatically inject the UserRepository to interact with the database
    @Autowired
    private UserRepository userRepository;

    // Automatically inject the PasswordEncoder to hash passwords
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to register a new user
    public User registerUser(String username, String email, String password) {
        // Check if the user already exists by email or username
        if (existsByEmail(email) || existsByUsername(username)) {
            throw new IllegalArgumentException("User with the given username or email already exists.");
        }

        // Create a new User object and set its attributes
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(password));

        // Save the new user in the database and return the saved user
        return userRepository.save(user);
    }

    // Check if a user exists by username
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Check if a user exists by email
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
