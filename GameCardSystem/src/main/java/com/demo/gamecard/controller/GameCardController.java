
package com.example.reportConfig.controller;

import java.util.List;
 import javax.validation.Valid;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 import com.demo.gamecard.model.GameCard;
 import com.demo.gamecard.repository.GameCardRepository;
 import com.demo.gamecard.exception.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Calendar;

@RestController
@RequestMapping("/games")
public class GameCardController {

    @Autowired
    GameCardRepository GameCardRepo;

    //report to the system swipe in event
    @PostMapping("/swipein/{cardumber}")
    public boolean HandleSwipeIn(@PathVariable(value = "cardumber") Long CardNumber) {
        //this function checks the balance. If balance is less than 10, return error
        GameCard card = GameCardRepo.findById(CardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("gamecard", "card number", CardNumber));
        if(card.getBalance() <10)
        {
            //don't allow the customer into the platform. Insufficent Balance
            return false;
        }
        Long balance = card.getBalance();
        balance = balance - calculateCost(card.getSwipeInTime());
        if(balance < 0)
        {
            return false;
        }
        return true;
    }

    //report to the system swipeout event
    @PostMapping("/swipeout/{cardumber}")
    public int HandleSwipeOut(@PathVariable(value = "cardumber") Long CardNumber) {
        GameCard card = GameCardRepo.findById(CardNumber)
        .orElseThrow(() -> new ResourceNotFoundException("gamecard", "card number", CardNumber));
        if(card.getBalance() <10)
        {

            //don't allow the customer into the platform. Insufficent Balance
            return 1;
        }
        return 0;
    }

    //utility function to calculate balance
    private Long calculateCost(Date swipeInTime) {
        //calculate the amount to be deducted
        Long currTimeInMillis=System.currentTimeMillis();
        LocalDateTime currTime = LocalDateTime.now();
        Long gameDuration = currTimeInMillis - swipeInTime.getTime();
        Long gameCost;
        //calculate the amount to be deduced
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if((dayOfWeek == Calendar.SUNDAY) || (dayOfWeek == Calendar.SATURDAY)) {
            gameCost = gameDuration * 20;
        }
        else {
            gameCost = gameDuration * 10;
        }
        return gameCost;
    }

}

