package me.zhangjh.translate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class TranslateApplicationTests {

    @Test
    void contextLoads() throws IOException {
        GoogleTranslate.translateText();
    }

}
