package com.github.yuriiyukh.controllers;

import javax.validation.Valid;

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

import com.github.yuriiyukh.dao.BookDao;
import com.github.yuriiyukh.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private static final String RESULT = "result";

private final BookDao bookDao;
    
    @Autowired
    public BooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("book", bookDao.index());
        return "books/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", bookDao.getById(id));
        return "books/show";
    }
    
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "books/new";
    }
    
    @PostMapping()
    public String create(@ModelAttribute("person") @Validated Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        
        bookDao.save(person);
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", bookDao.getById(id));
        
        return "books/edit";
        
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        
        bookDao.update(id, person);
        return "redirect:/books";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        
        return "redirect:/books";
    }

}
