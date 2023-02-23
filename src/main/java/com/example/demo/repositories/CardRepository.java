package com.example.demo.repositories;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    public Card findCardByName(String name);
    List<Card> findByOrderByNameAsc();
    List<Card> findByOrderByNameDesc();
    @Query(value = "SELECT * FROM CARD u WHERE u.status = 1", nativeQuery = true)
    List<Card> findAllActiveCards();
    @Query(value = "SELECT * FROM CARD u WHERE u.status = 0", nativeQuery = true)
    List<Card> findAllDeactivatedCards();
    public List<Card> findCardByUserId(Long id);
    public Card findCardById(Long id);
}
