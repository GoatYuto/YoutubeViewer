package com.goat.youtubeviewer.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goat.youtubeviewer.BuildConfig;
import com.goat.youtubeviewer.MainActivity;
import com.goat.youtubeviewer.R;
import com.goat.youtubeviewer.network.YouTubeSearchRequest;
import com.goat.youtubeviewer.network.YouTubeSearchResponse;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView mRecyclerView;
    private YoutubeListAdapter mAdapter;

    private YouTubeSearchResponse mResponse;
    private String mQuery;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new YoutubeListAdapter(new YoutubeListAdapter.RecyclerTapEvent() {
            @Override
            public void onGetTapVideoId(String videoId) {
                playVideo(videoId);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //recyclerの末尾まで来た時の処理
                if (!recyclerView.canScrollVertically(1)) {
                    requestYoutubeSearch();
                }
            }
        });

        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setListener(new MainActivity.SearchViewListener() {
                @Override
                public void onQuery(String query) {
                    if (isQueryChanged(query)) {
                        onResetAdaper();
                    }
                    mQuery = query;
                    requestYoutubeSearch();
                }
            });
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void requestYoutubeSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Activity activity = getActivity();
                if (activity == null || !isAdded()) {
                    return;
                }

                YouTubeSearchRequest request = new YouTubeSearchRequest();
                request.setKeyWord(mQuery);
                if (mResponse != null && mResponse.getNextPageToken() != null) {
                    request.setPageToken(mResponse.getNextPageToken());
                }

                YouTubeSearchResponse response = request.createRequest();
                if (response != null) {
                    mResponse = response;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mResponse.isSuccess()) {
                                mAdapter.addData(mResponse);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), R.string.common_error_massage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        }).start();

    }

    private void playVideo(String videoId) {
        Activity activity = getActivity();
        if (activity == null || !isAdded()) {
            return;
        }
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(activity, BuildConfig.YOUTUBE_API_KEY, videoId);
        startActivity(intent);
    }


    /**
     * 検索キーワードが変更され、adapterの内容をリセットする必要があるかどうかを返却する
     *
     * @param query 検索ボックスに入力されたキーワード
     * @return true　検索キーワードが変更されている false 前回から入力内容に変更はない
     */
    private boolean isQueryChanged(String query) {
        return mQuery != null && !TextUtils.equals(query, mQuery);
    }

    private void onResetAdaper() {
        mAdapter.resetAdapter();
        mAdapter.notifyDataSetChanged();
        mResponse = null;
    }
}