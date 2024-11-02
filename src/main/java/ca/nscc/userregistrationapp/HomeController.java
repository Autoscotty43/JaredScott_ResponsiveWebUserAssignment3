package ca.nscc.userregistrationapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            // Retrieve full user details from the database based on the username
            ca.nscc.userregistrationapp.User fullUser = userService.findByUsername(userDetails.getUsername());

            if (fullUser != null) {
                model.addAttribute("username", fullUser.getUsername());
                model.addAttribute("email", fullUser.getEmail());
            } else {
                model.addAttribute("error", "User details could not be found.");
            }
        } else {
            // User is not authenticated, you can handle it here or just set a default message
            model.addAttribute("username", "Guest"); // Display a default username for guests
        }

        return "home";
    }
}
