package me.zhangjh.translate;

import com.alibaba.fastjson2.JSONObject;
import me.zhangjh.translate.constant.BaiduLanguage;
import me.zhangjh.translate.dto.BaiduTransResponse;
import me.zhangjh.translate.service.BaiDuTranslate;
import me.zhangjh.translate.service.GoogleTranslate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() throws IOException {
        // TODO(developer): Replace these variables before running the sample.
        // Supported Languages: https://cloud.google.com/translate/docs/languages
        String targetLanguage = "en";
        String fromLanguage = "zh";
        String text = "你干什么吃的呀";
        new GoogleTranslate().translateText(targetLanguage, fromLanguage, text);
    }

    @Test
    void baiduTranslate() throws Exception {
        BaiduTransResponse response1 = new BaiDuTranslate().translateText("我爱我家", "zh", "en");
        System.out.println(response1);

        BaiduTransResponse response2 = new BaiDuTranslate().translateText("My heart will go on", "en", "zh");
        System.out.println(response2);
    }

    @Test
    void baiduLanguages() {
        List<Map<String, String>> languages = BaiduLanguage.getLanguages();
        System.out.println(JSONObject.toJSONString(languages));
    }

}
