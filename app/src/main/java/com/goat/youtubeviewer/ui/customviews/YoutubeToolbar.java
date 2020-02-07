package com.goat.youtubeviewer.ui.customviews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.goat.youtubeviewer.R;

public class YoutubeToolbar extends Toolbar {

    SearchView mSearchView;

    public YoutubeToolbar(Context context) {
        this(context, null);

    }

    public YoutubeToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public YoutubeToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSearchView = findViewById(R.id.search_view);

    }

    public void setSearchViewVisibilty(int visibilty) {
        mSearchView.setVisibility(visibilty);
    }

    public void setOnQueryTextListener(SearchView.OnQueryTextListener listener) {
        mSearchView.setOnQueryTextListener(listener);
    }

}
