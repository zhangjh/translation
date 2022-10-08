package me.zhangjh.translate.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import me.zhangjh.translate.dto.BingLanguageResponse;
import me.zhangjh.translate.dto.BingTransResponse;
import okhttp3.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
public class BingTranslate extends TranslateEngine<List<BingTransResponse>> {

    private static final String URL_PRE = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0";

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

    private final String key;

    private final String location;

    {
        key = System.getenv("bing_key");
        location = System.getenv("bing_location");
    }

    @SneakyThrows
    public BingLanguageResponse getLanguages() {
        String url = "https://api.cognitive.microsofttranslator.com/languages?api-version=3.0";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).get()
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return JSON.parseObject(Objects.requireNonNull(response.body()).string(), BingLanguageResponse.class);
    }

    @Override
    public List<BingTransResponse> translateText(String query, String from, String to) {
        try {
            OkHttpClient client = new OkHttpClient();
            // 将待搜词组装成形如["Text": "待搜词"]的格式
            JSONObject jsonVal = new JSONObject();
            jsonVal.put("Text", query);
            // query: "[{\"Text\": \"I would really like to drive your car around the block a few times!\"}]"
            RequestBody body = RequestBody.create(JSONObject.toJSONString(Collections.singleton(jsonVal)), MEDIA_TYPE);

            Request request = new Request.Builder()
                    .url(URL_PRE + "&from=" + from + "&to=" + to)
                    .post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", key)
                    .addHeader("Ocp-Apim-Subscription-Region", location)
                    .addHeader("Content-type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            if(response.code() != 200) {
                throw new RuntimeException(response.message());
            }
            return  JSON.parseArray(Objects.requireNonNull(response.body()).string(), BingTransResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}