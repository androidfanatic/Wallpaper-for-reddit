package com.geekmanish.wallpapers.wallpaper;

import com.geekmanish.wallpapers.models.Wallpaper;

public class WallpaperPresenter {
    private WallpaperView view;

    public WallpaperPresenter(WallpaperView view) {
        this.view = view;
    }

    public void displayById(long id) {
        Wallpaper wallpaper = null;
        try {
            wallpaper = Wallpaper.findById(Wallpaper.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wallpaper != null) {
            view.setWallpaper(wallpaper);
        }
    }
}
