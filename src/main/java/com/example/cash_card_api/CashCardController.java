package com.example.cash_card_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    @Autowired
    CashCardRepository cashCardRepository;


    @GetMapping("/{id}")
    public ResponseEntity<CashCard> getCashCardById(@PathVariable Long id){
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(id);
        if (cashCardOptional.isPresent()){
            return ResponseEntity.ok(cashCardOptional.get());
        }
        return ResponseEntity.notFound().build();

    }
    @GetMapping("")
    public ResponseEntity<Iterable<CashCard>> getAllCashCards(){
        Iterable<CashCard> cashCards = cashCardRepository.findAll();
        return ResponseEntity.ok(cashCards);
    }

    @PostMapping("/")
    public ResponseEntity<Void> postCashCardById(@RequestBody CashCard expectedCashCard, UriComponentsBuilder ucb){
       CashCard cashCard = cashCardRepository.save(expectedCashCard);
       URI createdCashCardURI = ucb
               .path("/cashcards/{id}")
               .buildAndExpand(cashCard.id())
               .toUri();
       return ResponseEntity.created(createdCashCardURI).build();
    }
}
