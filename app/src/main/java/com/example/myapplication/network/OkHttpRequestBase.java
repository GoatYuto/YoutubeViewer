package com.example.myapplication.network;

import androidx.annotation.NonNull;

import com.example.myapplication.utills.DebugLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

abstract public class OkHttpRequestBase {
    private static final String TAG = OkHttpRequestBase.class.getSimpleName();

    public enum HttpMethod {
        GET,
        HEAD,
        POST,
        PATCH,
        PUT,
        DELETE,
    }

    public void createRequest() {
        Request.Builder builder = new Request.Builder();
        HttpMethod method = getMethod();
        HashMap<String, String> headers = getHeaders();

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            DebugLog.d(TAG, "[createRequest] header key : " + entry.getKey() + " value : " + entry.getValue());
            builder.addHeader(entry.getKey(), entry.getValue());
        }


        if(method == HttpMethod.GET) {
            HttpUrl.Builder httpBuilder = HttpUrl.parse(getURL()).newBuilder();
            HashMap<String, String> params = getParams();
            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    httpBuilder.addQueryParameter(param.getKey(), param.getValue());
                }
            }
            builder.url(httpBuilder.build());
        }
        Request request = builder.build();
        Response response = null;
        try {
            response = OkHttpClientHelper.getClient().newCall(request).execute();
            DebugLog.d(TAG, "[createRequest]response = " + response.body().string());
        } catch (IOException e) {

        }


    }

    protected HashMap<String, String> getHeaders() {
        HashMap<String, String> header = new HashMap<>();
        return header;
    }


    protected HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        return params;
    }

    @NonNull
    abstract protected HttpMethod getMethod();

    @NonNull
    abstract protected String getURL();

}
