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

    private LocalDate loadDateStart;

    private LocalDate loadDateEnd;

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

    public LocalDate getLoadDateStart() {
        return loadDateStart;
    }

    public void setLoadDateStart(LocalDate loadDateStart) {
        this.loadDateStart = loadDateStart;
    }

    public LocalDate getLoadDateEnd() {
        return loadDateEnd;
    }

    public void setLoadDateEnd(LocalDate loadDateEnd) {
        this.loadDateEnd = loadDateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
