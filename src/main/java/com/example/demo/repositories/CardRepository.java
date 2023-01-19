package com.example.demo.repositories;

import com.example.demo.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    public Card findCardByName(String name);

}
