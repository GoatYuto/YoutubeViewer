package com.goat.youtubeviewer;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.goat.youtubeviewer.ui.customviews.YoutubeToolbar;
import com.goat.youtubeviewer.utills.DebugLog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    YoutubeToolbar mToolbar;
    private SearchViewListener mListener;
    private NavController.OnDestinationChangedListener mOnDestinationChangeListener = new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            DebugLog.d(TAG, "[onDestinationChanged]");
            switch (destination.getId()) {
                case R.id.navigation_home:
                    mToolbar.setSearchViewVisibilty(View.VISIBLE);
                    break;
                case R.id.navigation_dashboard:
                    mToolbar.setSearchViewVisibilty(View.GONE);
                    break;
                case R.id.navigation_notifications:
                    mToolbar.setSearchViewVisibilty(View.GONE);
                    break;
                case R.id.navigation_setting:
                    mToolbar.setSearchViewVisibilty(View.GONE);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);

        mToolbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mToolbar.clearFocus();
                if (mListener != null) {
                    mListener.onQuery(query);
                }
                DebugLog.d(TAG, "[onQueryTextSubmit] query " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(mOnDestinationChangeListener);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public void setListener(SearchViewListener listener) {
        mListener = listener;
    }

    public interface SearchViewListener {
        public void onQuery(String query);
    }

}
