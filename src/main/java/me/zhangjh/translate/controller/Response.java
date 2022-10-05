package me.zhangjh.translate.controller;

import lombok.Data;

/**
 * @author zhangjh
 * @date 2022/10/5
 */
@Data
public class Response<T> {

    public boolean success;

    public T data;

    public String errorMsg;

    public Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public Response<T> fail(String errorMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }
}