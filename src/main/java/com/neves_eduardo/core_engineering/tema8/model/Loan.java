package com.neves_eduardo.core_engineering.tema8.model;


import java.time.LocalDate;
import java.util.UUID;


public class Loan {
    private UUID id;
    private LocalDate dateOfLoan;
    private LocalDate dateOfReturn;
    private User user;
    private Book book;
    private boolean open= true;



    public Loan(LocalDate dateOfLoan) {
        this.id = UUID.randomUUID();
        this.dateOfLoan = dateOfLoan;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDateOfLoan() {
        return dateOfLoan;
    }

    public void setDateOfLoan(LocalDate dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


}
