package com.example.demo.repositories;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    public Card findCardByName(String name);
    public List<Card> findCardByUserId(Long id);
    public Card findCardById(Long id);
}
