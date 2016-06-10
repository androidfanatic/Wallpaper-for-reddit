package com.geekmanish.wallpapers.wallpaper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.opengl.GLES10;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.geekmanish.wallpapers.R;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.utils.WallpaperIOUtils;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import javax.microedition.khronos.opengles.GL10;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WallpaperActivity extends AppCompatActivity implements
        WallpaperView {

    private static final int STORAGE_PERM = 9;
    @Bind(R.id.progress_wheel) ProgressWheel progressWheel;
    @Bind(R.id.ssiv_wallpaper) SubsamplingScaleImageView wallpaperView;
    @Bind(R.id.btn_favorite) ImageView favoriteBtn;
    @Bind(R.id.btn_setwallpaper) ImageView setWallpaperBtn;
    @Bind(R.id.btn_share) ImageView shareBtn;
    @Bind(R.id.fabtoolbar) FABToolbarLayout fabToolbarLayout;

    private WallpaperPresenter presenter;
    private WallpaperTarget wallpaperTarget;
    private Toast toast;
    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new WallpaperPresenter(this);
        setContentView(R.layout.activity_wallpaper);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get("id") != null) {
            long id = (long) bundle.get("id");
            presenter.setWallpaperById(id);
        } else {
            onFatalError();
        }

        wallpaperView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        wallpaperTarget = new WallpaperTarget(this, presenter.getWallpaper());

        setWallpaper(presenter.getWallpaper());

        // Setup nav
        setupNav();

        findViewById(R.id.fabtoolbar_fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                msg("Sajjin!");
            }
        });

        // permissions

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("Storage permission")
                        .setMessage("This app requires read/write storage permissions to function correctly. Please consider granting permission.")
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override public void onDismiss(DialogInterface dialogInterface) {
                                ActivityCompat.requestPermissions(WallpaperActivity.this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, STORAGE_PERM);
                            }
                        })
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, STORAGE_PERM);
            }
        }

    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case STORAGE_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Wohoo got it.
                }

        }
    }

    private void onFatalError() {
        // Fatal error, finish
        msg(getString(R.string.wp_null_error));
        onBackPressed();
        finish();
    }

    @Override public void setupNav() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override public void onError(String s) {
        msg(s);
        wallpaperView.setImage(ImageSource.resource(R.drawable.ic_error));
    }

    @Override public void msg(final String s) {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override public void setWallpaper(Wallpaper wallpaper) {
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        Picasso.with(this)
                .load(wallpaper.getFullUrl())
                .into(wallpaperTarget);
        setFavoriteIcon(wallpaper.isFavorite());
    }

    @Override public void hideProgressWheel() {
        progressWheel.setVisibility(View.INVISIBLE);
    }

    @Override public void setWallpaperImage(ImageSource bitmap) {
        wallpaperView.setImage(bitmap);
    }

    @Override public void onError(int stringResId) {
        onError(getString(stringResId));
    }

    @OnClick(R.id.btn_share) public void share(View view) {
        if (isLoaded()) {
            WallpaperIOUtils.share(this, presenter.getWallpaper());
        } else {
            msg("Waiting for wallpaper to load.");
        }
    }

    @OnClick(R.id.btn_setwallpaper) public void setWallpaper(View view) {
        if (isLoaded()) {
            WallpaperIOUtils.setWallpaper(this, presenter.getWallpaper());
        } else {
            msg("Waiting for wallpaper to load.");
        }
    }

    @OnClick(R.id.btn_favorite) public void favorite(View view) {
        if (isLoaded()) {
            presenter.toggleFavorite();
        } else {
            msg("Waiting for wallpaper to load.");
        }
    }

    @Override public void onBackPressed() {
        if (fabToolbarLayout.isToolbar()) {
            fabToolbarLayout.hide();
        } else {
            super.onBackPressed();
        }
    }

    @Override public void setFavoriteIcon(boolean favorite) {
        if (favorite) {
            favoriteBtn.setImageResource(R.drawable.ic_favorite);
        } else {
            favoriteBtn.setImageResource(R.drawable.ic_favorite_o);
        }
    }

    @Override public void onFavIconSet(boolean favorite) {
        if (favorite) {
            msg(getString(R.string.fav_added));
        } else {
            msg(getString(R.string.fav_removed));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}