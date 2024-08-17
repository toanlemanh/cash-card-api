package com.example.cash_card_api;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {
//    CashCard findCashCardById(Long id);
}
