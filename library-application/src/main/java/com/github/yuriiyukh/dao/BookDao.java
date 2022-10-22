package com.github.yuriiyukh.dao;

import java.util.List;

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
        return jdbcTemplate.query("SELECT * FROM person WHERE book_id = ?", new Object[] { id }, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name,birth_year) VALUES (?, ?)", person.getName(),
                person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, birth_year = ? WHERE person_id = ?",
                updatedPerson.getName(), updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

}
