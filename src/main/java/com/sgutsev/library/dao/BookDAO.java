package com.sgutsev.library.dao;


import com.sgutsev.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT books.id,nameauthor,namebook,yearrelease,amount FROM books,authors WHERE idauthor = authors.id",
                new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(idauthor,namebook,yearrelease,amount) VALUES (?,?,?,?)",
                authorDAO.showByName(book.getNameAuthorOfBook()).getId(), book.getNameBook(), book.getYearRelease(), book.getAmount());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE books SET idauthor = ?,namebook = ?, yearrelease = ?, amount = ? WHERE id = ?",
                authorDAO.showByName(book.getNameAuthorOfBook()).getId(), book.getNameBook(), book.getYearRelease(), book.getAmount(), id);
    }

    public Book showById(int id) {
        return jdbcTemplate.query("SELECT books.id,nameauthor,namebook,yearrelease,amount FROM books,authors WHERE idauthor = authors.id AND books.id=?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }
}
