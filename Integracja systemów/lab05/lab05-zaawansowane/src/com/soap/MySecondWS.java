package com.soap;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@WebService(endpointInterface = "com.soap.MySecondSOAPInterface")
public class MySecondWS implements MySecondSOAPInterface{
    @Override
    public ArrayList<String> getUsers() {
        List<String> users = new ArrayList<>();
        users.add("Mateusz Kierepka");
        users.add("Jan Kowalski");
        users.add("Anna Nowak");
        return (ArrayList<String>) users;
    }

    @Override
    public int addNumbers(int a, int b) {
        return a + b;
    }
}
