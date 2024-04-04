package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Author;

import java.util.List;

public interface AuthorOperations {
    List<Author> index();
    void save(Author author);
    void delete(int id);
    void update(int id,Author author);
    Author showById(int id);
    Author showByName(String name);
}
