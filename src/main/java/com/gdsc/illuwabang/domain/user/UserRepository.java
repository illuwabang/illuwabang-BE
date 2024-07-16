package com.gdsc.illuwabang.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySub(String sub);

    User findByEmail(String email);

}
