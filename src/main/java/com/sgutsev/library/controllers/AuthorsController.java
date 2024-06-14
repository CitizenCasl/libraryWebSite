package com.sgutsev.library.controllers;

import com.sgutsev.library.dao.interfaces.AuthorOperations;
import com.sgutsev.library.helpers.AuthenticationManager;
import com.sgutsev.library.models.Author;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/library")
public class AuthorsController {
    @Autowired
    private AuthorOperations authorDAO;

    @GetMapping("/authors")
    public String showAllAuthors(Model model) {
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        model.addAttribute("authority", AuthenticationManager.checkAuthorityAdmin());
        model.addAttribute("authors", authorDAO.index());
        return "authorsPage";
    }

    @GetMapping("/newAuthor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAddAuthorPage(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/newAuthor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addAuthor(@RequestParam("authorPhoto") MultipartFile authorPhoto, @Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addAuthor";
        try {
            author.setPhoto(authorPhoto.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "addAuthor";
        }
        authorDAO.save(author);
        return "redirect:/library/authors";
    }

    @PostMapping("/deleteAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id) {
        authorDAO.delete(id);
        return "redirect:/library/authors";
    }

    @GetMapping("/changeAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateAuthorPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.showById(id));
        model.addAttribute("authorPhoto", false);
        return "updateAuthor";
    }

    @PostMapping("/changeAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@RequestParam("authorPhoto") MultipartFile authorPhoto,
                         @ModelAttribute("author") @Valid Author author,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "updateAuthor";
        try {
            if (authorPhoto.isEmpty()) {
                author.setPhoto(authorDAO.showById(id).getPhoto());
            } else {
                author.setPhoto(authorPhoto.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "updateAuthor";
        }
        authorDAO.update(id, author);
        return "redirect:/library/authors";
    }

    @GetMapping("/viewAuthor/{id}")
    public String showAuthorBiography(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.showById(id));
        return "biographyPage";
    }
}
