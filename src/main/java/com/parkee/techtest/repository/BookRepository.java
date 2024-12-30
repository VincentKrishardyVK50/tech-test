package com.parkee.techtest.repository;

import com.parkee.techtest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
    Optional<Book> findByIsbnNumberAndDeletedFalse(String isbn);
    Optional<Book> findByIsbnNumberAndIdNotAndDeletedFalse(String isbn, long id);


    @Query(nativeQuery = true, value =
            "SELECT * FROM book b " +
            "WHERE ( :keyword = '' OR b.book_title LIKE CONCAT('%', :keyword, '%') " +
            "   OR b.isbn_number LIKE CONCAT('%', :keyword, '%') )" +
            "   AND b.deleted = false ")
    Page<Book> findBooksByKeyword(Pageable pageable, String keyword);
}
