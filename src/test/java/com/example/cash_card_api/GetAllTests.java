package com.example.cash_card_api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAllTests {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Test
    void testGetAllCashCardApi (){
        ResponseEntity<String> response = testRestTemplate.getForEntity("/cashcards?page=0&size=2&sort=amount,asc", String.class );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int lengthOfPage = documentContext.read("$.length()");
        assertThat(lengthOfPage).isEqualTo(2);

        int size = documentContext.read("$.size()");
        assertThat(size).isEqualTo(2);

        JSONArray keys = documentContext.read("$..id");
        assertThat(keys).containsExactlyInAnyOrder(11, 12);

        ResponseEntity<String> response1 = testRestTemplate.getForEntity("/cashcards", String.class );
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext1 = JsonPath.parse(response1.getBody());
        JSONArray page = documentContext1.read("$.*");

        int size1 = page.size();
        assertThat(size1).isEqualTo(4);

        assertThat(page.toArray()).containsExactlyInAnyOrder(13, 15);




    }
}
