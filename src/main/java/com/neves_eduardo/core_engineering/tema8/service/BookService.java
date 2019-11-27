package com.neves_eduardo.core_engineering.tema8.service;

import com.neves_eduardo.core_engineering.tema8.DAO.BookDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookService {
    private BookDAO bookDAO;
    private List<Book> books;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
        this.books = bookDAO.getBookList();
    }

    public BookService() {
        this.bookDAO = new BookDAO();
        this.books = bookDAO.getBookList();
    }

    public Book registerBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null) {
            throw new InvalidOperationException("Book title and author are obligatory fields");
        }
        books.add(book);
        bookDAO.writeBookFile(books);
        return book;
    }


    public void deleteBook(UUID id) {
        if (books.stream().noneMatch(s -> s.getId().equals(id)))
            throw new InvalidOperationException("This Book does not exist");

        Book bookToDelete = books.stream().filter(s -> s.getId().equals(id)).findFirst().get();

        if (!bookToDelete.isAvailable()) {
            throw new InvalidOperationException("This book is not available.");
        }
        books.remove(bookToDelete);
        bookDAO.writeBookFile(books);


    }


    public Book getBookById(UUID id) {
        if (books.stream().noneMatch(s -> s.getId().equals(id)))
            throw new InvalidOperationException("This book does not exist");
        return books.stream().filter(s -> s.getId().equals(id)).findFirst().get();
    }

    public List<Book> getBookByAuthor(String author) {
        return books.stream().filter(s -> s.getAuthor().equals(author)).collect(Collectors.toList());
    }

    public List<Book> getBookByTitle(String name) {
        return books.stream().filter(s -> s.getTitle().equals(name)).collect(Collectors.toList());
    }

    public void updateBook(Book newBook) {
        Book oldBook = getBookById(newBook.getId());
        newBook.setId(oldBook.getId());
        books.add(books.indexOf(oldBook), newBook);
        books.remove(oldBook);
        bookDAO.writeBookFile(books);
    }


    public List<Book> getBookList() { return bookDAO.getBookList();
    }
}
