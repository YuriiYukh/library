package com.github.yuriiyukh.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Book {

    private int id;
    private int personId;
    
    @Min(value = -1, message = "Year should be above 0")
    @Max(value = 2030, message = "Year should be less than 2030")
    private int year;
    
    @Size(min = 1, max = 200, message = "Author name length should be between 200 characters")
    private String author;
    
    @Size(min = 1, max = 200, message = "Book name length should be between 200 characters")
    private String name;
    
    public Book(int id, int personId, int year, String author, String name) {
        this.id = id;
        this.personId = personId;
        this.year = year;
        this.author = author;
        this.name = name;
    }

    public Book() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
