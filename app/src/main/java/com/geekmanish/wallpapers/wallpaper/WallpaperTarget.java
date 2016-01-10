package com.geekmanish.wallpapers.wallpaper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.geekmanish.wallpapers.R;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.utils.WallpaperIOUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class WallpaperTarget implements Target {


    private Wallpaper wallpaper;
    private WallpaperView view;

    public WallpaperTarget(WallpaperView view, Wallpaper wallpaper) {
        this.view = view;
        this.wallpaper = wallpaper;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        WallpaperIOUtils.saveBitmap(bitmap, wallpaper);
        view.setWallpaperImage(ImageSource.bitmap(bitmap));
        view.setLoaded(true);
        view.hideProgressWheel();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        view.onError(R.string.wp_fetch_error);
        view.hideProgressWheel();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
