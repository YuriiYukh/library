package com.github.yuriiyukh.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.yuriiyukh.models.Book;
import com.github.yuriiyukh.models.Person;

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
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[] { id }, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE books SET name = ?, author = ?, year = ? WHERE id = ?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate
                .query("SELECT Person.* FROM books JOIN person ON books.person_id = person.id WHERE books.id = ?",
                        new Object[] { id }, new PersonMapper())
                .stream().findAny();
    }

    public void assign(int id, Person person) {
        jdbcTemplate.update("UPDATE books SET person_id = ? WHERE id = ?", person.getId(), id);
        
    }

    public void unassign(int id) {
        jdbcTemplate.update("UPDATE books SET person_id = NULL WHERE id = ?", id);
        
    }

}
