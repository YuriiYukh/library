package com.github.yuriiyukh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.yuriiyukh.dao.PersonDao;
import com.github.yuriiyukh.models.Person;
import com.github.yuriiyukh.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    
    private static final String REDIRECT_TO_PEOPLE = "redirect:/people";
    private static final String BOOKS_KEY = "books";
    private static final String PERSON_KEY = "person";
    private static final String PEOPLE_KEY = "people";
    private final PersonDao personDao;
    private final PersonValidator personValidator;
    
    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }
    
    @GetMapping()
    public String index(Model model) {
        model.addAttribute(PEOPLE_KEY, personDao.index());
        return "people/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute(PERSON_KEY, personDao.getById(id));
        model.addAttribute(BOOKS_KEY, personDao.getAssignedBooks(id));
        return "people/show";
    }
    
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute(PERSON_KEY, new Person());
        return "people/new";
    }
    
    @PostMapping()
    public String create(@ModelAttribute(PERSON_KEY) @Validated Person person, BindingResult bindingResult) {
        
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        
        personDao.save(person);
        return REDIRECT_TO_PEOPLE;
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute(PERSON_KEY, personDao.getById(id));
        
        return "people/edit";
        
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(PERSON_KEY) @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        
        personDao.update(id, person);
        return REDIRECT_TO_PEOPLE;
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        
        return REDIRECT_TO_PEOPLE;
    }

}
