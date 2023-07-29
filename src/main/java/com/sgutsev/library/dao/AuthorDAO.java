package com.sgutsev.library.dao;

import com.sgutsev.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> index() {
        return jdbcTemplate.query("SELECT * FROM authors", new BeanPropertyRowMapper<>(Author.class));
    }

    public void save(Author author) {
        jdbcTemplate.update("INSERT INTO authors(nameauthor) VALUES (?)", author.getNameAuthor());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id = ?", id);
    }

    public void update(int id,Author author){
        jdbcTemplate.update("UPDATE authors SET nameauthor = ? WHERE id = ?",author.getNameAuthor(),id);
    }

    public Author showById(int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Author.class))
                .stream().findAny().orElse(null);
    }

    public Author showByName(String name){
        return jdbcTemplate.query("SELECT id FROM authors WHERE nameauthor=?", new Object[]{name}, new BeanPropertyRowMapper<>(Author.class))
                .stream().findAny().orElse(null);
    }
}
