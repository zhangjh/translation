package me.zhangjh.translate.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
@Getter
public enum BaiduLanguage {

    AUTO("auto", "自动检测"),
    YUE("yue", "粤语"),
    ZH("zh", "中文"),
    CHT("cht", "繁体中文"),
    EN("en", "英语"),
    WYW("wyw", "文言文"),
    JP("jp", "日语"),
    KOR("kor", "韩语"),
    FRA("fra", "法语"),
    SPA("spa", "西班牙语"),
    TH("th", "泰语"),
    ARA("ara", "阿拉伯语"),
    RU("ru", "俄语"),
    PT("pt", "葡萄牙语"),
    DE("de", "德语"),
    IT("it", "意大利语"),
    EL("el", "希腊语"),
    PL("pl", "波兰语"),
    BUL("bul", "保加利亚语"),
    EST("est", "爱沙尼亚语"),
    DAN("dan", "丹麦语"),
    FIN("fin", "芬兰语"),
    CS("cs", "捷克语"),
    ROM("rom", "罗马尼亚语"),
    SLO("slo", "斯洛文尼亚语"),
    SWE("swe", "瑞典语"),
    HU("hu", "匈牙利语"),
    VIE("vie", "越南语"),
    ;

    // 语种标识
    private String code;
    // 语种名称
    private String desc;

    BaiduLanguage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Map<String, String> getLanguages() {
        Map<String, String> langs = new HashMap<>();
        for (BaiduLanguage baiduLanguage : BaiduLanguage.values()) {
            langs.put(baiduLanguage.code, baiduLanguage.desc);
        }
        return langs;
    }
}
