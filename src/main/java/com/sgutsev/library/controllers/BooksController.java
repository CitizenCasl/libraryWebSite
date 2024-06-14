package com.sgutsev.library.controllers;


import com.sgutsev.library.dao.interfaces.*;
import com.sgutsev.library.helpers.AuthenticationManager;
import com.sgutsev.library.models.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/library")
public class BooksController {
    @Autowired
    private BookOperations bookDAO;
    @Autowired
    private AuthorOperations authorDAO;
    @Autowired
    private GenresOperations genresDAO;
    @Autowired
    private CommentOperations commentDAO;
    @Autowired
    private BooksGenresOperations booksGenresDAO;
    @Autowired
    private FavoriteBooksOperations favoriteBooksDAO;


    @GetMapping("/books")
    public String showAllBooks(Model model) {
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        model.addAttribute("authority", AuthenticationManager.checkAuthorityAdmin());
        model.addAttribute("books", bookDAO.index());
        return "booksPage";
    }

    @GetMapping("/newBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAddBookPage(Model model) {
        model.addAttribute("authors", authorDAO.index());
        model.addAttribute("genres", genresDAO.index());
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/newBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBook(@RequestParam("bookCover") MultipartFile bookCover,
                          @RequestParam("authorName") String authorName,
                          @RequestParam(name = "genres", required = false) List<String> selectedGenres,
                          @Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorDAO.index());
            return "addBook";
        }
        try {
            book.setCover(bookCover.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "addBook";
        }
        Author author = authorDAO.showByName(authorName);
        book.setNameAuthorOfBook(author);
        if (selectedGenres != null && !selectedGenres.isEmpty()) {
            for (String selectedGenre : selectedGenres) {
                booksGenresDAO.save(book, genresDAO.getGenre(selectedGenre));
            }
        }
        bookDAO.save(book);
        return "redirect:/library/books";
    }

    @PostMapping("/deleteBook/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") int id) {
        if (!booksGenresDAO.getGenresForBook(bookDAO.showById(id)).isEmpty()){
            booksGenresDAO.delete(id);
        }
        bookDAO.delete(id);
        return "redirect:/library/books";
    }

    @GetMapping("/changeBook/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("authors", authorDAO.index());
        model.addAttribute("book", bookDAO.showById(id));
        model.addAttribute("genres", genresDAO.index());
        model.addAttribute("currentGenres", booksGenresDAO.getGenresForBook(bookDAO.showById(id)));
        model.addAttribute("bookCover", false);
        return "updateBook";
    }

    @PostMapping("/changeBook/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @RequestParam("authorName") String authorName,
                         @RequestParam(name = "genres", required = false) List<String> selectedGenres,
                         @RequestParam("bookCover") MultipartFile bookCover,
                         BindingResult bindingResult, @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorDAO.index());
            return "updateBook";
        }
        try {
            if (bookCover.isEmpty()) {
                book.setCover(bookDAO.showById(id).getCover());
            } else {
                book.setCover(bookCover.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "updateBook";
        }
        Author author = authorDAO.showByName(authorName);
        book.setNameAuthorOfBook(author);
        booksGenresDAO.delete(book.getId());
        if (selectedGenres != null && !selectedGenres.isEmpty()) {
            for (String selectedGenre : selectedGenres) {
                booksGenresDAO.save(book, genresDAO.getGenre(selectedGenre));
            }
        }
        bookDAO.update(id, book);
        return "redirect:/library/books";
    }

    @GetMapping("/viewBook/{id}")
    public String showBookDescription(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showById(id));
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        if(AuthenticationManager.checkAuthentication()){
            model.addAttribute("isFavoriteBook", favoriteBooksDAO.checkBookIsFavorite(AuthenticationManager.getAuthenticationUser(), bookDAO.showById(id)));
        }
        model.addAttribute("isAdmin", AuthenticationManager.checkAuthorityAdmin());
        if (AuthenticationManager.checkAuthentication()) {
            model.addAttribute("authenticationUserId", AuthenticationManager.getAuthenticationUser().getId());
        } else {
            model.addAttribute("authenticationUserId", null);
        }
        String genresAsString = booksGenresDAO.getGenresForBook(bookDAO.showById(id))
                .stream()
                .map(Genres::getNameGenre)
                .collect(Collectors.joining("; "));
        if (genresAsString.isEmpty()) {
            model.addAttribute("noGenres", false);
        } else {
            model.addAttribute("noGenres", true);
            model.addAttribute("bookGenreForCurrentBook", genresAsString);
        }

        List<Comment> comments = commentDAO.showCommentsForBook(bookDAO.showById(id));
        if (comments.isEmpty()) {
            model.addAttribute("noComments", true);
        } else {
            model.addAttribute("noComments", false);
            model.addAttribute("comments", comments);
        }
        model.addAttribute("newComment", new Comment());
        return "bookDescription";
    }

    @PostMapping("/newComment")
    public String addComment(@ModelAttribute("newComment") @Valid Comment comment,
                             @RequestParam("bookId") Integer bookId) {
        User currentUser = AuthenticationManager.getAuthenticationUser();
        comment.setUser(currentUser);
        comment.setBook(bookDAO.showById(bookId));
        commentDAO.save(comment);
        return "redirect:/library/viewBook/" + bookId;
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") Integer commentId,
                                @RequestParam("bookId") Integer bookId) {
        commentDAO.delete(commentId);
        return "redirect:/library/viewBook/" + bookId;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam("title") String searchInput, Model model) {
        model.addAttribute("searchTitle", searchInput);
        model.addAttribute("authority", AuthenticationManager.checkAuthorityAdmin());
        model.addAttribute("loggedIn", AuthenticationManager.checkAuthentication());
        model.addAttribute("findBooks", bookDAO.findByTitle(searchInput));
        return "searchBookPage";
    }

    @PostMapping("/addToWishList")
    public String addToWishList(@RequestParam("bookId") Integer bookId) {
        User currentUser = AuthenticationManager.getAuthenticationUser();
        FavoriteBooks favoriteBooks = new FavoriteBooks();
        favoriteBooks.setBook(bookDAO.showById(bookId));
        favoriteBooks.setUser(currentUser);
        favoriteBooksDAO.save(favoriteBooks);
        return "redirect:/library/viewBook/" + bookId;
    }

    @PostMapping("/deleteWishBook")
    public String deleteBookFromWishList(@RequestParam("bookId") Integer bookId) {
        User currentUser = AuthenticationManager.getAuthenticationUser();
        favoriteBooksDAO.delete(currentUser, bookDAO.showById(bookId));
        return "redirect:/library/viewBook/" + bookId;
    }
}
