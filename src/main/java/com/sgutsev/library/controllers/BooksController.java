package com.sgutsev.library.controllers;

import com.sgutsev.library.dao.AuthorDAO;
import com.sgutsev.library.dao.BookDAO;
import com.sgutsev.library.models.Author;
import com.sgutsev.library.models.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/library")
public class BooksController {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private AuthorDAO authorDAO;

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "booksPage";
    }

    @GetMapping("/newBook")
    public String showAddAuthorPage(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorDAO.index());
        return "addBook";
    }

    @PostMapping("/newBook")
    public String addBook(@Valid Book book, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("authors", authorDAO.index());
            return "addBook";
        }
        bookDAO.save(book);
        return "redirect:/library/books";
    }

    @PostMapping("/deleteBook/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/library/books";
    }

    @GetMapping("/changeBook/{id}")
    public String showUpdateBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("authors", authorDAO.index());
        model.addAttribute("book", bookDAO.showById(id));
        return "updateBook";
    }

    @PostMapping("/changeBook/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("authors", authorDAO.index());
            return "updateBook";
        }
        bookDAO.update(id, book);
        return "redirect:/library/books";
    }
}
