package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.LibraryOperations;
import com.sgutsev.library.models.Library;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class LibraryDAO implements LibraryOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session;

    @Override
    public List<Library> index() {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT l FROM Library l";
        Query query = session.createQuery(sql);
        List<Library> libraryList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return libraryList;
    }

    @Override
    public void save(Library library) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(library);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Library WHERE id = :id").setParameter("id", id).executeUpdate();
        session.close();
    }

    @Override
    public void update(int id, Library library) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE Library SET nameLibrary =:nameLibrary,addressLibrary =:address,phoneLibrary =:phone WHERE id = :id")
                .setParameter("id", id)
                .setParameter("nameLibrary", library.getNameLibrary())
                .setParameter("address", library.getAddressLibrary())
                .setParameter("phone", library.getPhoneLibrary())
                .executeUpdate();
        session.close();
    }
}
