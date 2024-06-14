package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.Comment;

import java.util.List;

public interface CommentOperations {
    List<Comment> showCommentsForBook(Book book);

    void save(Comment book);

    void delete(int id);

    Double getBookRating(Book book);
}
