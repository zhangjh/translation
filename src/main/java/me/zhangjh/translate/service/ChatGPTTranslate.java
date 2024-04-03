package me.zhangjh.translate.service;


import me.zhangjh.chatgpt.client.ChatGptService;
import me.zhangjh.chatgpt.dto.request.TextRequest;
import me.zhangjh.chatgpt.dto.response.TextResponse;
import me.zhangjh.translate.dto.TranslateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author zhangjh451@midea.com
 * @date 11:21 PM 2022/12/14
 * @Description
 */
@Component
public class ChatGPTTranslate extends TranslateEngine<String> {

    @Autowired
    private ChatGptService chatGptService;

    @Override
    public String translateText(TranslateRequest request) {
//        CompletionData data = new CompletionData();
//        data.setModel("text-davinci-003");
//        data.setPrompt("Q:将括号里的词汇翻译一下，如果是中文翻译成英文，如果是英文翻译成中文.(" + request.getText() + ") A:");

        TextRequest textRequest = new TextRequest();
        if(request.getTransMode()) {
            textRequest.setPrompt("Q:将括号里的词汇翻译一下，如果是中文翻译成英文，如果是英文翻译成中文.(" + request.getText() + ") A:");
        } else {
            textRequest.setPrompt("Q:" + request.getText() + " A:");
        }
        TextResponse textCompletion = chatGptService.createTextCompletion(textRequest);
//        System.out.println(textCompletion);
        if(StringUtils.isNotEmpty(textCompletion.getErrorMsg())) {
            throw new RuntimeException(textCompletion.getErrorMsg());
        }

        if(CollectionUtils.isEmpty(textCompletion.getChoices())) {
            throw new RuntimeException("no choices returned");
        }
        return textCompletion.getChoices().get(0).getText();
    }
}
