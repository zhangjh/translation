package me.zhangjh.translate.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author zhangjh
 * @date 2022/10/10
 */
@Data
public class YoudaoBasicDTO {

    private String phonetic;

    @JSONField(name = "uk-phonetic")
    private String ukPhonetic;

    @JSONField(name = "us-phonetic")
    private String usPhonetic;

    private List<String> explains;
}
