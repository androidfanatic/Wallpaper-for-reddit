package com.geekmanish.wallpapers.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.geekmanish.wallpapers.R;
import com.geekmanish.wallpapers.favorites.FavoritesActivity;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.wallpaper.WallpaperActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener,
        MainView {

    @Bind(R.id.rv_wallpapers) RecyclerView wallpaperRecyclerView;
    @Bind(R.id.srl_main) SwipeRefreshLayout swipeRefreshLayout;
    private WallpaperAdapter wallpaperAdapter;
    private MainPresenter presenter;
    private GridLayoutManager gridLayoutManager;
    private WallpaperItemDecoration wallpaperItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.fetchWallpapers();

        // Bind views
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);

        // Recyclerview
        gridLayoutManager = new GridLayoutManager(this, 2);
        wallpaperAdapter = new WallpaperAdapter(this);
        wallpaperItemDecoration = new WallpaperItemDecoration(this, R.dimen.item_offset);

        wallpaperRecyclerView.setLayoutManager(gridLayoutManager);
        wallpaperRecyclerView.setAdapter(wallpaperAdapter);
        wallpaperRecyclerView.addItemDecoration(wallpaperItemDecoration);
    }

    @Override public void setAdapter(WallpaperAdapter wallpaperAdapter) {
        this.wallpaperAdapter = wallpaperAdapter;
        wallpaperRecyclerView.setAdapter(wallpaperAdapter);
    }

    @Override public void onRefresh() {
        presenter.fetchWallpapers();
    }

    @Override public void onFetchComplete() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);
                wallpaperAdapter.refresh();
            }
        });
    }

    @Override public void onWallpaperItemClick(Wallpaper wallpaper) {
        startWallpaperActivity(wallpaper);
    }

    @Override public void startWallpaperActivity(Wallpaper wallpaper) {
        Intent intent = new Intent(this, WallpaperActivity.class);
        intent.putExtra("id", wallpaper.getId());
        startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorites:
                startFavActivity();
                return true;
            case R.id.action_about:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startFavActivity() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}