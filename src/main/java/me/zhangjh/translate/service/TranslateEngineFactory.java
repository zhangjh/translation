package me.zhangjh.translate.service;

import me.zhangjh.translate.constant.TranslateEngineEnum;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public class TranslateEngineFactory {

    public static TranslateEngine getTranslateEngine(Integer type) {
        return TranslateEngineEnum.getEngine(type);
    }
}
