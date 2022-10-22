package com.github.yuriiyukh.models;

public class Book {

    private int id;
    private int personId;
    private int year;
    private String author;
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
