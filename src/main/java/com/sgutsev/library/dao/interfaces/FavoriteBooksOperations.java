package com.sgutsev.library.dao.interfaces;

import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.FavoriteBooks;
import com.sgutsev.library.models.User;

import java.util.List;

public interface FavoriteBooksOperations {
    List<FavoriteBooks> showUserFavoriteBooks(User user);

    void save(FavoriteBooks favoriteBook);

    void delete(User user, Book book);

    boolean checkBookIsFavorite(User user,Book book);
}
