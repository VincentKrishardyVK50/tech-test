package com.parkee.techtest.validation;

import com.parkee.techtest.bean.BookRequestBean;
import com.parkee.techtest.repository.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BookValidation {
    private final BookRepository bookRepository;

    public BookValidation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validateNewBook(BookRequestBean bean) {
        validateTitleBook(bean.getBookTitle());
        validateIsbnNumber(bean.getIsbnNumber());
        validateStockNumber(bean.getStock());
    }

    public void validateUpdatedBook(BookRequestBean bean) {
        if(bean.getId() == 0) {
            throw new RuntimeException("ID buku harus diisi!");
        }
        validateTitleBook(bean.getBookTitle());
        validateUpdatedIsbnNumber(bean.getIsbnNumber(), bean.getId());
        validateStockNumber(bean.getStock());
    }

    private void validateTitleBook(String title) {
        // check request attribute must not be empty
        if (StringUtils.isBlank(title)) {
            throw new RuntimeException("Judul harus diisi!");
        }
    }

    private void validateIsbnNumber(String isbn){
        // check request attribute must not be empty
        if (StringUtils.isBlank(isbn)) {
            throw new RuntimeException("Kode ISBN harus diisi!");
        }
        // check length of isbn (must be 10 or 13, source: https://id.wikipedia.org/wiki/ISBN)
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new RuntimeException("Kode ISBN harus terdiri dari 10 atau 13 huruf!");
        }
        // check if isbn number already exists in database (isbn value always unique, source: https://id.wikipedia.org/wiki/ISBN)
        if (bookRepository.findByIsbnNumberAndDeletedFalse(isbn).isPresent()) {
            throw new RuntimeException("Kode ISBN sudah terdaftar!");
        }
    }

    private void validateUpdatedIsbnNumber(String isbn, long id){
        // check request attribute must not be empty
        if (StringUtils.isBlank(isbn)) {
            throw new RuntimeException("Kode ISBN harus diisi!");
        }
        // check length of isbn (must be 10 or 13)
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new RuntimeException("Kode ISBN harus terdiri dari 10 atau 13 huruf!");
        }
        // check if isbn number already exists in database and using in other books
        if (bookRepository.findByIsbnNumberAndIdNotAndDeletedFalse(isbn, id).isPresent()) {
            throw new RuntimeException("Kode ISBN sudah terdaftar!");
        }
    }

    private void validateStockNumber(long stock){
        if (stock < 0) {  // this value cannot be less than 0
            throw new RuntimeException("Stok buku harus diisi atau tidak boleh kurang dari 0!");
        }
    }
}
