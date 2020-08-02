package com.xpto.ppmtool.services;

import com.xpto.ppmtool.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    User loadUserById(Long id);

}
