package com.example.cash_card_api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PutTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    /**
     * @Notes:  PUT can provide capability of CREATE a new Resource or UPDATE whole attributes of Resource
     *          If updating successfully, response with 204 NO CONTENT code.
     *          Otherwise, refuse updating with 404 code.
     *
     */
    @Test
    public void testRefuseUpdateById () {
        CashCard cashCard = new CashCard(null, 15.15, null);
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/cashcards/15", HttpMethod.PUT, new HttpEntity<CashCard>(cashCard), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DirtiesContext
    public void testUpdateByIdUsingPutMethod () {
        CashCard cashCard = new CashCard(null, 15.15, null);
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("toan", "123")
                .exchange("/cashcards/15", HttpMethod.PUT, new HttpEntity<CashCard>(cashCard), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
