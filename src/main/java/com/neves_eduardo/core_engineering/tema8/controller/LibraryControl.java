package com.neves_eduardo.core_engineering.tema8.controller;

import com.neves_eduardo.core_engineering.tema8.model.Book;
import com.neves_eduardo.core_engineering.tema8.model.User;
import com.neves_eduardo.core_engineering.tema8.service.BookService;
import com.neves_eduardo.core_engineering.tema8.service.LoanService;
import com.neves_eduardo.core_engineering.tema8.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class LibraryControl {
    BookService bookService = new BookService();
    UserService userService = new UserService();
    LoanService loanService = new LoanService();

    public void usersWithDueLoansReport() {
        for (User user: userService
                .getUserList()
                .stream()
                .filter(s -> loanService.countUserOverDueLoans(s.getId())>0)
                .collect(Collectors.toList())
        ) {
            System.out.println("User  " + user.getName() +" has the follow overdue loans:");
            loanService.getLateLoans(user.getId())
                    .forEach(s -> System.out.println("Loan Id: "+ s.getId() + " Book" + s.getBook().getTitle() + "Days: " + loanService.countDaysOverDue(s.getId()) + "Fine: $" + loanService.calculateFee(s.getId())));
        }
    }

public void booksAndUserReport() {
    System.out.println("====Book and Users Report====");
    for(Book book : bookService
            .getBookList()
            .stream()
            .filter(s -> !s.isAvailable())
            .collect(Collectors.toList())
    ) {
        System.out.println("Book " + book.getTitle()+" is loaned to " + loanService
                .getLoanList()
                .stream()
                .filter(l -> l.getBook().getId().equals(book.getId()))
                .findFirst()
                .get().getUser().getName());
    }

}

    public void showTop10Users() {

        System.out.println("==== TOP 10 Users ====");
        Map<User, Integer> userMaps = new HashMap<>();
        for (User user: userService.getUserList()
                .stream()
                .filter(u -> loanService.countUserTotalLoans(u.getId())>0)
                .limit(10)
                .collect(Collectors.toList())
        ) {
            userMaps.put(user,loanService.countUserTotalLoans(user.getId()));
        }
        List<User> top10 = userMaps.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getKey).collect(Collectors.toList());
        for (User user: top10) {
            System.out.println("User: " + user.getName() + "  Number of Loans: " + loanService.countUserTotalLoans(user.getId()));
        }
    }



}
