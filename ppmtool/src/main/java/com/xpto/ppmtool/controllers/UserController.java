package com.xpto.ppmtool.controllers;

import com.xpto.ppmtool.domain.User;
import com.xpto.ppmtool.payload.JWTLoginSuccessResponse;
import com.xpto.ppmtool.payload.LoginRequest;
import com.xpto.ppmtool.security.JwtTokenProvider;
import com.xpto.ppmtool.security.SecurityConstants;
import com.xpto.ppmtool.services.UserService;
import com.xpto.ppmtool.utils.validations.MapValidationError;
import com.xpto.ppmtool.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, UserValidator userValidator, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<?> errorMap = MapValidationError.getMapValidation(bindingResult);//validating Inputs of project param.
        if (errorMap != null){
            return errorMap;
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + this.jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

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
