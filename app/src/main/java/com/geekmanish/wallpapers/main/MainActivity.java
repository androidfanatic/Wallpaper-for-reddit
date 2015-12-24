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
import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.wallpaper.WallpaperActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, MainView {

    @Bind(R.id.rv_wallpapers) RecyclerView wallpaperRecyclerView;
    @Bind(R.id.srl_main) SwipeRefreshLayout swipeRefreshLayout;
    private MainPresenter presenter;
    private WallpaperAdapter wallpaperAdapter;
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

    @Override public void onRefresh() {
        presenter.fetchWallpapers();
        wallpaperAdapter.refresh();
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
        Intent intent = new Intent(this, WallpaperActivity.class);
        intent.putExtra("id", wallpaper.getId());
        startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}