package com.neves_eduardo.core_engineering.tema8.main;

import com.neves_eduardo.core_engineering.tema8.DAO.BookDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.LoanDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.controller.LibraryControl;
import com.neves_eduardo.core_engineering.tema8.model.Book;
import com.neves_eduardo.core_engineering.tema8.model.Loan;
import com.neves_eduardo.core_engineering.tema8.model.User;
import com.neves_eduardo.core_engineering.tema8.service.BookService;
import com.neves_eduardo.core_engineering.tema8.service.LoanService;
import com.neves_eduardo.core_engineering.tema8.service.UserService;


import java.io.IOException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) throws IOException {
        Book book1 = new Book("Lord of The Rings: Fellowship of the Ring","Tolkien");
        Book book2 = new Book("Lord of The Rings: The Two Towers","Tolkien");
        Book book3 = new Book("Lord of The Rings: Return of the King","Tolkien");
        Book book4 = new Book("The Hobbit","Tolkien");
        Book book5 = new Book("Silmarillion","Tolkien");
        Book book6 = new Book("The Republic","Plato");
        Book book7 = new Book("Inferno","Dante");
        Book book8 = new Book("Politics","Aristotle");
        Book book9 = new Book("Memórias Póstumas de Bras Cubas","Machado de Assis");
        Book book10 = new Book("Dom Casmurro","Machado de Assis");
        Book book11 = new Book("O Alienista","Machado de Assis");
        Book book12 = new Book("O Triste Fim de Policarpo Quaresma","Lima Barreto");
        Book book13 = new Book("The Prince","Maquiaveli");
        Book book14 = new Book("Lord of The Flies","William Golding");
        BookService bookService = new BookService();
        bookService.registerBook(book1);
        bookService.registerBook(book2);
        bookService.registerBook(book3);
        bookService.registerBook(book4);
        bookService.registerBook(book5);
        bookService.registerBook(book6);
        bookService.registerBook(book7);
        bookService.registerBook(book8);
        bookService.registerBook(book9);
        bookService.registerBook(book10);
        bookService.registerBook(book11);
        bookService.registerBook(book12);
        bookService.registerBook(book13);
        bookService.registerBook(book14);


        User user1 = new User("João");
        User user2 = new User("Maria");
        User user3 = new User ("Francis");
        User user4 = new User("Sebastian");
        UserService userService= new UserService();
        userService.registerUser(user1);
        userService.registerUser(user2);
        userService.registerUser(user3);
        userService.registerUser(user4);


        Loan loan1 = new Loan(LocalDate.now());
        Loan loan2 = new Loan(LocalDate.now());
        Loan loan3 = new Loan(LocalDate.now());
        Loan loan4 = new Loan(LocalDate.now());
        Loan loan5 = new Loan(LocalDate.now().minusDays(21));
        Loan loan6 = new Loan(LocalDate.now());
        Loan loan7 = new Loan(LocalDate.now());
        Loan loan8 = new Loan(LocalDate.now());
        Loan loan9 = new Loan(LocalDate.now());
        Loan loan10 = new Loan(LocalDate.now());
        Loan loan11 = new Loan(LocalDate.now());
        Loan loan12 = new Loan(LocalDate.now());
        Loan loan13 = new Loan(LocalDate.now());
        Loan loan14 = new Loan(LocalDate.now());
        LoanService loanService = new LoanService();
        loanService.registerLoan(loan1,book1.getId(),user1.getId());
        loanService.registerLoan(loan2,book2.getId(),user1.getId());
        loanService.registerLoan(loan3,book3.getId(),user1.getId());
        loanService.registerLoan(loan4,book4.getId(),user1.getId());
        loanService.registerLoan(loan5,book5.getId(),user1.getId());
        loanService.registerLoan(loan6,book6.getId(),user2.getId());
        loanService.registerLoan(loan7,book7.getId(),user2.getId());
        loanService.registerLoan(loan8,book8.getId(),user3.getId());
        loanService.registerLoan(loan9,book9.getId(),user3.getId());
        loanService.registerLoan(loan10,book10.getId(),user3.getId());
        loanService.registerLoan(loan11,book11.getId(),user3.getId());
        loanService.registerLoan(loan12,book12.getId(),user4.getId());
        loanService.registerLoan(loan13,book13.getId(),user4.getId());
        loanService.registerLoan(loan14,book14.getId(),user4.getId());
        LibraryControl libraryControl = new LibraryControl();
        libraryControl.showTop10Users();
        libraryControl.booksAndUserReport();
        libraryControl.usersWithDueLoansReport();








    }
}
