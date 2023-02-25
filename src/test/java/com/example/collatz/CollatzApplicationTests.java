package com.example.collatz;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(args = { "10" })
class CollatzApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

}
