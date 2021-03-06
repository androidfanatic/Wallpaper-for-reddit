package com.geekmanish.wallpapers.main;

import com.geekmanish.wallpapers.models.Wallpaper;

public interface MainView {
    void setAdapter(WallpaperAdapter wallpaperAdapter);

    void onFetchComplete();

    void onWallpaperItemClick(Wallpaper wallpaper);

    void startWallpaperActivity(Wallpaper wallpaper);
}
