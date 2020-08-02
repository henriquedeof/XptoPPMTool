package com.xpto.ppmtool.controllers;

import com.xpto.ppmtool.domain.User;
import com.xpto.ppmtool.services.UserService;
import com.xpto.ppmtool.utils.validations.MapValidationError;
import com.xpto.ppmtool.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){
        this.userValidator.validate(user, bindingResult);//validating if passwords match

        ResponseEntity<?> errorMap = MapValidationError.getMapValidation(bindingResult);//validating Inputs of project param.
        if (errorMap != null){
            return errorMap;
        }

        User savedUser = this.userService.saveUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

}
