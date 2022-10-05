package me.zhangjh.translate.service;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
public class TranslateEngineFactory {

    public static TranslateEngine getTranslateEngine(String type) {
        switch (type) {
            case "0":
                return new BaiDuTranslate();
            case "1":
                return new GoogleTranslate();
        }
        throw new RuntimeException("非法的引擎类型");
    }
}
