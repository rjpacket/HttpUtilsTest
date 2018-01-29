package com.rjp.httputilstest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rjp.httputils.BaseApi;
import com.rjp.httputils.RetrofitService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : Gimpo create on 2018/1/29 17:39
 * email  : jimbo922@163.com
 */

public class MyApi extends BaseApi<BaseModel> {
    public static final String BASE_URL = "http://www.apiopen.top:88/";

    public MyApi(Builder builder){
        this.context = builder.context;
        this.timeOut = builder.timeOut;
        this.baseUrl = builder.baseUrl;
        this.showLoading = builder.showLoading;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS)       //设置连接超时
                .readTimeout(30000L, TimeUnit.MILLISECONDS)          //设置读取超时
                .writeTimeout(30000L, TimeUnit.MILLISECONDS)         //设置写入超时
                .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024))   //设置缓存目录和10M缓存
                .addInterceptor(interceptor)    //添加日志拦截器（该方法也可以设置公共参数，头信息）
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    @Override
    protected void hideLoadingDialog() {

    }

    @Override
    protected void showLoadingDialog() {

    }

    @Override
    protected BaseModel getModel(String decodeResponse, Gson gson) {
        return gson.fromJson(decodeResponse, new TypeToken<BaseModel>() {
        }.getType());
    }

    @Override
    protected String decodeResponse(String response) {

        return response;
    }

    @Override
    protected void logResponse(String decodeResponse) {

    }

    @Override
    protected String getEncryptParams(Map<String, Object> params) {

        return null;
    }

    @Override
    protected String getResponseCode(BaseModel baseModel) {
        return String.valueOf(baseModel.getCode());
    }

    @Override
    protected String getResponseSuccessCode() {
        return "200";
    }

    @Override
    protected String getDecodeDataErrorMessage() {
        return "解析错误";
    }

    @Override
    protected String getResponseMessage(BaseModel baseModel) {
        return baseModel.getMsg();
    }

    @Override
    protected Object getResponseData(BaseModel baseModel) {
        return baseModel.getData();
    }

    public static class Builder{

        private Context context;
        private long timeOut;
        private String baseUrl;
        private boolean showLoading;

        public Builder(Context context){
            this.context = context;
        }

        public Builder baseUrl(String url){
            this.baseUrl = url;
            return this;
        }

        public Builder timeOut(long timeOut){
            this.timeOut = timeOut;
            return this;
        }

        public Builder showLoading(boolean showable){
            this.showLoading = showable;
            return this;
        }

        public MyApi build(){
            return new MyApi(this);
        }
    }
}
