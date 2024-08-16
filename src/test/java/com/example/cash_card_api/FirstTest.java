package com.example.cash_card_api;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FirstTest {
    @Test
     void testFirst(){
        assertThat(33).isEqualTo(32);
    }
    @Test
     void testSecond(){
        assertThat(33).isEqualTo(33);
    }
}
