package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.AuthorOperations;
import com.sgutsev.library.models.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class AuthorDAO implements AuthorOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session;

    public List<Author> index() {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT a FROM Author a ORDER BY nameAuthor";
        Query query = session.createQuery(sql);
        List<Author> authorsList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return authorsList;
    }

    public void save(Author author) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(author);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Author WHERE id = :id").setParameter("id", id).executeUpdate();
        session.close();
    }

    public void update(int id, Author author) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE Author SET nameAuthor =:nameAuthor,biography =:biography,photo=:photo WHERE id = :id")
                .setParameter("id", id)
                .setParameter("nameAuthor", author.getNameAuthor())
                .setParameter("biography", author.getBiography())
                .setParameter("photo", author.getPhoto())
                .executeUpdate();
        session.close();
    }

    public Author showById(int id) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT a FROM Author a WHERE id = :id";
        Query query = session.createQuery(sql);
        query.setParameter("id", id);
        Author author = (Author) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return author;
    }

    public Author showByName(String name) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT a FROM Author a WHERE nameAuthor = :nameAuthor";
        Query query = session.createQuery(sql);
        query.setParameter("nameAuthor", name);
        Author author = (Author) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return author;
    }

}
