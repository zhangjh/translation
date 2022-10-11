package me.zhangjh.translate;

import com.alibaba.fastjson2.JSONObject;
import me.zhangjh.translate.constant.BaiduLanguage;
import me.zhangjh.translate.dto.*;
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
        String text = "english";
        TranslateRequest request = new TranslateRequest();
        request.setText(text);
        request.setFrom("en");
        request.setTo("zh");
        new GoogleTranslate().translateText(request);
    }

    @Test
    void baiduTranslate() throws Exception {
        TranslateRequest request = new TranslateRequest();
        request.setText("我爱我家");
        request.setFrom("zh");
        request.setTo("en");
        request.setAppId(System.getenv("baidu_fy_appId"));
        request.setAppSecret(System.getenv("baidu_fy_appSecret"));
        BaiduTransResponse response1 = new BaiDuTranslate().translateText(request);
        System.out.println(response1);

        request.setText("My heart will go on");
        request.setFrom("en");
        request.setTo("zh");
        BaiduTransResponse response2 = new BaiDuTranslate().translateText(request);
        System.out.println(response2);
    }

    @Test
    void baiduLanguages() {
        Map<String, String> languages = BaiduLanguage.getLanguages();
        System.out.println(JSONObject.toJSONString(languages));
    }

    @Test
    void bingTranslate() {
        TranslateRequest request = new TranslateRequest();
        request.setText("我爱中国");
        request.setFrom("zh");
        request.setTo("en");
        List<BingTransResponse> responses = new BingTranslate().translateText(request);
        System.out.println(responses);
    }

    @Test
    void bingLanguages() {
        BingLanguageResponse languages = new BingTranslate().getLanguages();
        System.out.println(languages);
    }

    @Test
    void youdaoTranslate() {
        TranslateRequest request = new TranslateRequest();
        request.setText("我爱中国");
        request.setFrom("zh-CHS");
        request.setTo("en");
        request.setAppId(System.getenv("youdao_appKey"));
        request.setAppSecret(System.getenv("youdao_appSecret"));
        YoudaoTransResponse response = new YouDaoTranslate().translateText(request);
        System.out.println(response);
    }

    @Test
    void youdaoLanguage() {
        Map<String, String> languages = new YouDaoTranslate().getLanguages();
        System.out.println(languages);
    }
}
