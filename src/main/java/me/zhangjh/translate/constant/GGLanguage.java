package me.zhangjh.translate.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
@Getter
public enum GGLanguage {

    af("南非荷兰语","af"),
    sq("阿尔巴尼亚语","sq"),
    am("阿姆哈拉语","am"),
    ar("阿拉伯语","ar"),
    hy("亚美尼亚语","hy"),
    az("阿塞拜疆语","az"),
    eu("巴斯克语","eu"),
    be("白俄罗斯语","be"),
    bn("孟加拉语","bn"),
    bs("波斯尼亚语","bs"),
    bg("保加利亚语","bg"),
    ca("加泰罗尼亚语","ca"),
    ceb("宿务语","ceb"),
    cn("中文（简体）","zh"),
    tw("中文（繁体）","zh-TW"),
    co("科西嘉语","co"),
    hr("克罗地亚语","hr"),
    cs("捷克语","cs"),
    da("丹麦语","da"),
    nl("荷兰语","nl"),
    en("英语","en"),
    eo("世界语","eo"),
    et("爱沙尼亚语","et"),
    fil("菲律宾语","fil"),
    fi("芬兰语","fi"),
    fr("法语","fr"),
    fy("弗里斯兰语","fy"),
    gl("加利西亚语","gl"),
    ka("格鲁吉亚语","ka"),
    de("德语","de"),
    el("希腊语","el"),
    gu("古吉拉特语","gu"),
    ht("海地克里奥尔语","ht"),
    ha("豪萨语","ha"),
    haw("夏威夷语","haw"),
    he("希伯来语","he"),
    hi("印地语","hi"),
    hmn("苗语","hmn"),
    hu("匈牙利语","hu"),
    is("冰岛语","is"),
    ig("伊博语","ig"),
    id("印度尼西亚语","id"),
    ga("爱尔兰语","ga"),
    it("意大利语","it"),
    ja("日语","ja"),
    jv("爪哇语","jv"),
    kn("卡纳达语","kn"),
    kk("哈萨克语","kk"),
    km("高棉语","km"),
    rw("卢旺达语","rw"),
    ko("韩语","ko"),
    ku("库尔德语","ku"),
    ky("吉尔吉斯语","ky"),
    lo("老挝语","lo"),
    la("拉丁文","la"),
    lv("拉脱维亚语","lv"),
    lt("立陶宛语","lt"),
    lb("卢森堡语","lb"),
    mk("马其顿语","mk"),
    mg("马尔加什语","mg"),
    ms("马来语","ms"),
    ml("马拉雅拉姆文","ml"),
    mt("马耳他语","mt"),
    mi("毛利语","mi"),
    mr("马拉地语","mr"),
    mn("蒙古文","mn"),
    my("缅甸语","my"),
    ne("尼泊尔语","ne"),
    no("挪威语","no"),
    ps("普什图语","ps"),
    fa("波斯语","fa"),
    pl("波兰语","pl"),
    pt("葡萄牙语","pt"),
    pa("旁遮普语","pa"),
    ro("罗马尼亚语","ro"),
    ru("俄语","ru"),
    sm("萨摩亚语","sm"),
    gd("苏格兰盖尔语","gd"),
    sr("塞尔维亚语","sr"),
    st("塞索托语","st"),
    sn("修纳语","sn"),
    sd("信德语","sd"),
    si("僧伽罗语","si"),
    sk("斯洛伐克语","sk"),
    sl("斯洛文尼亚语","sl"),
    so("索马里语","so"),
    es("西班牙语","es"),
    su("巽他语","su"),
    sw("斯瓦希里语","sw"),
    sv("瑞典语","sv"),
    tg("塔吉克语","tg"),
    ta("泰米尔语","ta"),
    tt("鞑靼语","tt"),
    te("泰卢固语","te"),
    th("泰文","th"),
    tr("土耳其语","tr"),
    tk("土库曼语","tk"),
    uk("乌克兰语","uk"),
    ur("乌尔都语","ur"),
    ug("维吾尔语","ug"),
    uz("乌兹别克语","uz"),
    vi("越南语","vi"),
    cy("威尔士语","cy"),
    xh("班图语","xh"),
    yi("意第绪语","yi"),
    yo("约鲁巴语","yo"),
    zu("祖鲁语","zu"),
    ;

    // 语种名称
    private String desc;
    // 语种标识
    private String code;

    GGLanguage(String desc, String code) {
        this.code = code;
        this.desc = desc;
    }

    public static Map<String, String> getLanguages() {
        Map<String, String> langs = new HashMap<>();
        for (GGLanguage ggLanguage : GGLanguage.values()) {
            langs.put(ggLanguage.code, ggLanguage.desc);
        }
        return langs;
    }
}
