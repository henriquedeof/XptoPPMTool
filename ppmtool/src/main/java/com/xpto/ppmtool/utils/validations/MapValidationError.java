package com.xpto.ppmtool.utils.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class MapValidationError {

    public static ResponseEntity<?> getMapValidation(final BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<String, String>();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
