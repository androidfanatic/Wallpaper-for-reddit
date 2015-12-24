package com.geekmanish.wallpapers.wallpaper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.geekmanish.wallpapers.R;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WallpaperActivity extends AppCompatActivity implements
        WallpaperView {

    @Bind(R.id.progress_wheel) ProgressWheel progressWheel;
    @Bind(R.id.ssiv_wallpaper) SubsamplingScaleImageView wallpaperView;
    private WallpaperPresenter wallpaperPresenter;
    private Target wallpaperTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wallpaperPresenter = new WallpaperPresenter(this);

        setContentView(R.layout.activity_wallpaper);
        ButterKnife.bind(this);

        wallpaperView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);

        wallpaperTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                wallpaperView.setImage(ImageSource.bitmap(bitmap));
                hideProgressWheel();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                onError(getString(R.string.wp_fetch_error));
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get("id") != null) {
            long id = (long) bundle.get("id");
            wallpaperPresenter.displayById(id);
        } else {
            onError(getString(R.string.wp_null_error));
        }
    }

    @Override public void onError(String s) {
        msg(s);
        wallpaperView.setImage(ImageSource.resource(R.drawable.ic_error));
    }

    @Override public void msg(final String s) {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override public void setWallpaper(Wallpaper wallpaper) {
        Picasso.with(this).load(wallpaper.getFullUrl()).into(wallpaperTarget);
    }

    @Override public void hideProgressWheel() {
        progressWheel.setVisibility(View.INVISIBLE);
    }
}
