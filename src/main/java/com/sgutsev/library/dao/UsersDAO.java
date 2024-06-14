package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.UserOperations;
import com.sgutsev.library.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class UsersDAO implements UserOperations {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public User findByLoginUser(String userLogin) throws NoResultException {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT u FROM User u WHERE loginUser = :login";
        Query query = session.createQuery(sql);
        query.setParameter("login", userLogin);
        User currentUser = null;
        try {
            currentUser = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return currentUser;
        }
        session.getTransaction().commit();
        session.close();
        return currentUser;
    }

    @Override
    public void save(User user) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateInfo(int id, User user) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE User SET nameUser=:name,loginUser=:login,numberOfCard=:numberOfCard,phoneUser=:phone WHERE id = :id")
                .setParameter("id", id)
                .setParameter("name", user.getNameUser())
                .setParameter("login", user.getLoginUser())
                .setParameter("numberOfCard", user.getNumberOfCard())
                .setParameter("phone", user.getPhoneUser())
                .executeUpdate();
        session.close();
    }

    @Override
    public void updatePassword(int id, User user) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("UPDATE User SET password=:password WHERE id = :id")
                .setParameter("id", id)
                .setParameter("password", user.getPassword())
                .executeUpdate();
        session.close();
    }
}
