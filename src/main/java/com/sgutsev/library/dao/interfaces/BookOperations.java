package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Book;

import java.util.List;

public interface BookOperations {
    List<Book> index();

    void save(Book book);

    void delete(int id);

    void update(int id, Book book);

    Book showById(int id);

    List<Book> findByTitle(String title);
}
