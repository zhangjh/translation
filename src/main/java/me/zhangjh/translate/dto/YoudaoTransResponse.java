package me.zhangjh.translate.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Data
public class YoudaoTransResponse {

    private String errorCode;

    private List<String> returnPhrase;

    private String query;

    private List<String> translation;

    private String basic;

    private List<Map<String, String>> web;

    // 源语种和目标语种：zh-CHS2en
    private String l;

    private String dict;

    private String webdict;

    // 目标语种发音
    private String tSpeakUrl;

    // 源语种发音
    private String speakUrl;
}
