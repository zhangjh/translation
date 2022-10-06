package me.zhangjh.translate.constant;

import lombok.Getter;
import me.zhangjh.translate.service.BingTranslate;
import me.zhangjh.translate.service.GoogleTranslate;
import me.zhangjh.translate.service.TranslateEngine;

import java.util.Arrays;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Getter
public enum TranslateEngineEnum {
    BAIDU(0, new BingTranslate()),
    GOOGLE(1, new GoogleTranslate()),
    BING(2, new BingTranslate()),
    ;

    private Integer type;

    private TranslateEngine translateEngine;

    TranslateEngineEnum(Integer type, TranslateEngine translateEngine) {
        this.type = type;
        this.translateEngine = translateEngine;
    }

    public static TranslateEngine getEngine(Integer type) {
        return Arrays.stream(TranslateEngineEnum.values())
                .filter(item -> item.type.equals(type))
                .findAny().get().translateEngine;
    }
}
