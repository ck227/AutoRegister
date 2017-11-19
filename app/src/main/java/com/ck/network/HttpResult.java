package com.ck.network;

/**
 * Created by chendaye on 2017/11/19.
 */

public class HttpResult {

    //拿手机号，拿验证码
    public static class StringResponse {
        public String result;
    }

    //发验证码，注册
    public static class CodeResponse {
        public Boolean error;
        public String result;
    }

}
