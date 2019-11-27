package com.neves_eduardo.core_engineering.tema8.service;


import com.neves_eduardo.core_engineering.tema8.DAO.BookDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.LoanDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidLoanRegistryException;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.Book;
import com.neves_eduardo.core_engineering.tema8.model.Loan;
import com.neves_eduardo.core_engineering.tema8.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class LoanService {
    private static final int RETURN_DAYS = 7;
    private static final int FEE_MULTIPLIER = 5;
    private LoanDAO loanDAO;
    private BookService bookService;
    private UserService userService;
    private List<Loan> loans;

    public LoanService(BookService bookService, UserService userService, LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
        this.bookService = bookService;
        this.userService = userService;
        this.loans = loanDAO.getLoanList();
    }

    public LoanService() {
        this.loanDAO = new LoanDAO();
        this.userService = new UserService();
        this.bookService = new BookService();
        this.loans = loanDAO.getLoanList();
    }

    public Loan getLoanById(UUID loanId) {
        if (loanDAO.getLoanList().stream().noneMatch(s -> s.getId().equals(loanId)))
            throw new InvalidOperationException("This loan does not exist");
        return loanDAO.getLoanList().stream().filter(s -> s.getId().equals(loanId)).findFirst().get();
    }

    public List<Loan> getLoanList() {
        return loanDAO.getLoanList();
    }

    public Loan registerLoan(Loan loan, UUID bookId, UUID userId) {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUserById(userId);

        loan.setBook(book);
        loan.setUser(user);
        validateLoan(loan);
        book.setAvailable(false);
        bookService.updateBook(book);
        loan.setDateOfReturn(loan.getDateOfLoan().plusDays(RETURN_DAYS));
        loans.add(loan);
        loanDAO.writeLoanFile(loans);
        return loan;

    }

    public void updateLoan(Loan newLoan) {
        Loan oldLoan = getLoanById(newLoan.getId());
        newLoan.setId(oldLoan.getId());
        loans.add(loans.indexOf(oldLoan), newLoan);
        loans.remove(oldLoan);
        loanDAO.writeLoanFile(loans);
    }

    public Loan renewLoan(UUID loanId) {
        Loan loanToRenew = getLoanById(loanId);
        if (!loanToRenew.isOpen()) throw new InvalidOperationException("This loan is closed");
        if (LocalDate.now().isAfter(loanToRenew.getDateOfReturn()))
            throw new InvalidOperationException("This loan is due.");
        loanToRenew.setDateOfReturn(loanToRenew.getDateOfReturn().plusDays(7));
        updateLoan(loanToRenew);
        return loanToRenew;
    }


    public Loan returnBook(UUID loanId) {
        Loan loanToReturn = getLoanById(loanId);
        loanToReturn.setOpen(false);
        loanToReturn.getBook().setAvailable(true);
        updateLoan(loanToReturn);
        bookService.updateBook(loanToReturn.getBook());
        return loanToReturn;
    }

    public long calculateFee(UUID loanId) {
        Loan loanToCalculate = getLoanById(loanId);
        if (!LocalDate.now().isAfter(loanToCalculate.getDateOfReturn())) return 0;
        Period period = Period.between(loanToCalculate.getDateOfReturn(), LocalDate.now());
        return ((period.getDays()) * FEE_MULTIPLIER);


    }

    public int countUserTotalLoans(UUID userId) {
        return (int) loans
                .stream()
                .filter(s -> s.getUser().getId().equals(userId)).count();
    }

    public int countUserOverDueLoans(UUID userId){
        return (int) loans
                .stream()
                .filter(s-> s.isOpen() && s.getUser().getId().equals(userId) && LocalDate.now().isAfter(s.getDateOfReturn()))
                .count();
    }
    public List<Loan> getLateLoans(UUID userId){
        return  loanDAO.getLoanList().stream()
                .filter(s -> s.isOpen() && s.getUser().getId().equals(userId) && LocalDate.now().isAfter(s.getDateOfReturn()))
                .collect(toList());
    }
    public int countDaysOverDue(UUID loanId){
        Loan loan = getLoanById(loanId);
        return Period.between(loan.getDateOfReturn(), LocalDate.now()).getDays();

    }


    private void validateLoan(Loan loan) {
        if (loan.getUser() == null || loan.getBook() == null)
            throw new InvalidLoanRegistryException("You need to register an user and a book to make a loan");
        if (loan.getDateOfLoan() == null)
            throw new InvalidLoanRegistryException("You need to register a date to make a loan");
        if (loan.getBook().isAvailable() == false)
            throw new InvalidLoanRegistryException("This book is not available");
        if ((int) loans
                .stream()
                .filter(s -> s.isOpen() && s.getUser().getId().equals(loan.getUser().getId())).count() >= 5)
            throw new InvalidLoanRegistryException("One user cannot make more than five loans");

        if (loans.stream().anyMatch(s -> s.isOpen() && LocalDate.now().isAfter(s.getDateOfReturn()) && s.getUser().getId().equals(loan.getUser().getId()))) {
            throw new InvalidLoanRegistryException("User has overdue loans");
        }


    }

}
