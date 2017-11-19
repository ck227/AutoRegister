package com.ck.network;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by chendaye on 2017/11/19.
 */

public interface NetworkService {

    //获取手机号
    @GET("httpApi.do")
    Observable<ResponseBody> getMobile(@QueryMap Map<String, Object> options);


    //发送验证码

    /*@Headers({
//            "Content-Type:application/json; charset=utf-8",
            "Kept Alive:Yes",
    })*/
//    @Headers("Content-Type : application/json; charset=utf-8")

    @POST("activity/get_lottery?")
    @Headers({"Content-Type:application/json;charset=UTF-8",
            "Kept Alive:Yes"})
    Observable<ResponseBody> sendCode(@Body RequestBody body);

    //拿验证码
    @GET("httpApi.do")
    Observable<ResponseBody> getCode(@QueryMap Map<String, Object> options);

    //直接注册
    @POST("activity/get_lottery?")
    @Headers({"Content-Type:application/json;charset=UTF-8",
            "Kept Alive:Yes"})
    Observable<ResponseBody> register(@Body RequestBody body);

}
