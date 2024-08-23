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
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    @Autowired
    CashCardRepository cashCardRepository;


    @GetMapping("/{id}")
    public ResponseEntity<CashCard> getCashCardById(@PathVariable Long id, Principal principal){
        Optional<CashCard> cashCardOptional = cashCardRepository.findByIdAndOwner(id, principal.getName());
        if (cashCardOptional.isPresent()){
            return ResponseEntity.ok(cashCardOptional.get());
        }
        return ResponseEntity.notFound().build();

    }
    @GetMapping("")
    public ResponseEntity<Iterable<CashCard>> getAllCashCards(Pageable pageable, Principal principal){
        Page<CashCard> cashCards = cashCardRepository.findAllByOwner(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(new Sort.Order(Sort.Direction.ASC, "amount")))
                ),
                principal.getName()
        );
        System.out.println(cashCards);
        return ResponseEntity.ok(cashCards.getContent());
    }
    //find by name

    // sort by name

    @PostMapping("/")
    public ResponseEntity<Void> postCashCard(@RequestBody CashCard expectedCashCard, UriComponentsBuilder ucb, Principal principal){
        if (principal != null ) {
            CashCard cashCard = cashCardRepository.save(new CashCard(null, expectedCashCard.amount(), principal.getName()));
            URI createdCashCardURI = ucb
                    .path("/cashcards/{id}")
                    .buildAndExpand(cashCard.id())
                    .toUri();
            return ResponseEntity.created(createdCashCardURI).build();
        }
       return ResponseEntity.notFound().build();
    }

    //Do not allow to create a cash card by PUT method supplying id
    // Only UPDATE or 404
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCashCardById (@PathVariable(value="id") Long id,
                                                    @RequestBody CashCard cashCard,
                                                    Principal principal){
        Optional<CashCard> cashCardOptional = cashCardRepository.findByIdAndOwner(id, principal.getName());
        if (cashCardOptional.isPresent()) {
            //updating
            cashCardRepository.save( new CashCard(id, cashCard.amount(), principal.getName()) );
            return ResponseEntity.noContent().build();
        }
        //404
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashCardById (@PathVariable(value="id") Long id,
                                                    Principal principal){
        if (cashCardRepository.existsByIdAndOwner(id, principal.getName())){
            cashCardRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
