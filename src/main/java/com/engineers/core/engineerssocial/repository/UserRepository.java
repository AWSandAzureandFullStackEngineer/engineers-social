package com.engineers.core.engineerssocial.repository;

import com.engineers.core.engineerssocial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("select u from User u where u.username like %:query% OR u.email like %:query%")
    List<User> searchUser(@Param("query") String query);

}
