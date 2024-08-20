package com.example.cash_card_api;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import javax.swing.text.Document;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApiApplicationTests {
	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void contextLoads() {
	}
	@Test
	void testGetCashCardApi (){
		ResponseEntity<String> response = testRestTemplate.getForEntity("/cashcards/15", String.class );
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Integer id = documentContext.read("$.id");
		assertThat(id).isEqualTo(15);
	}




}
