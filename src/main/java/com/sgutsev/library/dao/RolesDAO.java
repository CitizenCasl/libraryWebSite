package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.RolesOperation;
import com.sgutsev.library.models.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class RolesDAO implements RolesOperation {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public List<Roles> index() {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT r FROM Roles r";
        Query query = session.createQuery(sql);
        List<Roles> rolesList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return rolesList;
    }

    @Override
    public Roles showByName(String name) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT r FROM Roles r WHERE nameRole = :nameRole";
        Query query = session.createQuery(sql);
        query.setParameter("nameRole", name);
        Roles role = (Roles) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return role;
    }
}
