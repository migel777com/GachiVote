package kz.edu.controller;

import kz.edu.dao.UserDAO;
import kz.edu.model.Question;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Controller2
{
    private final UserDAO userDAO;
    @Autowired
    public Controller2(UserDAO userDAO)
    { this.userDAO = userDAO;}
    PasswordEncoder passwordEncoder;
    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}
    @RequestMapping(value={"", "/", "home"})
    public String home()
    {
        return "home";
    }
    @GetMapping("/profile")
    public String profile(Model model)
    {
        String currentUserName = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        User user = userDAO.findByUserName(currentUserName);
        model.addAttribute("user", user);
        return "profile";
    }
    @GetMapping("/profile/edit/{id}")
    public String updateUser(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("user", userDAO.findByUserId(id));
        return "edit-user";
    }
    @PatchMapping("/profile/{id}")
    public String updateUserPatch(@ModelAttribute("user") User user, @PathVariable("id") int id)
    {
        userDAO.updateUser(user, id);
        return "redirect:/profile";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, @RequestParam("username") String email, Model model)
    {
        System.out.println("REGISTRATION:"+email);

        if (userDAO.findByUserName(email) != null)
        {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        else
        {
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAO.addUser(user);
            return "redirect:/login";
        }
    }
}