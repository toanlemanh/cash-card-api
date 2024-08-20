package com.example.cash_card_api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    @DirtiesContext
    @Test
    void testPostCashCardApi (){
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/cashcards/", new CashCard(null, 90.01),Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> getResponse = testRestTemplate.getForEntity(location, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //additional test for data assertion in the newly created cash card
        //Using DocumentContext to read json object from the body of response
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        Double amount = documentContext.read("$.amount");
        assertThat(id).isNotNull();
        assertThat(amount).isEqualTo(90.01);

    }
}
