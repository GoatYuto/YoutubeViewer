package com.example.myapplication.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;

import com.google.android.youtube.player.YouTubeStandalonePlayer;


public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        return inflater.inflate(R.layout.fragment_setting, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), BuildConfig.YOUTUBE_API_KEY,"uYzS7esYB_A");
        startActivity(intent);
    }
}

