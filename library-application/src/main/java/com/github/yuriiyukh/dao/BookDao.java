package com.github.yuriiyukh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.yuriiyukh.models.Book;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Books", new BookMapper());
    }

    public Book getById(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id = ?", new Object[] { id }, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE books SET name = ?, author = ?, year = ? WHERE book_id = ?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE book_id = ?", id);
    }

}
