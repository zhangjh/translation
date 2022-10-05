package me.zhangjh.translate.service;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public abstract class TranslateEngine<T> {
    /**
     * 通用翻译接口
     * @param query 待翻译文本
     * @param from 源语种，可空
     * @param to 目标语种，非空
     * */
    public abstract T translateText(String query, String from, String to) throws Exception;
}
