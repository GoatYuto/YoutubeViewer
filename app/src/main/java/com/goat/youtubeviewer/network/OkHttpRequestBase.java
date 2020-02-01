package com.goat.youtubeviewer.network;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.goat.youtubeviewer.utills.DebugLog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

abstract public class OkHttpRequestBase<T extends ApiResponseBase> {
    private static final String TAG = OkHttpRequestBase.class.getSimpleName();

    private static final int DEFAULT_RETRY = 3;

    public enum HttpMethod {
        GET,
        HEAD,
        POST,
        PATCH,
        PUT,
        DELETE,
    }

    public T createRequest() {
        Request.Builder requestBuilder = new Request.Builder();
        HttpMethod method = getMethod();
        HashMap<String, String> headers = getHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            String key = header.getKey();
            String value = header.getValue();
            DebugLog.d(TAG, "[createRequest] header key : " + key + " value : " + value);
            requestBuilder.addHeader(key, value);
        }

        //TODO Get以外の実装はしていない
        if (method == HttpMethod.GET) {
            HttpUrl httpUrl = HttpUrl.parse(getURL());
            if(httpUrl != null) {
                HttpUrl.Builder httpBuilder = httpUrl.newBuilder();
                HashMap<String, String> params = getParams();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    String key = param.getKey();
                    String value = param.getValue();
                    DebugLog.d(TAG, "[createRequest] body key : " + key + " value : " + value);
                    httpBuilder.addQueryParameter(param.getKey(), param.getValue());
                }

                requestBuilder.url(httpBuilder.build());
            }
        }

        Request request = requestBuilder.build();

        try {
            Response response = OkHttpClientHelper.getClient().newCall(request).execute();
            ResponseBody responseBody = response.body();
            if(responseBody != null) {
                return new Gson().fromJson(responseBody.string(), getClazz());
            }

        } catch (Exception e) {
            DebugLog.d(TAG, "[createRequest]", e);

        }
        return null;
    }

    @NonNull
    protected HashMap<String, String> getHeaders() {
        HashMap<String, String> header = new HashMap<>();
        return header;
    }

    @NonNull
    protected HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        return params;
    }

    @NonNull
    abstract Class<? extends T> getClazz();

    @NonNull
    abstract protected HttpMethod getMethod();

    @NonNull
    abstract protected String getURL();

}
