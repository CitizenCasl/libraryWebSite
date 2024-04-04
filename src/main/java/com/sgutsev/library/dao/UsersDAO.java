package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.UserOperations;
import com.sgutsev.library.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class UsersDAO implements UserOperations {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public Optional<User> findByLoginUser(String userLogin) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT u FROM User u WHERE loginUser = :login";
        Query query = session.createQuery(sql);
        query.setParameter("login", userLogin);
        List<User> currentUser = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return currentUser.isEmpty() ? Optional.empty() : Optional.of(currentUser.get(0));
    }
}
