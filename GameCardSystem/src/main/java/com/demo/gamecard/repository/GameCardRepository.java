package com.demo.gamecard.repository;

 import com.demo.gamecard.model.GameCard;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

@Repository
public interface GameCardRepository extends JpaRepository<GameCard,Long>{

}

