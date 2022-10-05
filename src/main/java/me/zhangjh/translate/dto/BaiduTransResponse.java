package me.zhangjh.translate.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author zhangjh
 * @date 2022/10/5
 * {"from":"en","to":"zh",
 *  "trans_result":[{"src":"My heart will go on",\
 *  "dst":"\u6211\u7684\u5fc3\u4f1a\u7ee7\u7eed"}]}
 */
@Data
public class BaiduTransResponse {
    private String from;

    private String to;

    @JSONField(name = "error_code")
    private String errorCode;

    @JSONField(name = "error_msg")
    private String errorMsg;

    @JSONField(name = "trans_result")
    private List<BaiduTransResult> transResults;
}
