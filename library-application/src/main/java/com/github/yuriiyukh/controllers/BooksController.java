package com.github.yuriiyukh.controllers;

import java.util.Optional;

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
import com.github.yuriiyukh.dao.PersonDao;
import com.github.yuriiyukh.models.Book;
import com.github.yuriiyukh.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

    private static final String REDIRECT_BOOKS = "redirect:/books/";
    private static final String OWNER_KEY = "owner";
    private static final String PEOPLE_KEY = "people";
    private static final String BOOK_KEY = "book";
    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(BOOK_KEY, bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute(BOOK_KEY, bookDao.getById(id));

        Optional<Person> bookOwner = bookDao.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute(OWNER_KEY, bookOwner.get());
        } else {
            model.addAttribute(PEOPLE_KEY, personDao.index());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute(BOOK_KEY, new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute(BOOK_KEY) @Validated Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDao.save(book);
        return REDIRECT_BOOKS;
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute(BOOK_KEY, bookDao.getById(id));

        return "books/edit";

    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute(BOOK_KEY) @Valid Book book, BindingResult bindingResult,
            @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDao.update(id, book);
        return REDIRECT_BOOKS;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);

        return REDIRECT_BOOKS;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {

        bookDao.assign(id, selectedPerson);

        return REDIRECT_BOOKS + id;
    }

    @PatchMapping("/{id}/unassign")
    public String unassignOwner(@PathVariable("id") int id) {
        bookDao.unassign(id);

        return REDIRECT_BOOKS + id;
    }

}
