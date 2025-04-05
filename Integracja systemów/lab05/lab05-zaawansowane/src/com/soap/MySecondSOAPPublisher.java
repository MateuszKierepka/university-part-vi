package com.soap;

import javax.xml.ws.Endpoint;

public class MySecondSOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:7779/second", new MySecondWS());
    }
}
