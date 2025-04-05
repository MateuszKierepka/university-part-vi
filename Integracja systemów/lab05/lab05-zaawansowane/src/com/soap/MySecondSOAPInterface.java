package com.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MySecondSOAPInterface {
    @WebMethod
    List<String> getUsers();

    @WebMethod
    int addNumbers(int a, int b);
}
