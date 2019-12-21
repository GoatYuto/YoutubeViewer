package com.goat.youtubeviewer.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goat.youtubeviewer.R;
import com.goat.youtubeviewer.network.YouTubeSearchResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class YoutubeListAdapter extends RecyclerView.Adapter<YoutubeListAdapter.YoutubeListViewHolder> {

    private ArrayList<YouTubeSearchResponse.Item> mDataList;

    public void addData(YouTubeSearchResponse response) {
        if(mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(Arrays.asList(response.getItems()));
    }

    @NonNull
    @Override
    public YoutubeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_youtube_search_list, parent,false);
        return new YoutubeListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeListViewHolder holder, int position) {
        YouTubeSearchResponse.Item item = mDataList.get(position);
        YouTubeSearchResponse.Item.Snippet snippet = item.getSnippet();
        Uri uri = Uri.parse(snippet.getThumbnails().getDefaultThumbnail().getUrl());
        Glide.with(holder.mThumbnailView).load(uri).into(holder.mThumbnailView);
        holder.mVideoTitle.setText(snippet.getTitle());

    }

    @Override
    public int getItemCount() {

        return mDataList != null ? mDataList.size() : 0;
    }

    public static class YoutubeListViewHolder extends RecyclerView.ViewHolder {
        TextView mVideoTitle;
        ImageView mThumbnailView;

        public YoutubeListViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoTitle = itemView.findViewById(R.id.video_title);
            mThumbnailView = itemView.findViewById(R.id.image_thumbnail);
        }
    }
}
