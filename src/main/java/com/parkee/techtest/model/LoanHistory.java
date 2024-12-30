package com.parkee.techtest.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "people_id")
    private People people;

    private LocalDate loanDateStart;

    private LocalDate loanDateEnd;
    private LocalDate returnDate;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public LocalDate getLoanDateStart() {
        return loanDateStart;
    }

    public void setLoanDateStart(LocalDate loanDateStart) {
        this.loanDateStart = loanDateStart;
    }

    public LocalDate getLoanDateEnd() {
        return loanDateEnd;
    }

    public void setLoanDateEnd(LocalDate loanDateEnd) {
        this.loanDateEnd = loanDateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
