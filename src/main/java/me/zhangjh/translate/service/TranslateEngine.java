package me.zhangjh.translate.service;

import me.zhangjh.translate.dto.TranslateRequest;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public abstract class TranslateEngine<T> {
    /**
     * 通用翻译接口
     * */
    public abstract T translateText(TranslateRequest translateRequest) throws Exception;
}
