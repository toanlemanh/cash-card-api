package com.example.cash_card_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testPostCashCardApi (){
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/cashcards/", new CashCard(null, 90.01),Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

       URI location = response.getHeaders().getLocation();
       ResponseEntity<String> getResponse = testRestTemplate.getForEntity(location, String.class);
       assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(getResponse.getBody()).isEqualTo("1");

    }
}
