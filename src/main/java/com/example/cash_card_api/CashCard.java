package com.example.cash_card_api;

import org.springframework.data.annotation.Id;

record CashCard (@Id Long id, Double amount, String owner){

}
