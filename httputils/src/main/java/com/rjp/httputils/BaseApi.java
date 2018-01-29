package com.rjp.httputils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Gimpo create on 2018/1/26 12:43
 * @email : jimbo922@163.com
 */

public abstract class BaseApi<U> {

    public Context context;
    public long timeOut;
    public String baseUrl;
    public boolean showLoading;
    public RetrofitService service;

    /**
     * 获取对象
     * @param url
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void getObject(String url, Map<String, Object> params, final GetObjectCallBack<T> callBack) {
        service.request(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object result) {
                        try {
                            String decodeResponse = decodeResponse(result.toString());
                            logResponse(decodeResponse);
                            Gson gson = new GsonBuilder().create();
                            U baseModel = gson.fromJson(decodeResponse, new TypeToken<U>() {
                            }.getType());
                            String errorcode = getResponseCode(baseModel);
                            if (getResponseSuccessCode().equals(errorcode)) {
                                Object data = getResponseData(baseModel);
                                Class cl = getTypeClassOfInterfaceObject(callBack);
                                T t = gson.fromJson(gson.toJson(data), new Element<T>(cl));
                                if (t != null) {
                                    callBack.onSuccess(t);
                                } else {
                                    callBack.onFailure(getDecodeDataErrorMessage());
                                }
                            } else {
                                callBack.onFailure(getResponseMessage(baseModel));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    protected abstract void hideLoadingDialog();

    protected abstract void showLoadingDialog();

    /**
     * 获取对象  参数需要加密
     * @param url
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void getEncryptObject(String url, Map<String, Object> params, final GetObjectCallBack<T> callBack) {
        service.request(url, getEncryptParams(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object result) {
                        try {
                            String decodeResponse = decodeResponse(result.toString());
                            logResponse(decodeResponse);
                            Gson gson = new GsonBuilder().create();
                            U baseModel = gson.fromJson(decodeResponse, new TypeToken<U>() {
                            }.getType());
                            String errorcode = getResponseCode(baseModel);
                            if (getResponseSuccessCode().equals(errorcode)) {
                                Object data = getResponseData(baseModel);
                                Class cl = getTypeClassOfInterfaceObject(callBack);
                                T t = gson.fromJson(gson.toJson(data), new Element<T>(cl));
                                if (t != null) {
                                    callBack.onSuccess(t);
                                } else {
                                    callBack.onFailure(getDecodeDataErrorMessage());
                                }
                            } else {
                                callBack.onFailure(getResponseMessage(baseModel));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取数组
     * @param url
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void getList(String url, Map<String, Object> params, final GetListCallBack<T> callBack) {
        service.request(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("----------->", e.getMessage());
                    }

                    @Override
                    public void onNext(Object result) {
                        try {
                            String decodeResponse = decodeResponse(result.toString());
                            logResponse(decodeResponse);
                            Gson gson = new GsonBuilder().create();
                            U baseModel = gson.fromJson(decodeResponse, new TypeToken<U>() {
                            }.getType());
                            String errorcode = getResponseCode(baseModel);
                            if (getResponseSuccessCode().equals(errorcode)) {
                                Object data = getResponseData(baseModel);
                                Class cl = getTypeClassOfInterfaceObject(callBack);
                                List<T> list = gson.fromJson(gson.toJson(data), new ListWithElements<T>(cl));
                                if (list != null) {
                                    callBack.onSuccess(list);
                                } else {
                                    callBack.onFailure(getDecodeDataErrorMessage());
                                }
                            } else {
                                callBack.onFailure(getResponseMessage(baseModel));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 获取数组
     * @param url
     * @param params
     * @param callBack
     * @param <T>
     */
    public <T> void getEncryptList(String url, Map<String, Object> params, final GetListCallBack<T> callBack) {
        service.request(url, getEncryptParams(params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object result) {
                        try {
                            String decodeResponse = decodeResponse(result.toString());
                            logResponse(decodeResponse);
                            Gson gson = new GsonBuilder().create();
                            U baseModel = gson.fromJson(decodeResponse, new TypeToken<U>() {
                            }.getType());
                            String errorcode = getResponseCode(baseModel);
                            if (getResponseSuccessCode().equals(errorcode)) {
                                Object data = getResponseData(baseModel);
                                Class cl = getTypeClassOfInterfaceObject(callBack);
                                List<T> list = gson.fromJson(gson.toJson(data), new ListWithElements<T>(cl));
                                if (list != null) {
                                    callBack.onSuccess(list);
                                } else {
                                    callBack.onFailure(getDecodeDataErrorMessage());
                                }
                            } else {
                                callBack.onFailure(getResponseMessage(baseModel));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private Class getTypeClassOfInterfaceObject(Object obj) {
        return (Class) ((ParameterizedType) obj.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    /**
     *
     * 解密获取的数据
     * @param response
     * @return 解密之后的参数
     */
    protected abstract String decodeResponse(String response);

    /**
     * 打印解密之后的数据
     * @param decodeResponse
     */
    protected abstract void logResponse(String decodeResponse);

    /**
     *
     * 加密请求参数
     * @param params
     * @return 加密之后的参数
     */
    protected abstract String getEncryptParams(Map<String, Object> params);

    /**
     * 获取返回码
     * @param baseModel
     * @return
     */
    protected abstract String getResponseCode(U baseModel);

    /**
     * 成功的特征码
     * @return
     */
    protected abstract String getResponseSuccessCode();

    /**
     * 解析数据出错时的文案提示
     * @return
     */
    protected abstract String getDecodeDataErrorMessage();

    /**
     * 获取后台的提示信息
     * @param baseModel
     * @return
     */
    protected abstract String getResponseMessage(U baseModel);

    /**
     * 获取真实可用的数据
     * @param baseModel
     * @return
     */
    protected abstract Object getResponseData(U baseModel);

    /**
     * 回调对象接口
     */
    public interface GetObjectCallBack<T> {

        void onSuccess(T result);

        void onFailure(String errorMsg);
    }

    /**
     * 回调列表接口
     */
    public interface GetListCallBack<T> {

        void onSuccess(List<T> result);

        void onFailure(String errorMsg);
    }
}
