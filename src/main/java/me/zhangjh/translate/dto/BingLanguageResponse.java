package me.zhangjh.translate.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangjh
 * @date 2022/10/6
 */
@Data
public class BingLanguageResponse {
    private Map<String, BingLanguageDTO> translation;
}
