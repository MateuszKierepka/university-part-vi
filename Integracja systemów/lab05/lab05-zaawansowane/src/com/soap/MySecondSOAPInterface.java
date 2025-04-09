package com.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

// Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface MySecondSOAPInterface {
    @WebMethod
    UserList getUsers();

    @WebMethod
    double multiplyNum(double num1, double num2);
}
