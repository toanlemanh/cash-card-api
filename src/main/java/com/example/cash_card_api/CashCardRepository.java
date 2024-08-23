package com.example.cash_card_api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {
    Optional<CashCard> findByIdAndOwner (Long id, String owner);
    Page<CashCard> findAllByOwner (PageRequest pageRequest, String owner);
    boolean existsByIdAndOwner(Long id, String owner);
}
