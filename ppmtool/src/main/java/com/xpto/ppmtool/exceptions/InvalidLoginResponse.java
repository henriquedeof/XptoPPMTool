package com.xpto.ppmtool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvalidLoginResponse {
    //Will be returned as a Json to the client calling the service, example: Postman.

    private String username = "Bad username";
    private String password = "Bad password";

}

