package com.parkee.techtest.service;

import com.parkee.techtest.bean.LoanRequestBean;
import com.parkee.techtest.constant.StatusConstants;
import com.parkee.techtest.mapper.LoanMapper;
import com.parkee.techtest.model.Book;
import com.parkee.techtest.model.LoanHistory;
import com.parkee.techtest.model.People;
import com.parkee.techtest.projection.LoanHistoryProjection;
import com.parkee.techtest.repository.BookRepository;
import com.parkee.techtest.repository.LoanHistoryRepository;
import com.parkee.techtest.repository.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LoanService {
    private final LoanHistoryRepository loanHistoryRepository;
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanHistoryRepository loanHistoryRepository, BookRepository bookRepository, PeopleRepository peopleRepository, LoanMapper loanMapper) {
        this.loanHistoryRepository = loanHistoryRepository;
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional
    public void processLoanBook(LoanRequestBean bean) {
        // validate loanDateStart and expected return date no more than 30 days
        long loanDuration = ChronoUnit.DAYS.between(bean.getLoanDate(), bean.getReturnDate());
        if (loanDuration > 30) {
            throw new RuntimeException("Durasi peminjaman buku tidak boleh lebih dari 30 hari!");
        }

        // validate stock of current book which want to loan
        Book choosenBook = bookRepository.findById(bean.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Buku tidak ditemukan!"));  // in case there's no book on entity
        if (choosenBook.getStock() == 0) {
            throw new RuntimeException("Stok buku habis!");
        }

        // validate people if still have loan book
        loanHistoryRepository.findByPeopleId(bean.getPeopleId()).ifPresent(entity -> {
            throw new RuntimeException("Masih ada buku yang belum dikembalikan!");
        });

        People currPeople = peopleRepository.findById(bean.getPeopleId())
                .orElseThrow(() -> new EntityNotFoundException("Peminjam tidak ditemukan!")); // in case there's no people on entity

        // write loan book data into entity
        LoanHistory loanHistory = loanMapper.toEntity(bean);
        loanHistory.setBook(choosenBook);
        loanHistory.setPeople(currPeople);
        loanHistoryRepository.save(loanHistory);

        // update current stock of book
        choosenBook.setStock(choosenBook.getStock() - 1);
        bookRepository.save(choosenBook);
    }

    @Transactional
    public void processReturnBook(long loanId) {
        // find loan data by id
        LoanHistory currLoan = loanHistoryRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Data tidak ditemukan!")); // in case there's no loan data on entity

        // update entity by set actual return date and return status (late or on-time)
        currLoan.setReturnDate(LocalDate.now());
        currLoan.setStatus(LocalDate.now().isAfter(currLoan.getLoanDateEnd()) ?
                StatusConstants.RETURNED_LATE : StatusConstants.RETURNED); // in actual return date is after expected return date, it will set status to be late
        loanHistoryRepository.save(currLoan);

        // update stock of book after returned
        Book returnedBook = currLoan.getBook();
        returnedBook.setStock(returnedBook.getStock() + 1);
        bookRepository.save(returnedBook);
    }

    public Page<LoanHistoryProjection> getListLoanHistory(Pageable pageable, String keyword) {
        return loanHistoryRepository.findListLoanHistory(pageable, keyword);
    }
}
