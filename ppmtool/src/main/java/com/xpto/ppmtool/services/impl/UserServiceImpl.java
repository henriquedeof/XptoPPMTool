package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.User;
import com.xpto.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.xpto.ppmtool.repositories.UserRepository;
import com.xpto.ppmtool.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //Encrypts passed information

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User saveUser(User newUser) {
        try {
            newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());//Username must be unique. If not, throws constraint exception

            //Workaround: The match password validation was already executed but the confirmPassword is still being shown on Postman. So, I am setting it blank and it will not be shown anymore.
            //For sure this is not the best way to solve it.
            newUser.setConfirmPassword("");

            return this.userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }
    }


}
