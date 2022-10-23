package com.github.yuriiyukh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.yuriiyukh.models.Book;

public class BookMapper implements RowMapper<Book>{

    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setPersonId(resultSet.getInt("person_id"));
        book.setYear(resultSet.getInt("year"));
        book.setAuthor(resultSet.getString("author"));
        book.setName(resultSet.getString("name"));
        return book;
    }

}
