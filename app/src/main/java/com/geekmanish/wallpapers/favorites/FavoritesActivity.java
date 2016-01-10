package com.geekmanish.wallpapers.favorites;

import android.os.Bundle;
import android.view.Menu;

import com.geekmanish.wallpapers.main.MainActivity;

public class FavoritesActivity extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Update adapter
        setAdapter(new FavoritesWallpaperAdapter(this));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Dont create a menu
        //return super.onCreateOptionsMenu(menu);
        return false;
    }
}
