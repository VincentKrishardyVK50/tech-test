package com.parkee.techtest.controller;

import com.parkee.techtest.bean.BookRequestBean;
import com.parkee.techtest.bean.BookResponseBean;
import com.parkee.techtest.bean.GeneralResponseBean;
import com.parkee.techtest.service.BookService;
import com.parkee.techtest.validation.BookValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/back-office/book")
public class BookController {
    private final BookService bookService;
    private final BookValidation bookValidation;

    public BookController(BookService bookService, BookValidation bookValidation) {
        this.bookService = bookService;
        this.bookValidation = bookValidation;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponseBean> create(@RequestBody BookRequestBean bean){
        bookValidation.validateNewBook(bean);
        GeneralResponseBean<BookResponseBean> response =
                new GeneralResponseBean<>(bookService.createNewBook(bean), HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
