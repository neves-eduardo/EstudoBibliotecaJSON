package com.neves_eduardo.core_engineering.tema8.DAO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.neves_eduardo.core_engineering.tema8.exceptions.DataAccessException;
import com.neves_eduardo.core_engineering.tema8.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDAO {
    private static final String DIRECTORY = "data/users/";
    private static final String FORMAT = ".json";
    private static final String FILENAME = "users";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<User> writeUserFile(List<User> users)  {
        try(FileWriter writer = new FileWriter(DIRECTORY + FILENAME + FORMAT)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new DataAccessException("Error accessing Users.json");
        }
        return users;
    }
    public List<User> getUserList() {
        List<User> usersToReturn = new ArrayList<>();

        try (Reader reader = new FileReader(DIRECTORY + FILENAME + FORMAT)){
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            usersToReturn = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersToReturn;
    }





}
