package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.User;
import com.xpto.ppmtool.repositories.UserRepository;
import com.xpto.ppmtool.services.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getExistingUser(this.userRepository.findByUsername(username));
    }

    @Override
    public User loadUserById(Long id) {
        return this.getExistingUser(this.userRepository.getById(id));
    }

    private User getExistingUser(User user) {
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

}
