package com.xpto.ppmtool.repositories;

import com.xpto.ppmtool.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User getById(Long id);

}
