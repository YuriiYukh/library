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
import com.github.yuriiyukh.models.Book;

@Controller
@RequestMapping("/books")
public class BooksController {

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
        model.addAttribute("book", bookDao.getById(id));
        return "books/show";
    }
    
    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    
    @PostMapping()
    public String create(@ModelAttribute("book") @Validated Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        
        bookDao.save(book);
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.getById(id));
        
        return "books/edit";
        
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        
        bookDao.update(id, book);
        return "redirect:/books";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        
        return "redirect:/books";
    }

}
