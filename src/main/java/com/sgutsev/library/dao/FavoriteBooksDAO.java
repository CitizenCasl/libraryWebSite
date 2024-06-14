package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.FavoriteBooksOperations;
import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.FavoriteBooks;
import com.sgutsev.library.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class FavoriteBooksDAO implements FavoriteBooksOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public List<FavoriteBooks> showUserFavoriteBooks(User user) {
        session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT f FROM FavoriteBooks f WHERE user =: idUser").setParameter("idUser", user);
        List<FavoriteBooks> commentsList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return commentsList;
    }

    @Override
    public void save(FavoriteBooks favoriteBook) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(favoriteBook);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(User user, Book book) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM FavoriteBooks WHERE user = :currentUser AND book = :currentBook")
                .setParameter("currentUser", user)
                .setParameter("currentBook", book)
                .executeUpdate();
        session.close();
    }

    @Override
    public boolean checkBookIsFavorite(User user, Book book) {
        session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT f FROM FavoriteBooks f WHERE user =: idUser AND book=: idBook")
                .setParameter("idUser", user)
                .setParameter("idBook", book);
        List<FavoriteBooks> commentsList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        if (commentsList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
