package com.ck.network;

import retrofit2.Retrofit;

/**
 * Created by chendaye on 2017/11/19.
 */

public class HttpMethods {

    public static final String MSG_URL = "http://api.eobzz.com/httpApi.do";

    public static final String COMPANY_URL = "http://m.reocar.com/activity/get_lottery";

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private NetworkService networkService;

}
