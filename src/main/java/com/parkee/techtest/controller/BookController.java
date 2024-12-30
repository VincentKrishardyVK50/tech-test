package com.parkee.techtest.controller;

import com.parkee.techtest.bean.BookRequestBean;
import com.parkee.techtest.bean.BookResponseBean;
import com.parkee.techtest.bean.GeneralResponseBean;
import com.parkee.techtest.bean.PeopleResponseBean;
import com.parkee.techtest.service.BookService;
import com.parkee.techtest.validation.BookValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/back-office/book")
public class BookController {
    private final BookService bookService;
    private final BookValidation bookValidation;

    public BookController(BookService bookService, BookValidation bookValidation) {
        this.bookService = bookService;
        this.bookValidation = bookValidation;
    }

    /**
     * endpoint for create book into database
     * @param bean which contains book title, isbn number, and stock
     * @return only information about created book
     */
    @PostMapping("/create")
    public ResponseEntity<GeneralResponseBean> create(@RequestBody BookRequestBean bean){
        bookValidation.validateNewBook(bean);
        GeneralResponseBean<BookResponseBean> response =
                new GeneralResponseBean<>(bookService.createNewBook(bean), HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * endpoint for listing of created book
     * @param pageable
     * @param keyword
     * @return list of books
     */
    @GetMapping("")
    public ResponseEntity<GeneralResponseBean> list(Pageable pageable,
                                                       @RequestParam(required = false, defaultValue = "") String keyword) {
        GeneralResponseBean<Page<BookResponseBean>> response =
                new GeneralResponseBean<>(bookService.getListBook(pageable, keyword), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for detail of book based on given id
     * @param id
     * @return detail information of book
     */
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponseBean> detail(@PathVariable long id) {
        GeneralResponseBean<BookResponseBean> response =
                new GeneralResponseBean<>(bookService.getDetailBook(id), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for updating book information
     * @param bean
     * @return only information about updated book
     */
    @PutMapping("/update")
    public ResponseEntity<GeneralResponseBean> update(@RequestBody BookRequestBean bean){
        bookValidation.validateUpdatedBook(bean);
        GeneralResponseBean<BookResponseBean> response =
                new GeneralResponseBean<>(bookService.updateCurrentBook(bean), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * endpoint for deleting book by given id
     * @param id
     * @return success message only
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponseBean> delete(@PathVariable long id){
        bookService.deleteBook(id);
        GeneralResponseBean<Void> response = new GeneralResponseBean<>( null , HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
