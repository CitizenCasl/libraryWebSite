package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.Genres;

import java.util.List;

public interface BooksGenresOperations {

    List<Genres> getGenresForBook(Book book);

    void save(Book book, Genres genre);

    void delete(Integer id);

}
