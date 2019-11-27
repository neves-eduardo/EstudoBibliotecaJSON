package com.neves_eduardo.core_engineering.tema8.service;

import com.neves_eduardo.core_engineering.tema8.DAO.BookDAO;
import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    @Mock
    BookDAO bookDAO;
    @InjectMocks
    private BookService bookService = new BookService();
    ArrayList<Book> books = new ArrayList<>();
    @Before
    public void init(){
        when(bookDAO.getBookList()).thenReturn(books);
        bookService = new BookService(bookDAO);
    }
    @Test
    public void registerBookShouldRegisterTheBookCorrectly() {
        Book book = new Book("Jooj","Joorj");
        bookService.registerBook(book);
        assertEquals(bookDAO.getBookList().size(),1);
        assertTrue(bookDAO.getBookList().contains(book));
        verify(bookDAO).writeBookFile(books);
    }
    @Test (expected = InvalidOperationException.class)
    public void registerBookShouldNotRegisterBookWithNullTitle() {
        Book book = new Book(null,"Joorj");
        bookService.registerBook(book);
        assertTrue(books.isEmpty());
        assertFalse(bookDAO.getBookList().contains(book));
        verify(bookDAO).writeBookFile(books);
    }

    @Test (expected = InvalidOperationException.class)
    public void registerBookShouldNotRegisterBookWithNullAuthor() {
        Book book = new Book("Jooj",null);
        bookService.registerBook(book);
        assertTrue(books.isEmpty());
        assertFalse(bookDAO.getBookList().contains(book));
        verify(bookDAO).writeBookFile(books);
    }
    @Test
    public void deleteBookShouldDeleteTheBookCorrectly(){
        Book book = new Book("Mock","MockGyvver");
        books.add(book);
        bookService.deleteBook(book.getId());
        assertTrue(bookDAO.getBookList().isEmpty());
    }

    @Test(expected = InvalidOperationException.class)
    public void deleteBookShouldNotDeleteUnavailableBook(){
        Book book = new Book("Mock","MockGyvver");
        book.setAvailable(false);
        books.add(book);
        bookService.deleteBook(book.getId());
        assertTrue(bookDAO.getBookList().isEmpty());
    }

    @Test
    public void updateBookShouldUpdateTheBookCorrectly() {
        Book book = new Book ("Change me", "Mock");
        books.add(book);
        Book book2 = new Book("Changed","Mock");
        book2.setId(book.getId());
        bookService.updateBook(book2);
        assertEquals("Changed", bookService.getBookById(book.getId()).getTitle());
    }

}