package com.parkee.techtest.service;

import com.parkee.techtest.bean.BookRequestBean;
import com.parkee.techtest.bean.BookResponseBean;
import com.parkee.techtest.mapper.BookMapper;
import com.parkee.techtest.model.Book;
import com.parkee.techtest.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponseBean createNewBook(BookRequestBean request) {
        Book book = bookMapper.toEntity(request); // mapping using mapstruct instead set each attributes manually
        book = bookRepository.save(book);
        return bookMapper.toBean(book);
    }

    /**
     * get list of book which can filter by isbn or title book based on given keyword
     */
    public Page<BookResponseBean> getListBook(Pageable pageable, String keyword) {
        return bookRepository
                .findBooksByKeyword(pageable, keyword)
                .map(bookMapper::toBean);
    }
}
