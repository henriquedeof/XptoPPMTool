package com.xpto.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JWTLoginSuccessResponse {

    private boolean success;
    private String token;

}
