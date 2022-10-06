package me.zhangjh.translate.controller;

import com.google.cloud.translate.v3.TranslateTextResponse;
import com.google.cloud.translate.v3.Translation;
import me.zhangjh.translate.dto.BaiduTransResponse;
import me.zhangjh.translate.dto.BaiduTransResult;
import me.zhangjh.translate.dto.BingTransResponse;
import me.zhangjh.translate.dto.YoudaoTransResponse;
import me.zhangjh.translate.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
@RestController
public class TranslateController {

    @RequestMapping("/translate")
    public Response translateText(String types, String text,
                                                   String from, String to) throws Exception {
        List<Map<String, String>> res = new ArrayList<>();

        try {
            Assert.isTrue(StringUtils.isNotEmpty(types), "翻译引擎为空");
            List<TranslateEngine> translateEngines = new ArrayList<>();
            for (String type : types.split(",")) {
                translateEngines.add(TranslateEngineFactory.getTranslateEngine(Integer.parseInt(type)));
            }
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "待翻译目标语种为空");
            for (TranslateEngine engine : translateEngines) {
                if(engine instanceof BaiDuTranslate) {
                    BaiduTransResponse response = ((BaiDuTranslate) engine).translateText(text, from, to);
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
                    TranslateTextResponse response = ((GoogleTranslate) engine).translateText(text, from, to);
                    for (Translation translation : response.getTranslationsList()) {
                        Map<String, String> map = new HashMap<>();
                        map.put("google", translation.getTranslatedText());
                        res.add(map);
                    }
                } else if(engine instanceof BingTranslate) {
                    List<BingTransResponse> bingTransResponses = ((BingTranslate) engine).translateText(text, from, to);
                    Map<String, String> map = new HashMap<>();
                    map.put("Bing", bingTransResponses.get(0).getTranslations().get(0).getText());
                    res.add(map);
                } else if(engine instanceof YouDaoTranslate) {
                    YoudaoTransResponse transResponse = ((YouDaoTranslate) engine).translateText(text, from, to);
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
    @RequestMapping("/baidu")
    public Response baiduTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            BaiduTransResponse transResponse = new BaiDuTranslate().translateText(text, from, to);
            if(StringUtils.isNotEmpty(transResponse.getErrorCode())) {
                return new Response<>().fail(transResponse.getErrorMsg());
            }
            List<BaiduTransResult> transResults = transResponse.getTransResults();
            return new Response<>().success(transResults);
        } catch (Exception e) {
            return new Response<>().fail(e.getMessage());
        }
    }

    @RequestMapping("/google")
    public Response ggTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            TranslateTextResponse transResponse = new GoogleTranslate().translateText(text, from, to);
            if(StringUtils.isNotEmpty(transResponse.getInitializationErrorString())) {
                return new Response<>().fail(transResponse.getInitializationErrorString());
            }
            List<Translation> translationsList = transResponse.getTranslationsList();
            return new Response<>().success(translationsList);
        } catch (Exception e) {
            return new Response<>().fail(e.getMessage());
        }
    }

    @RequestMapping("/bing")
    public Response bingTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            List<BingTransResponse> transResponses = new BingTranslate().translateText(text, from, to);
            return new Response<>().success(transResponses);
        } catch (Exception e) {
            return new Response<>().fail(e.getMessage());
        }
    }

    @RequestMapping("/youdao")
    public Response ydTranslateText(String text, String from, String to) {
        try {
            Assert.isTrue(StringUtils.isNotEmpty(text), "待翻译文本为空");
            Assert.isTrue(StringUtils.isNotEmpty(to), "目标语种为空");
            YoudaoTransResponse transResponse = new YouDaoTranslate().translateText(text, from, to);
            if(!Objects.equals(transResponse.getErrorCode(), "0")) {
                return new Response<>().fail(transResponse.getErrorCode());
            }
            return new Response<>().success(transResponse);
        } catch (Exception e) {
            return new Response<>().fail(e.getMessage());
        }
    }
}
