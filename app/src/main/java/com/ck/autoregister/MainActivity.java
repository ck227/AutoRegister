package com.ck.autoregister;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private int size = 0;
    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = (TextView) findViewById(R.id.num);
        getMobile();
    }

    private void getMobile() {
        Map<String, Object> map = new HashMap<>();
        map.put("action", "getMobilenum");
        map.put("pid", "8566");
        map.put("uid", "chendaye123");
        map.put("token", "8513c8577a3e06a230ed21a7a8ec7f1e");
        map.put("mobile", "");
        map.put("size", "1");
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }


            @Override
            public void onError(Throwable e) {
                Log.e("ck", "获取手机号异常");
//                getMobile();
            }

            @Override
            public void onNext(ResponseBody response) {
                try {
//                    Log.e("ck", "拿到手机号" + response.string());
                    String mobile = response.string().substring(0, 11);
                    Log.e("ck", "拿到手机号" + mobile);
                    //发送验证码
                    sendCode(mobile);
                } catch (IOException e) {
                    Log.e("ck", "拿到手机号异常");
                    e.printStackTrace();
                }
            }
        };
        HttpMethods.getInstance().getMobile(subscriber, map);
    }

    private void sendCode(final String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "ditui");
        map.put("mobile", mobile);
        map.put("ground_push_code", "GROUND_PUSH16080000001");
        map.put("cdkey", "");
        map.put("user_name", "");
        map.put("code", "");
        map.put("resend", false);
        map.put("universal_zcode", "201705");
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ck", "发送验证码异常");
            }

            @Override
            public void onNext(ResponseBody response) {
//                http://www.reocar.com/activity/get_lottery
                //Log.e("ck", "http://www.reocar.com/activity/get_lottery");
                //重新再来
                sendCode2(mobile);
                /*try {
                    Log.e("ck", response.string().toString());
                    if (response.string().toString().contains("ok")) {
                        Log.e("ck", "发送验证码成功,10秒后获取验证码" + response.string().toString());
                        //获取验证码
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                getCode(mobile);
                            }
                        }, 10 * 1000);
                    }
                } catch (IOException e) {
                    Log.e("ck", "发送验证码异常2");
                    e.printStackTrace();

                }*/
                //发送验证码
            }
        };
        Gson gson = new Gson();
        String strEntity = gson.toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        HttpMethods.getInstance().sendCode(subscriber, body);
    }

    private void sendCode2(final String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "ditui");
        map.put("mobile", mobile);
        map.put("ground_push_code", "GROUND_PUSH16080000001");
        map.put("cdkey", "");
        map.put("user_name", "");
        map.put("code", "");
        map.put("resend", false);
        map.put("universal_zcode", "201705");
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ck", "发送验证码异常2");
                Log.e("ck", "重新取号");
                getMobile();
                //
            }

            @Override
            public void onNext(ResponseBody response) {
                try {
                    String tmp = response.string().toString();
                    Log.e("ck", "第二次发送短信" + mobile + tmp);
                    if (tmp.contains("ok")) {
                        Log.e("ck", "发送验证码成功,10秒后获取验证码2");
                        //获取验证码
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                getCode(mobile);
                            }
                        }, 10 * 1000);
                    } else {
                        //
                        Log.e("ck", "已经参与过了，换号");
                        getMobile();
                    }
                } catch (Exception e) {
                    Log.e("ck", "发送验证码异常3");
                    e.printStackTrace();

                }
            }
        };
        Gson gson = new Gson();
        String strEntity = gson.toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        HttpMethods.getInstance().sendCode2(subscriber, body);
    }

    int tryTime = 0;

    private void getCode(final String mobile) {
        Log.e("ck", "正在获取验证码" + mobile);
        Map<String, Object> map = new HashMap<>();
        map.put("action", "getVcodeAndReleaseMobile");
        map.put("uid", "chendaye123");
        map.put("token", "8513c8577a3e06a230ed21a7a8ec7f1e");
        map.put("mobile", mobile);

        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ck", "获取验证码异常");
            }

            @Override
            public void onNext(ResponseBody response) {
                try {
                    String tmp = response.string().toString();
                    if (tmp.equals("not_receive")) {
                        tryTime++;
                        if (tryTime < 4) {
                            Log.e("ck", "没收到验证码,10秒后重试");
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    getCode(mobile);
                                }
                            }, 10 * 1000);
                        } else {
                            tryTime = 0;
                            Log.e("ck", "手机收不到短信。换号中");
                            getMobile();
                        }
                    } else {
//                        您好！瑞卡租车欢迎注册。您的6位验证码为：867308，请尽快完成激活。【首汽瑞卡租车】
                        int pos = tmp.indexOf("：");
                        String code = tmp.substring(pos + 1, pos + 7);
                        Log.e("ck", "拿到验证码" + code);
                        register(mobile, code);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        HttpMethods.getInstance().getCode(subscriber, map);
    }


    private void register(final String mobile, final String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "ditui");
        map.put("mobile", mobile);
        map.put("ground_push_code", "GROUND_PUSH16080000001");
        map.put("cdkey", "");
        map.put("user_name", "");
        map.put("code", code);
        map.put("resend", false);
        map.put("universal_zcode", "201705");
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody response) {
                register2(mobile, code);
            }
        };
        Gson gson = new Gson();
        String strEntity = gson.toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        HttpMethods.getInstance().register(subscriber, body);
    }

    private void register2(final String mobile, final String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "ditui");
        map.put("mobile", mobile);
        map.put("ground_push_code", "GROUND_PUSH16080000001");
        map.put("cdkey", "");
        map.put("user_name", "");
        map.put("code", code);
        map.put("resend", false);
        map.put("universal_zcode", "201705");
        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                Log.e("ck", "注册成功" + mobile);
//                size++;
//                num.setText("成功注册：" + size + "个用户");
            }

            @Override
            public void onNext(ResponseBody response) {
                try {
                    Log.e("ck", "注册信息" + response.string().toString());
                    size++;
                    num.setText("成功注册：" + size + "个用户");
                    if (size < 100)
                        getMobile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Gson gson = new Gson();
        String strEntity = gson.toJson(map);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        HttpMethods.getInstance().register2(subscriber, body);
    }

}
