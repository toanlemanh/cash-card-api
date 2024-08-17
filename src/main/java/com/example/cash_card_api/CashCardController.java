package com.example.cash_card_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    @Autowired
    CashCardRepository cashCardRepository;

    @GetMapping("/{id}")
    public ResponseEntity<String> getCashCardById(@PathVariable Long id){
        CashCard cashCard = cashCardRepository.findById(id).get();
        if (cashCard != null)
        return ResponseEntity.ok(""+cashCard.id());
        return ResponseEntity.notFound().build();
    }


}
