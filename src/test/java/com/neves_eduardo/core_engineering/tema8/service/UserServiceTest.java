package com.neves_eduardo.core_engineering.tema8.service;

import com.neves_eduardo.core_engineering.tema8.DAO.UserDAO;
import com.neves_eduardo.core_engineering.tema8.exceptions.InvalidOperationException;
import com.neves_eduardo.core_engineering.tema8.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private UserService userService;
    ArrayList<User> users = new ArrayList<>();
    @Before
    public void init(){
        when(userDAO.getUserList()).thenReturn(users);
        userService = new UserService(userDAO);
    }

    @Test
    public void registerUserShouldRegisterTheUserCorrectly() {
        User user = new User("z");
        userService.registerUser(user);
        assertEquals(userDAO.getUserList().size(), 1);
        assertTrue(userDAO.getUserList().contains(user));
        verify(userDAO).writeUserFile(users);
    }

    @Test(expected = InvalidOperationException.class)
    public void registerUserShouldNotRegisterUserWithNullName() {
        User user = new User(null);
        users.add(user);
        userService.registerUser(user);
        assertTrue(userDAO.getUserList().isEmpty());
        verify(userDAO).writeUserFile(users);
    }

    @Test(expected = InvalidOperationException.class)
    public void registerUserShouldNotRegisterUserWithEmptyName() {
        User user = new User("");
        userService.registerUser(user);
        assertTrue(userDAO.getUserList().isEmpty());
        verify(userDAO).writeUserFile(users);
    }

    @Test
    public void deleteUserShouldDeleteTheUserCorrectly() {
        User user = new User("z");
        users.add(user);
        userService.deleteUser(user.getId());
        assertEquals(users.size(), 0);
        verify(userDAO).writeUserFile(users);
    }

    @Test
    public void getUserListShouldReturnTheCorrectList() {
        User user = new User("mockinson");
        users.add(user);
        assertEquals(userDAO.getUserList().size(), 1);
        assertTrue(userService.getUserList().contains(user));
    }

}