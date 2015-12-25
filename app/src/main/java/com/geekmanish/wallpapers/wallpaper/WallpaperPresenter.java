package com.geekmanish.wallpapers.wallpaper;

import com.geekmanish.wallpapers.models.Wallpaper;

public class WallpaperPresenter {
    private WallpaperView view;
    private Wallpaper wallpaper;

    public WallpaperPresenter(WallpaperView view) {
        this.view = view;
    }

    public void setWallpaperById(long id) {
        Wallpaper wallpaper = null;
        try {
            wallpaper = Wallpaper.findById(Wallpaper.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wallpaper != null) {
            setWallpaper(wallpaper);
        }
    }

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public void toggleFavorite() {
        wallpaper.setFavorite(!wallpaper.isFavorite());
        wallpaper.save();
        view.setFavoriteIcon(wallpaper.isFavorite());
        view.onFavIconSet(wallpaper.isFavorite());
    }
}
