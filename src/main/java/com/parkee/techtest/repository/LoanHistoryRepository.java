package com.parkee.techtest.repository;

import com.parkee.techtest.model.LoanHistory;
import com.parkee.techtest.projection.LoanHistoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM loan_history lh " +
            "WHERE people_id = :peopleId AND status != 'LOANED' " +
            "ORDER BY lh.id DESC " +
            "LIMIT 1")
    Optional<LoanHistory> findByPeopleId(long peopleId);

    @Query(nativeQuery = true, value =
            "SELECT lh.id as id, p.name as peopleName, b.book_title as titleBook, " +
            "   lh.loan_date_start as loanDate, lh.return_date as returnDate lh.status as status " +
            "FROM loan_history lh " +
            "   INNER JOIN people p ON lh.people_id = p.id " +
            "   INNER JOIN book b ON lh.book_id = b.id " +
            "WHERE :keyword = '' OR b.book_title LIKE CONCAT('%', :keyword, '%') " +
            "   OR p.name LIKE CONCAT('%', :keyword, '%')")
    Page<LoanHistoryProjection> findListLoanHistory(Pageable pageable, String keyword);
}
