package com.antsrl.jwtexample.repository;

import com.antsrl.jwtexample.entity.Role;
import com.antsrl.jwtexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRoleNot(Role role);

    List<User> findAllByUsernameNot(String username);
}
