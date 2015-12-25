package com.geekmanish.wallpapers.wallpaper;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.geekmanish.wallpapers.models.Wallpaper;

public interface WallpaperView {
    void setupNav();

    void onError(String s);

    void msg(String s);

    void setWallpaper(Wallpaper wallpaper);

    void hideProgressWheel();

    void setWallpaperImage(ImageSource bitmap);

    void onError(int stringResId);

    void setFavoriteIcon(boolean favorite);

    void onFavIconSet(boolean favorite);
}
