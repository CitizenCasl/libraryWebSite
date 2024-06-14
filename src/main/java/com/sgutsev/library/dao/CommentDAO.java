package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.CommentOperations;
import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class CommentDAO implements CommentOperations {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public List<Comment> showCommentsForBook(Book book) {
        session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT c FROM Comment c WHERE book =: idBook").setParameter("idBook", book);
        List<Comment> commentsList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return commentsList;
    }

    @Override
    public void save(Comment comment) {
        session = factory.openSession();
        session.beginTransaction();
        session.save(comment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Comment WHERE id = :id").setParameter("id", id).executeUpdate();
        session.close();
    }

    @Override
    public Double getBookRating(Book book) {
        session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT c.grade FROM Comment c WHERE book =: idBook").setParameter("idBook", book);
        List<Integer> gradesList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        if (gradesList.isEmpty()) {
            return Double.parseDouble("0");
        } else {
            return gradesList.parallelStream().mapToInt(Integer::intValue).average().getAsDouble();
        }
    }
}
