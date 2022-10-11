package me.zhangjh.translate.dto;

import lombok.Data;

/**
 * @author zhangjh
 * @date 2022/10/11
 */
@Data
public class TranslateRequest {

    private String text;

    private String from;

    private String to;

    /** appId, appSecret在有道、百度时必传 */
    private String appId;

    private String appSecret;
}
