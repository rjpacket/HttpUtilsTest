package com.rjp.httputils;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Gimpo create on 2018/1/26 12:32
 * @email : jimbo922@163.com
 */

public interface RetrofitService {

    @POST()
    @FormUrlEncoded
    Observable<Object> request(@Url() String url, @FieldMap Map<String, Object> maps);

    @POST("{url}")
    Observable<Object> request(@Path("url") String url, @Query("body") String params);
}
