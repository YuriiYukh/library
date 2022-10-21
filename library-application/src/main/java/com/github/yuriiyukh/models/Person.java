package com.github.yuriiyukh.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    
    @Size(min = 2, max = 75, message = "Name should be between 2 and 75 chars")
    private String name;
    
    @Min(value = 1900, message = "Age should be above 1900")
    @Max(value = 2022, message = "Age should be lower then 2022")
    private int birthYear; 

    public Person() {
        
    }
    
    public Person(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
