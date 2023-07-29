package com.sgutsev.library.controllers;

import com.sgutsev.library.dao.AuthorDAO;
import com.sgutsev.library.models.Author;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/library")
public class AuthorsController {
    @Autowired
    private AuthorDAO authorDAO;

    @GetMapping("/authors")
    public String showAllAuthors(Model model) {
        model.addAttribute("authors", authorDAO.index());
        return "authorsPage";
    }

    @GetMapping("/newAuthor")
    public String showAddAuthorPage(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthor";
    }

    @PostMapping("/newAuthor")
    public String addAuthor(@Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "addAuthor";
        authorDAO.save(author);
        return "redirect:/library/authors";
    }

    @PostMapping("/deleteAuthor/{id}")
    public String delete(@PathVariable("id") int id) {
        authorDAO.delete(id);
        return "redirect:/library/authors";
    }

    @GetMapping("/changeAuthor/{id}")
    public String showUpdateAuthorPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.showById(id));
        return "updateAuthor";
    }

    @PostMapping("/changeAuthor/{id}")
    public String update(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "updateAuthor";
        authorDAO.update(id, author);
        return "redirect:/library/authors";
    }
}
