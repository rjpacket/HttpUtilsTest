package com.rjp.httputilstest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.rjp.httputils.BaseApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rjp.httputilstest.MyApi.BASE_URL;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("page", 1);
        new MyApi.Builder(this)
                .timeOut(20 * 1000)
                .baseUrl(BASE_URL)
                .showLoading(false)
                .build()
                .getList("femaleNameApi", hashMap, new BaseApi.GetListCallBack<Object>() {

                    @Override
                    public void onSuccess(List<Object> result) {
                        Log.e("-------------->", "success");
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });

//        new Novate.Builder(this)
//                .baseUrl("http://www.apiopen.top:88/")
//                .build()
//                .rxGet("femaleNameApi", hashMap, new RxStringCallback() {
//
//                    @Override
//                    public void onStart(Object tag) {
//                        super.onStart(tag);
//                    }
//
//                    @Override
//                    public void onError(Object tag, Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object tag, String response) {
//                        Log.e("-------------->", response);
//                    }
//
//                    @Override
//                    public void onCancel(Object tag, Throwable e) {
//                    }
//                });
    }
}
