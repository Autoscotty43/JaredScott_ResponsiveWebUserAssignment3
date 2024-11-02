package ca.nscc.userregistrationapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Validate input fields
            if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Validate password strength
            if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
                throw new IllegalArgumentException("Password must be at least 8 characters long, with at least one letter, one number, and one special character.");
            }

            // Check if the user already exists
            if (userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Username or Email already exists.");
            }

            // Hash the password and register the user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registeredUser = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());

            model.addAttribute("registeredUser", registeredUser);
            model.addAttribute("successMessage", "Registration successful!");
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

