package com.neves_eduardo.core_engineering.tema8.service;

import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.DataAccessException;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO;
    private List<User> users;
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.users = userDAO.getUserList();
    }
    public UserService() {
        this.userDAO = new UserDAO();
        this.users = userDAO.getUserList();
    }

    public User registerUser(User user)  {
        validateUserRegistration(user);
        users.add(user);
        userDAO.writeUserFile(users);
        return user;
    }
    public void deleteUser(UUID userId) {
        if(!users.stream().anyMatch(b -> b.getId().equals(userId))) throw new DataAccessException("User not found");
        User userToDelete = users.stream().filter(b -> b.getId().equals(userId)).findFirst().get();
        users.remove(userToDelete);
        userDAO.writeUserFile(users);

    }
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    public User getUserById(UUID userId) {
        if(users.stream().noneMatch(s -> s.getId().equals(userId))) throw new InvalidOperationException("This user does not exist");
        return users.stream().filter(s -> s.getId().equals(userId)).findFirst().get();
    }



    private void validateUserRegistration(User user) {
        if(user.getName()==null || user.getName().equals("")) {
            throw new InvalidOperationException("User Name is a required field");
        }
    }
}
