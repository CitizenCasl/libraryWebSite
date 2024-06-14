package com.sgutsev.library.controllers;


import com.sgutsev.library.dao.interfaces.BookOperations;
import com.sgutsev.library.dao.interfaces.FavoriteBooksOperations;
import com.sgutsev.library.dao.interfaces.UserOperations;
import com.sgutsev.library.helpers.AuthenticationManager;
import com.sgutsev.library.models.User;
import com.sgutsev.library.models.UserPasswords;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class UserController {
    @Autowired
    private UserOperations usersDAO;

    @Autowired
    private FavoriteBooksOperations favoriteBooksDAO;

    @GetMapping("/info")
    public String index(Model model) {
        User user = AuthenticationManager.getAuthenticationUser();
        model.addAttribute("user", user);
        return "userInfo";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        UserPasswords userPasswords = new UserPasswords();
        model.addAttribute("userPasswords", userPasswords);
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String saveNewPassword(@Valid @ModelAttribute("userPasswords") UserPasswords userPasswords, BindingResult bindingResult) {
        User user = AuthenticationManager.getAuthenticationUser();
        String encodedPassword = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(userPasswords.getCurrentPassword(), encodedPassword)) {
            bindingResult.rejectValue("currentPassword", "password.invalid", "Invalid current password");
            return "changePassword";
        }
        if (!userPasswords.getNewPassword().equals(userPasswords.getConfirmNewPassword())) {
            bindingResult.rejectValue("confirmNewPassword", "password.mismatch", "New password and confirm password do not match");
            return "changePassword";
        }
        if (bindingResult.hasErrors()) {
            return "changePassword";
        }
        String encodedNewPassword = passwordEncoder.encode(userPasswords.getNewPassword());
        user.setPassword(encodedNewPassword);
        usersDAO.updatePassword(user.getId(), user);
        return "redirect:/account/info";
    }

    @GetMapping("/editUserInfo")
    public String editUserProfile(Model model) {
        User user = AuthenticationManager.getAuthenticationUser();
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUserInfo")
    public String saveChanges(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        User existingUser = usersDAO.findByLoginUser(user.getLoginUser());
        if (existingUser != null && !(existingUser.getId() == user.getId()))
            bindingResult.rejectValue("loginUser", null, "There is already an account used with the same email");
        if (bindingResult.hasFieldErrors("nameUser") ||
                bindingResult.hasFieldErrors("loginUser") ||
                bindingResult.hasFieldErrors("numberOfCard") ||
                bindingResult.hasFieldErrors("phoneUser")) {
            return "editUser";
        }
        usersDAO.updateInfo(user.getId(), user);
        User updateUser = AuthenticationManager.getAuthenticationUser();
        updateUser.setNameUser(user.getNameUser());
        updateUser.setLoginUser(user.getLoginUser());
        updateUser.setNumberOfCard(user.getNumberOfCard());
        updateUser.setPhoneUser(user.getPhoneUser());
        model.addAttribute("user", updateUser);
        return "redirect:/account/info";
    }

    @GetMapping("/showAllUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAllLibrariesUsers() {
        return "";
    }

    @GetMapping("/showWishList")
    public String showUserWishList(Model model) {
        User currentUser = AuthenticationManager.getAuthenticationUser();
        model.addAttribute("favoriteBooks", favoriteBooksDAO.showUserFavoriteBooks(currentUser));
        return "wishListPage";
    }
}
