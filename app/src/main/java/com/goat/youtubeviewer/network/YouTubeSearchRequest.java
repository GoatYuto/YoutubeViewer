package com.goat.youtubeviewer.network;

import androidx.annotation.NonNull;

import com.goat.youtubeviewer.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class YouTubeSearchRequest extends OkHttpRequestBase<YouTubeSearchResponse>{

    private static final int MAX_RESULT_DEFAULT = 50;
    private int mMaxResult = MAX_RESULT_DEFAULT;
    private String mPageToken;

    public void setMaxResult (int maxResult) {
        //maxResultに指定できる範囲は0から50まで
        if(maxResult > 50 || 0 > maxResult) {
            return;
        }
        mMaxResult = maxResult;
    }

    public void setPageToken (String pageToken) {
        mPageToken = pageToken;
    }

    @NonNull
    @Override
    protected HashMap<String, String> getParams() {
        HashMap<String, String> params = super.getParams();
        params.put("type", "video");
        params.put("part", "snippet");
        params.put("q", "もこう");
        params.put("maxResults", Integer.toString(mMaxResult));
        if (mPageToken != null) {
            params.put("pageToken", mPageToken);
        }
        params.put("key", BuildConfig.YOUTUBE_API_KEY);
        return params;
    }

    @NonNull
    @Override
    Class<? extends YouTubeSearchResponse> getClazz() {
        return YouTubeSearchResponse.class;
    }


    @NonNull
    @Override
    protected HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    @NonNull
    @Override
    protected String getURL() {
        return "https://www.googleapis.com/youtube/v3/search";
    }
}
