package com.example.cash_card_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeleteTests {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testRefuseNonExistingIdAndOwner () {
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("toan", "123")
                .exchange("/cashcards/16666", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    public void testDeleteByExistingIdAndOwner () {
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("toan", "123")
                .exchange("/cashcards/15", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> getResponse = testRestTemplate
                .withBasicAuth("toan", "123")
                .getForEntity("/cashcards/15", String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testNotAllowDeletingNotOwning() {
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("amee","123")
                .exchange("/cashcards/15", HttpMethod.DELETE, null, Void.class );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
