package me.zhangjh.translate.constant;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Getter
public enum YouDaoLanguage {
    zh("中文","zh-CHS"),
    cht("中文繁体","zh-CHT"),
    en("英文","en"),
    ja("日文","ja"),
    ko("韩文","ko"),
    fr("法文","fr"),
    es("西班牙文","es"),
    pt("葡萄牙文","pt"),
    it("意大利文","it"),
    ru("俄文","ru"),
    vi("越南文","vi"),
    de("德文","de"),
    ar("阿拉伯文","ar"),
    id("印尼文","id"),
    af("南非荷兰语","af"),
    bs("波斯尼亚语","bs"),
    bg("保加利亚语","bg"),
    yue("粤语","yue"),
    ca("加泰隆语","ca"),
    hr("克罗地亚语","hr"),
    cs("捷克语","cs"),
    da("丹麦语","da"),
    nl("荷兰语","nl"),
    et("爱沙尼亚语","et"),
    fj("斐济语","fj"),
    fi("芬兰语","fi"),
    el("希腊语","el"),
    ht("海地克里奥尔语","ht"),
    he("希伯来语","he"),
    hi("印地语","hi"),
    mww("白苗语","mww"),
    hu("匈牙利语","hu"),
    sw("斯瓦希里语","sw"),
    tlh("克林贡语","tlh"),
    lv("拉脱维亚语","lv"),
    lt("立陶宛语","lt"),
    ms("马来语","ms"),
    mt("马耳他语","mt"),
    no("挪威语","no"),
    fa("波斯语","fa"),
    pl("波兰语","pl"),
    otq("克雷塔罗奥托米语","otq"),
    ro("罗马尼亚语","ro"),
    cyrl("塞尔维亚语(西里尔文)","sr-Cyrl"),
    latn("塞尔维亚语(拉丁文)","sr-Latn"),
    sk("斯洛伐克语","sk"),
    sl("斯洛文尼亚语","sl"),
    sv("瑞典语","sv"),
    ty("塔希提语","ty"),
    th("泰语","th"),
    to("汤加语","to"),
    tr("土耳其语","tr"),
    uk("乌克兰语","uk"),
    ur("乌尔都语","ur"),
    cy("威尔士语","cy"),
    yua("尤卡坦玛雅语","yua"),
    sq("阿尔巴尼亚语","sq"),
    am("阿姆哈拉语","am"),
    hy("亚美尼亚语","hy"),
    az("阿塞拜疆语","az"),
    bn("孟加拉语","bn"),
    eu("巴斯克语","eu"),
    be("白俄罗斯语","be"),
    ceb("宿务语","ceb"),
    co("科西嘉语","co"),
    eo("世界语","eo"),
    tl("菲律宾语","tl"),
    fy("弗里西语","fy"),
    gl("加利西亚语","gl"),
    ka("格鲁吉亚语","ka"),
    gu("古吉拉特语","gu"),
    ha("豪萨语","ha"),
    haw("夏威夷语","haw"),
    is("冰岛语","is"),
    ig("伊博语","ig"),
    ga("爱尔兰语","ga"),
    jw("爪哇语","jw"),
    kn("卡纳达语","kn"),
    kk("哈萨克语","kk"),
    km("高棉语","km"),
    ku("库尔德语","ku"),
    ky("柯尔克孜语","ky"),
    lo("老挝语","lo"),
    la("拉丁语","la"),
    lb("卢森堡语","lb"),
    mk("马其顿语","mk"),
    mg("马尔加什语","mg"),
    ml("马拉雅拉姆语","ml"),
    mi("毛利语","mi"),
    mr("马拉地语","mr"),
    mn("蒙古语","mn"),
    my("缅甸语","my"),
    ne("尼泊尔语","ne"),
    ny("齐切瓦语","ny"),
    ps("普什图语","ps"),
    pa("旁遮普语","pa"),
    sm("萨摩亚语","sm"),
    gd("苏格兰盖尔语","gd"),
    st("塞索托语","st"),
    sn("修纳语","sn"),
    sd("信德语","sd"),
    si("僧伽罗语","si"),
    so("索马里语","so"),
    su("巽他语","su"),
    tg("塔吉克语","tg"),
    ta("泰米尔语","ta"),
    te("泰卢固语","te"),
    uz("乌兹别克语","uz"),
    xh("南非科萨语","xh"),
    yi("意第绪语","yi"),
    yo("约鲁巴语","yo"),
    zu("南非祖鲁语","zu"),
    auto("自动识别","auto"),
        ;
    // 语种标识
    private String code;
    // 语种名称
    private String desc;

    YouDaoLanguage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<Map<String, String>> getLanguages() {
        List<Map<String, String>> languages = new ArrayList<>();
        for (YouDaoLanguage ydLanguage : YouDaoLanguage.values()) {
            Map<String, String> map = new HashMap<>();
            map.put(ydLanguage.desc, ydLanguage.code);
            languages.add(map);
        }
        return languages;
    }
}
