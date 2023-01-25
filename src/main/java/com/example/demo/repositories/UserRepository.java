package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // nu e necesar?
public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByFirstName(String firstName);
    public User findUserByEmail(String email);
   //Optional<User> findUserByEmail(String email);


    //   public void deleteUserByEmail(String email);
}
