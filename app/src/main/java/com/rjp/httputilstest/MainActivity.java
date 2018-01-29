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
                .getList("femaleNameApi", hashMap, new BaseApi.GetListCallBack<NameModel>() {

                    @Override
                    public void onSuccess(List<NameModel> result) {
                        for (NameModel nameModel : result) {
                            Log.e("------->", nameModel.getFemalename());
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
    }
}
