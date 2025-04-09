package com.soap;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.soap.MySecondSOAPInterface")
public class MySecondWS implements MySecondSOAPInterface{
    @Override
    public UserList getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Mateusz Kierepka", 22));
        users.add(new User("Beata Nowak", 23));
        users.add(new User("Jan Kowalski", 24));
        UserList userList = new UserList();
        userList.setUsers(users);
        return userList;
    }

    @Override
    public double multiplyNum(double num1, double num2) {
        return num1 * num2;
    }
}