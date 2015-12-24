package com.geekmanish.wallpapers.wallpaper;

import com.geekmanish.wallpapers.models.Wallpaper;

public interface WallpaperView {
    void onError(String s);

    void msg(String s);

    void setWallpaper(Wallpaper wallpaper);

    void hideProgressWheel();
}
