package com.sgutsev.library.dao;

import com.sgutsev.library.dao.interfaces.BooksGenresOperations;
import com.sgutsev.library.models.Book;
import com.sgutsev.library.models.BooksGenres;
import com.sgutsev.library.models.Genres;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BooksGenresDAO implements BooksGenresOperations {

    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    private Session session;

    @Override
    public List<Genres> getGenresForBook(Book book) {
        session = factory.openSession();
        session.beginTransaction();
        String sql = "SELECT b FROM BooksGenres b WHERE book =:currentBook";
        Query query = session.createQuery(sql).setParameter("currentBook", book);
        List<BooksGenres> genresForBookList = query.getResultList();
        List<Genres> bookGenres = genresForBookList.stream().map(BooksGenres::getGenre).collect(Collectors.toList());
        session.getTransaction().commit();
        session.close();
        return bookGenres;
    }

    @Override
    public void save(Book book, Genres genre) {
        BooksGenres booksGenres = new BooksGenres();
        booksGenres.setBook(book);
        booksGenres.setGenre(genre);
        session = factory.openSession();
        session.beginTransaction();
        session.save(booksGenres);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        BookDAO bookDAO = new BookDAO();
        session = factory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM BooksGenres WHERE book = :book").setParameter("book", bookDAO.showById(id)).executeUpdate();
        session.close();
    }
}
