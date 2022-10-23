package com.github.yuriiyukh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.yuriiyukh.models.Book;
import com.github.yuriiyukh.models.Person;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person getById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[] { id }, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public Person getByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name = ?", new Object[] { name }, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES (?, ?)", person.getName(),
                person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, birth_year = ? WHERE id = ?",
                updatedPerson.getName(), updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
    
    public List<Book> getAssignedBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE person_id = ?", new Object[] { id }, new BookMapper());
    }
}
