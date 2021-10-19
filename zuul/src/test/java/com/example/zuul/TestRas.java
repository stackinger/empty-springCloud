package com.example.zuul;

import com.example.utils.RasUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestRas {

    private static final String pubKeyPath = "D:\\ras\\ras.pub";

    private static final String priKeyPath = "D:\\ras\\ras.pri";

    @Test
    void testGen() throws Exception {
        //生产公钥和私钥
        RasUtils.generateKey(pubKeyPath,priKeyPath,"dlNVa32DbOtk");
    }

}
