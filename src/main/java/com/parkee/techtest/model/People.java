package com.parkee.techtest.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nik;

    private String name;

    private String email;

    private boolean deleted;

    @OneToMany(mappedBy = "people")
    private List<LoanHistory> loanHistoryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<LoanHistory> getLoanHistoryList() {
        return loanHistoryList;
    }

    public void setLoanHistoryList(List<LoanHistory> loanHistoryList) {
        this.loanHistoryList = loanHistoryList;
    }
}
