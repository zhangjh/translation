package me.zhangjh.translate.service;

import me.zhangjh.gemini.client.GeminiService;
import me.zhangjh.gemini.response.TextResponse;
import me.zhangjh.translate.dto.TranslateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author njhxzhangjihong@126.com
 * @date 10:19 2024/4/3
 * @Description
 */
@Component
public class GeminiTranslate extends TranslateEngine<String> {

    @Autowired
    private GeminiService geminiService;

    @Override
    public String translateText(TranslateRequest translateRequest) {
        String text = translateRequest.getText();
        if(StringUtils.isNotEmpty(text)) {
            String prompt =
                    "将包裹在{{{}}}内的内容进行翻译，只需返回译文即可，如果是中文翻译成英文，如果是英文翻译成中文，" +
                            "我会通过语言标识告知你待翻译内容是什么语种，zh表示中文，en" +
                            "表示英文，如果未给出请自行判断。" +
                            "待翻译内容: {{{ " + text + " }}}";
            if(StringUtils.isNotEmpty(translateRequest.getTo())) {
                prompt += ", 语种：" + translateRequest.getTo();
            }
            TextResponse response = geminiService.generateByText(prompt);
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        return "";
    }
}
