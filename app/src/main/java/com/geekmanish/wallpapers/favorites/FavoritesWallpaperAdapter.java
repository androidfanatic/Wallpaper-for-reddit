package com.geekmanish.wallpapers.favorites;

import com.geekmanish.wallpapers.main.MainView;
import com.geekmanish.wallpapers.main.WallpaperAdapter;
import com.geekmanish.wallpapers.models.Wallpaper;

public class FavoritesWallpaperAdapter extends WallpaperAdapter {

    public FavoritesWallpaperAdapter(MainView view) {
        super(view);
    }

    @Override public void refresh() {
        // Todo fix this
        // 0 / 1 to true/false
        wallpapers = Wallpaper.find(Wallpaper.class, "favorite = ?", "1");
        System.out.println("Size: " + wallpapers.size());
        notifyDataSetChanged();
    }
}
