package com.sgutsev.library.dao;

import com.sgutsev.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setNameAuthorOfBook(resultSet.getString("nameauthor"));
        book.setNameBook(resultSet.getString("namebook"));
        book.setYearRelease(resultSet.getInt("yearrelease"));
        book.setAmount(resultSet.getInt("amount"));
        return book;
    }
}