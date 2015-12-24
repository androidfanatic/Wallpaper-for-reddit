package com.geekmanish.wallpapers.main;

import com.geekmanish.wallpapers.api.API;

public class MainPresenter {
    private MainView view;
    private API api;

    public MainPresenter(MainView view) {
        this.view = view;
        api = new API(this);
    }

    public void fetchWallpapers() {
        api.loadWallpapers();
    }

    public void onFetchComplete() {
        view.onFetchComplete();
    }
}
