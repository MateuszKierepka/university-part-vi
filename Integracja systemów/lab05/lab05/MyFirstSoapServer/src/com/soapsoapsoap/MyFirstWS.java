package com.soapsoapsoap;

import javax.jws.WebService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebService(endpointInterface = "com.soapsoapsoap.MyFirstSOAPInterface")
public class MyFirstWS implements MyFirstSOAPInterface{
    public String getHelloWorldAsString(String nazwa) {
        String message = "Witaj "+nazwa+"!";
        return message;
    }
    public Long getDaysBetweenDates(String date1, String date2) {
        long numberOfDays=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            LocalDateTime tempdate1 = LocalDateTime.parse(date1, dtf);
            LocalDateTime tempdate2 = LocalDateTime.parse(date2, dtf);
            numberOfDays = Duration.between(tempdate1, tempdate2).toDays();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
}