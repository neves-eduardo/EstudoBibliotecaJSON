package com.neves_eduardo.core_engineering.tema8.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.neves_eduardo.core_engineering.tema8.exceptions.DataAccessException;
import com.neves_eduardo.core_engineering.tema8.model.Book;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookDAO {
    private static final String DIRECTORY = "data/books/";
    private static final String FORMAT = ".json";
    private static final String FILENAME = "books";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void writeBookFile(List<Book> books)  {
        try(FileWriter writer = new FileWriter(DIRECTORY + FILENAME + FORMAT)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            throw new DataAccessException("Error accessing Books.json");
        }
    }
    public List<Book> getBookList() {
        List<Book> booksToReturn = new ArrayList<>();

        try (Reader reader = new FileReader(DIRECTORY + FILENAME + FORMAT)){
            Type listType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            booksToReturn = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booksToReturn;
    }


}
