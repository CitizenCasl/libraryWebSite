package com.sgutsev.library.dao;


import com.sgutsev.library.dao.interfaces.BookOperations;
import com.sgutsev.library.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class BookDAO implements BookOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session;

    public List<Book> index() {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT b FROM Book b ORDER BY nameBook";
        Query query = session.createQuery(sql);
        List<Book> booksList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return booksList;
    }

    public void save(Book book) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Book WHERE id = :id").setParameter("id", id).executeUpdate();
        session.close();
    }

    public void update(int id, Book book) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE Book SET nameBook =:nameBook,nameAuthorOfBook =: author,yearRelease =:yearRelease,cover=:cover,description =:description WHERE id = :id")
                .setParameter("id", id)
                .setParameter("nameBook", book.getNameBook())
                .setParameter("author", book.getNameAuthorOfBook())
                .setParameter("yearRelease", book.getYearRelease())
                .setParameter("description", book.getDescription())
                .setParameter("cover", book.getCover())
                .executeUpdate();
        session.close();
    }

    public Book showById(int id) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT b FROM Book b WHERE id = :id";
        Query query = session.createQuery(sql);
        query.setParameter("id", id);
        Book book = (Book) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return book;
    }

    @Override
    public List<Book> findByTitle(String title) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT b FROM Book b WHERE lower(nameBook) LIKE :titleFragment ORDER BY nameBook";
        Query query = session.createQuery(sql);
        query.setParameter("titleFragment", "%" + title.toLowerCase() + "%");
        List<Book> booksList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return booksList;
    }
}
