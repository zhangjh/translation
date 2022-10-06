package me.zhangjh.translate.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Data
public class BingTransResponse {

    private List<BingTransResult> translations;
}
