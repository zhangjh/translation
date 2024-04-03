package me.zhangjh.translate.controller;

import com.alibaba.fastjson2.JSONObject;
import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import me.zhangjh.translate.dto.*;
import me.zhangjh.translate.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
@RestController
@CrossOrigin
public class TranslateController {

    @Autowired
    private ChatGPTTranslate chatGPTTranslate;

    @Autowired
    private GeminiTranslate geminiTranslate;

    @RequestMapping("/translate")
    public Response translateText(String types, String text,
                                  String from, String to, String appId, String appSecret) {
        List<Map<String, String>> res = new ArrayList<>();

        try {
            Assert.isTrue(StringUtils.isNotEmpty(types), "翻译引擎为空");
            List<TranslateEngine> translateEngines = new ArrayList<>();
            for (String type : types.split(",")) {
                translateEngines.add(TranslateEngineFactory.getTranslateEngine(Integer.parseInt(type)));
            }
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "待翻译目标语种为空");
            TranslateRequest request = new TranslateRequest();
            request.setText(text);
            request.setFrom(from);
            request.setTo(to);
            for (TranslateEngine engine : translateEngines) {
                if(engine instanceof BaiDuTranslate) {
                    request.setAppId(appId);
                    request.setAppSecret(appSecret);
                    BaiduTransResponse response = ((BaiDuTranslate) engine).translateText(request);
                    Map<String, String> map = new HashMap<>();
                    if(StringUtils.isNotEmpty(response.getErrorCode())) {
                        map.put("baidu", response.getErrorMsg());
                        res.add(map);
                    } else {
                        List<BaiduTransResult> transResults = response.getTransResults();
                        Assert.isTrue(!CollectionUtils.isEmpty(transResults), "翻译结果返回空");
                        String dst = transResults.get(0).getDst();
                        map.put("baidu", dst);
                        res.add(map);
                    }
                } else if(engine instanceof GoogleTranslate) {
                    TranslateTextResponse response = ((GoogleTranslate) engine).translateText(request);
                    for (Translation translation : response.getTranslationsList()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("google", translation.getTranslatedText());
                        res.add(map);
                    }
                } else if(engine instanceof BingTranslate) {
                    List<BingTransResponse> bingTransResponses = ((BingTranslate) engine).translateText(request);
                    Map<String, String> map = new HashMap<>();
                    map.put("Bing", bingTransResponses.get(0).getTranslations().get(0).getText());
                    res.add(map);
                } else if(engine instanceof YouDaoTranslate) {
                    request.setAppId(appId);
                    request.setAppSecret(appSecret);
                    YoudaoTransResponse transResponse = ((YouDaoTranslate) engine).translateText(request);
                    Map<String, String> map = new HashMap<>();
                    map.put("Youdao", transResponse.getBasic());
                    res.add(map);
                }
            }
            return new Response<List<Map<String, String>>>().success(res);
        } catch (Throwable t) {
            return new Response<String>().fail(t.getMessage());
        }
    }

    @PostMapping("/baidu")
    public Response<String> baiduTranslateText(@RequestBody BaiduTransRequest baiduTransRequest) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(baiduTransRequest.getText()), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(baiduTransRequest.getTo()), "目标语种为空");

            TranslateRequest request = new TranslateRequest();
            request.setText(baiduTransRequest.getText());
            request.setFrom(baiduTransRequest.getFrom());
            request.setTo(baiduTransRequest.getTo());
            request.setAppId("20221005001370895");
            request.setAppSecret("E3UD2nYo0yK2miFeUYNq");
            BaiduTransResponse transResponse = new BaiDuTranslate().translateText(request);
            if(StringUtils.isNotEmpty(transResponse.getErrorCode())) {
                return new Response<String>().fail(transResponse.getErrorMsg());
            }
            List<BaiduTransResult> transResults = transResponse.getTransResults();
            if(CollectionUtils.isEmpty(transResults)) {
                return new Response<String>().success("");
            }
            return new Response<String>().success(transResults.get(0).getDst());
        } catch (Exception e) {
            return new Response<String>().fail(e.getMessage());
        }
    }

    @RequestMapping("/baidu/lang")
    public Response<Map<String, String>> baiduLang() {
        try {
            return new Response<Map<String, String>>().success(new BaiDuTranslate().getLanguages());
        } catch (Exception e) {
            return new Response<Map<String, String>>().fail(e.getMessage());
        }
    }

    @RequestMapping("/google")
    public Response<String> ggTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            TranslateRequest request = new TranslateRequest();
            request.setText(text);
            request.setFrom(from);
            request.setTo(to);
            TranslateTextResponse transResponse = new GoogleTranslate().translateText(request);
            if(StringUtils.isNotEmpty(transResponse.getInitializationErrorString())) {
                return new Response<String>().fail(transResponse.getInitializationErrorString());
            }
            List<Translation> translationsList = transResponse.getTranslationsList();
            if(CollectionUtils.isEmpty(translationsList)) {
                return new Response<String>().success("");
            }
            return new Response<String>().success(translationsList.get(0).getTranslatedText());
        } catch (Exception e) {
            return new Response<String>().fail(e.getMessage());
        }
    }

    @RequestMapping("/google/lang")
    public Response<Map<String, String>> ggLang() {
        try {
            return new Response<Map<String, String>>().success(new GoogleTranslate().getLanguages());
        } catch (Exception e) {
            return new Response<Map<String, String>>().fail(e.getMessage());
        }
    }

    @RequestMapping("/bing")
    public Response<String> bingTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            TranslateRequest request = new TranslateRequest();
            request.setText(text);
            request.setFrom(from);
            request.setTo(to);
            List<BingTransResponse> transResponses = new BingTranslate().translateText(request);
            if(CollectionUtils.isEmpty(transResponses)
                    || CollectionUtils.isEmpty(transResponses.get(0).getTranslations())) {
                return new Response<String>().success(null);
            }
            return new Response<String>().success(transResponses.get(0).getTranslations().get(0).getText());
        } catch (Exception e) {
            return new Response<String>().fail(e.getMessage());
        }
    }

    @RequestMapping("/bing/lang")
    public Response<Map<String, String>> bingLang() {
        try {
            Map<String, String> langs = new HashMap<>(1);
            BingLanguageResponse languages = new BingTranslate().getLanguages();
            for (Map.Entry<String, BingLanguageDTO> entry : languages.getTranslation().entrySet()) {
                String key = entry.getKey();
                String name = entry.getValue().getName();
                langs.put(key, name);
            }
            return new Response<Map<String, String>>().success(langs);
        } catch (Exception e) {
            return new Response<Map<String, String>>().fail(e.getMessage());
        }
    }

    @RequestMapping("/youdao")
    public Response<YoudaoBasicDTO> ydTranslateText(String text, String from, String to, String appId,
                                                    String appSecret) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            TranslateRequest request = new TranslateRequest();
            request.setText(text);
            request.setFrom(from);
            request.setTo(to);
            request.setAppId(appId);
            request.setAppSecret(appSecret);
            YoudaoTransResponse transResponse = new YouDaoTranslate().translateText(request);
            if(!Objects.equals(transResponse.getErrorCode(), "0")) {
                return new Response<YoudaoBasicDTO>().fail(transResponse.getErrorCode());
            }
            YoudaoBasicDTO youdaoBasicDTO = JSONObject.parseObject(transResponse.getBasic(), YoudaoBasicDTO.class);
            if(youdaoBasicDTO == null) {
                youdaoBasicDTO = new YoudaoBasicDTO();
            }
            youdaoBasicDTO.setTranslation(transResponse.getTranslation());
            return new Response<YoudaoBasicDTO>().success(youdaoBasicDTO);
        } catch (Exception e) {
            return new Response<YoudaoBasicDTO>().fail(e.getMessage());
        }
    }

    @RequestMapping("/youdao/lang")
    public Response<Map<String, String>> ydLang() {
        try {
            return new Response<Map<String, String>>().success(new YouDaoTranslate().getLanguages());
        } catch (Exception e) {
            return new Response<Map<String, String>>().fail(e.getMessage());
        }
    }

    @RequestMapping("/chatGpt")
    public Response<String> chatGpt(String text, String transMode) {
        try {
            TranslateRequest request = new TranslateRequest();
            request.setText(text);
            request.setTransMode(Boolean.parseBoolean(transMode));
            request.setAppId("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");
            String responses = chatGPTTranslate.translateText(request);
            return new Response<String>().success(responses);
        } catch (Exception e) {
            return new Response<String>().fail(e.getMessage());
        }
    }

    @PostMapping("/gemini")
    public Response<String> geminiStream(@RequestBody TranslateRequest request) {
        try {
            String res = geminiTranslate.translateText(request);
            return new Response<String>().success(res);
        } catch (Exception e) {
            return new Response<String>().fail(e.getMessage());
        }
    }
}
