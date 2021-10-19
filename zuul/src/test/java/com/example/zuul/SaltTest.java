package com.example.zuul;

import com.example.utils.RasUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaltTest {

    @Test
    void testGen() throws Exception {
        String model = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuffer salt = new StringBuffer();
        char[] m = model.toCharArray();
        for (int i = 0; i < 12; i++) {
            char c = m[(int) (Math.random() * 62)];
            salt = salt.append(c);
        }
        System.out.println(salt.toString());
    }

}
