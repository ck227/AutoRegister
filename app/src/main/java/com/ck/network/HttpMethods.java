package com.ck.network;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chendaye on 2017/11/19.
 */

public class HttpMethods {

    //    http://www.reocar.com/activity/get_lottery
    public static final String MSG_URL = "http://api.eobzz.com/";
    public static final String COMPANY_URL = "http://m.reocar.com/";
    public static final String COMPANY_URL2 = "http://www.reocar.com/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private NetworkService networkService;
    private Retrofit retrofit2;
    private NetworkService networkService2;
    private Retrofit retrofit3;
    private NetworkService networkService3;

    private HttpMethods() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MSG_URL)
                .build();
        networkService = retrofit.create(NetworkService.class);

        retrofit2 = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(COMPANY_URL)
                .build();
        networkService2 = retrofit2.create(NetworkService.class);

        retrofit3 = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(COMPANY_URL2)
                .build();
        networkService3 = retrofit3.create(NetworkService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(s);
    }

    //获取手机号
    public void getMobile(Subscriber<ResponseBody> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getMobile(options);
        toSubscribe(observable, subscriber);
    }

    //发验证码
    public void sendCode(Subscriber<ResponseBody> subscriber, @Body RequestBody body) {
        Observable observable = networkService2.sendCode(body);
        toSubscribe(observable, subscriber);
    }

    //发验证码2
    public void sendCode2(Subscriber<ResponseBody> subscriber, @Body RequestBody body) {
        Observable observable = networkService3.sendCode(body);
        toSubscribe(observable, subscriber);
    }

    //拿验证码
    public void getCode(Subscriber<ResponseBody> subscriber, Map<String, Object> options) {
        Observable observable = networkService.getCode(options);
        toSubscribe(observable, subscriber);
    }

    //注册
    public void register(Subscriber<ResponseBody> subscriber, @Body RequestBody body) {
        Observable observable = networkService2.register(body);
        toSubscribe(observable, subscriber);
    }

    //注册2
    public void register2(Subscriber<ResponseBody> subscriber, @Body RequestBody body) {
        Observable observable = networkService3.register(body);
        toSubscribe(observable, subscriber);
    }


}
