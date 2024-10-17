package ca.nscc.userregistrationapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// Registration Model
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Register Mapping
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Validate user input
            if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Check for existing user
            if (userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Username or Email already exists.");
            }

            // Hash the password
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword); // Set the hashed password

            // Register the user
            User registeredUser = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());

            // Add user details and success message to the model
            model.addAttribute("registeredUser", registeredUser);
            model.addAttribute("username", registeredUser.getUsername());
            model.addAttribute("email", registeredUser.getEmail());
            model.addAttribute("successMessage", "Registration successful!");
            model.addAttribute("message", "Welcome, " + registeredUser.getUsername() + "!");

            // Return to the success page
            return "registrationsuccess";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "register";
        }
    }
}
