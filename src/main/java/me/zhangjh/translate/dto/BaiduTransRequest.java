package me.zhangjh.translate.dto;

import lombok.Data;

/**
 * @author zhangjh451@midea.com
 * @date 16:01 2023/9/14
 * @Description
 */
@Data
public class BaiduTransRequest {
    private String text;

    private String from;

    private String to;

}
