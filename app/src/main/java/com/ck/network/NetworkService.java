package com.ck.network;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by chendaye on 2017/11/19.
 */

public interface NetworkService {

    //获取手机号
    @GET("httpApi.do")
    Observable<HttpResult.BaseResponse> getMobile(@QueryMap Map<String, Object> options);

    //发送验证码
    @GET("httpApi.do")
    Observable<HttpResult.BaseResponse> sendCode(@QueryMap Map<String, Object> options);

}
