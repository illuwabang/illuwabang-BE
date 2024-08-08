package com.gdsc.illuwabang.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySub(String sub);

    User findByEmail(String email);

}
