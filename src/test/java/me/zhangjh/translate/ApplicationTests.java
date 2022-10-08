package me.zhangjh.translate;

import com.alibaba.fastjson2.JSONObject;
import me.zhangjh.translate.constant.BaiduLanguage;
import me.zhangjh.translate.dto.BaiduTransResponse;
import me.zhangjh.translate.dto.BingLanguageResponse;
import me.zhangjh.translate.dto.BingTransResponse;
import me.zhangjh.translate.dto.YoudaoTransResponse;
import me.zhangjh.translate.service.BaiDuTranslate;
import me.zhangjh.translate.service.BingTranslate;
import me.zhangjh.translate.service.GoogleTranslate;
import me.zhangjh.translate.service.YouDaoTranslate;
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
        String targetLanguage = "zh";
        String fromLanguage = "en";
        String text = "english";
        new GoogleTranslate().translateText(text, fromLanguage, targetLanguage);
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

    @Test
    void bingTranslate() {
        List<BingTransResponse> responses = new BingTranslate().translateText("我爱中国", "zh", "en");
        System.out.println(responses);
    }

    @Test
    void bingLanguages() {
        BingLanguageResponse languages = BingTranslate.getLanguages();
        System.out.println(languages);
    }

    @Test
    void youdaoTranslate() {
        YoudaoTransResponse response = new YouDaoTranslate().translateText("我爱中国", "zh-CHS", "en");
        System.out.println(response);
    }

    @Test
    void youdaoLanguage() {
        List<Map<String, String>> languages = new YouDaoTranslate().getLanguages();
        System.out.println(languages);
    }
}
