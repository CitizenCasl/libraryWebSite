package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.GenresOperations;
import com.sgutsev.library.models.Genres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class GenresDAO implements GenresOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public List<Genres> index() {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT g FROM Genres g";
        Query query = session.createQuery(sql);
        List<Genres> genresList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return genresList;
    }

    @Override
    public Genres getGenre(String genreName) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT g FROM Genres g WHERE nameGenre =: nameGenre";
        Query query = session.createQuery(sql).setParameter("nameGenre", genreName);
        Genres genre = (Genres) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return genre;
    }
}
