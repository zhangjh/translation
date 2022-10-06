package me.zhangjh.translate.constant;

import lombok.Getter;
import me.zhangjh.translate.service.*;

import java.util.Arrays;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Getter
public enum TranslateEngineEnum {
    BAIDU(0, new BaiDuTranslate()),
    GOOGLE(1, new GoogleTranslate()),
    BING(2, new BingTranslate()),
    YOUDAO(3, new YouDaoTranslate()),
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
