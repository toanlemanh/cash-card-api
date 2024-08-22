package com.example.cash_card_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Iterable<CashCard>> getAllCashCards(Pageable pageable){
        Page<CashCard> cashCards = cashCardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(new Sort.Order(Sort.Direction.ASC, "amount")))
                )
        );
        return ResponseEntity.ok(cashCards.getContent());
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
