package com.sgutsev.library.controllers;

import com.sgutsev.library.dao.interfaces.AuthorOperations;
import com.sgutsev.library.models.Author;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/library")
public class AuthorsController {
    @Autowired
    private AuthorOperations authorDAO;
    private final String photoDir = "D:\\Универ\\Практика\\library\\src\\main\\resources\\static\\photo\\";

    @GetMapping("/authors")
    public String showAllAuthors(Model model) {
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
            String photo = "/photo/" + authorPhoto.getOriginalFilename();
            String filePath = photoDir + authorPhoto.getOriginalFilename();
            File dest = new File(filePath);
            authorPhoto.transferTo(dest);
            author.setPhoto(photo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorDAO.save(author);
        return "redirect:/library/authors";
    }

    @PostMapping("/deleteAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id) {
        String photo = authorDAO.showById(id).getPhoto().replace("/photo/", "");
        File imageAuthorForDelete = new File(photoDir + photo);
        imageAuthorForDelete.delete();
        authorDAO.delete(id);
        return "redirect:/library/authors";
    }

    @GetMapping("/changeAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateAuthorPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.showById(id));
        return "updateAuthor";
    }

    @PostMapping("/changeAuthor/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "updateAuthor";
        authorDAO.update(id, author);
        return "redirect:/library/authors";
    }
}
