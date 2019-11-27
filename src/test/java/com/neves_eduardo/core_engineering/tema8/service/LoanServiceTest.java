package com.neves_eduardo.core_engineering.tema8.service;

import com.neves_eduardo.core_engineering.tema8.DAO.BookDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.LoanDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidLoanRegistryException;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.Book;
import com.neves_eduardo.core_engineering.tema8.model.Loan;
import com.neves_eduardo.core_engineering.tema8.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @Mock
    private LoanDAO loanDAO;
    @Mock
    private BookDAO bookDAO;
    @Mock
    private UserDAO userDAO;
    private List users = new ArrayList();
    private List books = new ArrayList();
    private List loans = new ArrayList();
    @InjectMocks
    private LoanService loanService;
    @InjectMocks
    private BookService bookService;
    @InjectMocks
    private UserService userService;
    @Before
    public void init(){
        when(userDAO.getUserList()).thenReturn(users);
        when(bookDAO.getBookList()).thenReturn(books);
        when(loanDAO.getLoanList()).thenReturn(loans);
        userService = new UserService(userDAO);
        bookService = new BookService(bookDAO);
        loanService = new LoanService(bookService,userService,loanDAO);

    }


    @Test
    public void registerLoan() throws IOException {
        User user = new User("Mock");
        Book book = new Book("MockBOok","Mocker");
        Loan loan = new Loan(LocalDate.now());
        books.add(book);
        users.add(user);
        loanService.registerLoan(loan,book.getId(),user.getId());
        assertFalse(loans.isEmpty());
        assertEquals(loan,loanService.getLoanById(loan.getId()));

    }
    @Test (expected = InvalidLoanRegistryException.class)
    public void registerLoanCannotRegisterALoanIfUsersHasOverdueLoans() throws IOException {
        User user = new User("Mock");
        Book book = new Book("MockBOok","Mocker");
        Loan loan = new Loan(LocalDate.now());
        loan.setUser(user);
        loan.setDateOfReturn(LocalDate.now().minusDays(7));
        loans.add(loan);
        books.add(book);
        users.add(user);
        Loan loan2 = new Loan(LocalDate.now());
        loanService.registerLoan(loan2,book.getId(),user.getId());

    }
    @Test (expected = InvalidLoanRegistryException.class)
    public void registerLoanCannotRegisterALoanIfUserHasFiveLoansOrMore() throws IOException {
        User user = new User("Mock");
        Book book = new Book("MockBOok","Mocker");
        Loan loan = new Loan(LocalDate.now());
        Loan loan2 = new Loan(LocalDate.now());
        Loan loan3 = new Loan(LocalDate.now());
        Loan loan4 = new Loan(LocalDate.now());
        Loan loan5= new Loan(LocalDate.now());
        loan.setUser(user);
        loan2.setUser(user);
        loan3.setUser(user);
        loan4.setUser(user);
        loan5.setUser(user);
        loan.setDateOfReturn(LocalDate.now());
        loan2.setDateOfReturn(LocalDate.now());
        loan3.setDateOfReturn(LocalDate.now());
        loan4.setDateOfReturn(LocalDate.now());
        loan5.setDateOfReturn(LocalDate.now());
        loans.add(loan);
        loans.add(loan2);
        loans.add(loan3);
        loans.add(loan4);
        loans.add(loan5);
        books.add(book);
        users.add(user);

        loanService.registerLoan(new Loan(LocalDate.now()),book.getId(),user.getId());

    }

    @Test
    public void updateLoanShouldUpdateTheCorrectLoan(){
        Loan loan = new Loan(LocalDate.now());
        loans.add(loan);
        Loan loan2 = new Loan(LocalDate.now());
        loan2.setOpen(false);
        loan2.setId(loan.getId());
        loanService.updateLoan(loan2);
        assertFalse(loanService.getLoanById(loan.getId()).isOpen());
    }
    @Test
    public void renewLoanShouldAdd7daysToLoanReturn(){
        Loan loan = new Loan(LocalDate.now());
        User user = new User("a");
        Book book = new Book("a","a");
        loan.setUser(user);
        loan.setBook(book);
        loan.setDateOfReturn(LocalDate.now().plusDays(7));
        loans.add(loan);
        users.add(user);
        books.add(book);
        loanService.renewLoan(loan.getId());
        assertEquals(LocalDate.now().plusDays(14),loanService.getLoanById(loan.getId()).getDateOfReturn());

    }
    @Test (expected = InvalidOperationException.class)
    public void renewLoanShouldNotAllowUsersWithOverdueLoansRenew(){
        Loan loan = new Loan(LocalDate.now().minusDays(14));
        User user = new User("a");
        Book book = new Book("a","a");
        loan.setUser(user);
        loan.setBook(book);
        loan.setDateOfReturn(LocalDate.now().minusDays(7));
        loans.add(loan);
        users.add(user);
        books.add(book);
        loanService.renewLoan(loan.getId());


    }


    @Test
    public void calculateFeeShouldCalculateFeeCorrectly() {
        Loan loan = new Loan(LocalDate.now().minusDays(14));
        loan.setDateOfReturn(LocalDate.now().minusDays(7));
        loans.add(loan);
        assertEquals(35, loanService.calculateFee(loan.getId()));
    }

    @Test
    public void calculateFeeShouldReturn0toLoansThatAreNotOverdue() {
        Loan loan = new Loan(LocalDate.now());
        loan.setDateOfReturn(LocalDate.now().plusDays(7));
        loans.add(loan);
        assertEquals(0, loanService.calculateFee(loan.getId()));
    }

    @Test
    public void returnBookShouldChangeLoanOpenStatus() {
        Loan loan = new Loan(LocalDate.now());
        Book book = new Book("a", "a");
        loan.setBook(book);
        loans.add(loan);
        books.add(book);
        loanService.returnBook(loan.getId());
        assertFalse(loanService.getLoanById(loan.getId()).isOpen());

    }    @Test
    public void returnBookShouldChangeBookStatus() {
        Loan loan = new Loan(LocalDate.now());
        Book book = new Book("a", "a");
        book.setAvailable(false);
        loan.setBook(book);
        loans.add(loan);
        books.add(book);
        loanService.returnBook(loan.getId());
        assertTrue(bookService.getBookById(book.getId()).isAvailable());

    }


}