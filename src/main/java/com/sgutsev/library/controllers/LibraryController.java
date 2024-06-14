package com.sgutsev.library.controllers;

import com.sgutsev.library.dao.interfaces.LibraryOperations;
import com.sgutsev.library.dao.interfaces.RolesOperation;
import com.sgutsev.library.dao.interfaces.UserOperations;
import com.sgutsev.library.helpers.AuthenticationManager;
import com.sgutsev.library.models.Roles;
import com.sgutsev.library.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private UserOperations usersDAO;

    @Autowired
    private RolesOperation rolesDAO;

    @Autowired
    private LibraryOperations libraryDAO;


    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        return "startPage";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public void processLogin(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
    }

    @GetMapping("/sign_up")
    public String showSignUpPage(Model model) {
        model.addAttribute("newUser", new User());
        return "signUp";
    }

    @PostMapping("/sign_up")
    public String registerUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult) {
        User existingUser = usersDAO.findByLoginUser(user.getLoginUser());
        if (existingUser != null && existingUser.getLoginUser() != null && !existingUser.getLoginUser().isEmpty())
            bindingResult.rejectValue("loginUser", null, "There is already an account registered with the same email");
        if (bindingResult.hasErrors())
            return "signUp";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        Roles userRole = rolesDAO.showByName("ROLE_USER");
        user.setUserRole(userRole);
        usersDAO.save(user);
        return "redirect:/library/home";
    }

    @GetMapping("/librariesInfo")
    public String showAllLibraries(Model model) {
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        model.addAttribute("libraries", libraryDAO.index());
        return "librariesInfo";
    }
}
