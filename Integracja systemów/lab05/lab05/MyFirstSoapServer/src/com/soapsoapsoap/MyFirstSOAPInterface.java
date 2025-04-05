package com.soapsoapsoap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService // oznaczenie klasy jako SEO (Service Endpoint Interface)
@SOAPBinding(style = Style.RPC)
public interface MyFirstSOAPInterface {
    @WebMethod
    String getHelloWorldAsString(String name);

    @WebMethod
    Long getDaysBetweenDates(String date1, String date2);
}