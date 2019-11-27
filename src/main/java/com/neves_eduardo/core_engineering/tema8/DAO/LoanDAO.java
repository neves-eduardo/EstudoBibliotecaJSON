package com.neves_eduardo.core_engineering.tema8.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.neves_eduardo.core_engineering.tema8.exceptions.DataAccessException;
import com.neves_eduardo.core_engineering.tema8.model.Loan;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoanDAO {
    private static final String DIRECTORY = "data/loans/";
    private static final String FORMAT = ".json";
    private static final String FILENAME = "loans";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void writeLoanFile(List<Loan> loans){
        try(FileWriter writer = new FileWriter(DIRECTORY + FILENAME + FORMAT)) {
            gson.toJson(loans, writer);
        } catch (IOException e) {
            throw new DataAccessException("Error accessing Loans.json");
        }
    }
    public List<Loan> getLoanList() {
        List<Loan> loansToReturn = new ArrayList<>();

        try (Reader reader = new FileReader(DIRECTORY + FILENAME + FORMAT)){
            Type listType = new TypeToken<ArrayList<Loan>>() {
            }.getType();
            loansToReturn = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loansToReturn;
    }
}
