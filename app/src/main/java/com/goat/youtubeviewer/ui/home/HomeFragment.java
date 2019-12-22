package com.goat.youtubeviewer.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goat.youtubeviewer.MainActivity;
import com.goat.youtubeviewer.R;
import com.goat.youtubeviewer.network.YouTubeSearchRequest;
import com.goat.youtubeviewer.network.YouTubeSearchResponse;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView mRecyclerView;
    private YoutubeListAdapter mAdapter;

    private YouTubeSearchResponse mResponse;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new YoutubeListAdapter();
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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestYoutubeSearch();
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
                if (mResponse != null && mResponse.getNextPageToken() != null) {
                    request.setPageToken(mResponse.getNextPageToken());
                }

                Object response = request.createRequest();
                if (response instanceof YouTubeSearchResponse) {
                    mResponse = (YouTubeSearchResponse) response;
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
}