package com.geekmanish.wallpapers.favorites;

import android.os.Bundle;

import com.geekmanish.wallpapers.main.MainActivity;

public class FavoritesActivity extends MainActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapter(new FavoritesWallpaperAdapter(this));
    }
}
