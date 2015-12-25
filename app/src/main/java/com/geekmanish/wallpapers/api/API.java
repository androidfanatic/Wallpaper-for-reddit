package com.geekmanish.wallpapers.api;

import com.geekmanish.wallpapers.main.MainPresenter;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class API implements Callback {

    private final String URL = "http://www.reddit.com/r/wallpapers.json";
    private MainPresenter presenter;

    public API(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void loadWallpapers() {
        new OkHttpClient().newCall(new Request.Builder().url(URL).build()).enqueue(this);
    }

    @Override public void onResponse(Response response) throws IOException {
        try {
            String jsonStr = response.body().string();
            JSONArray children = new JSONObject(jsonStr).getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                String url = (String) children.getJSONObject(i).getJSONObject("data").get("url");
                if (url.matches("(?i)(^https?://(i\\.)?imgur\\.com/([A-Za-z0-9\\.]+?)$)")) {
                    int slash = url.lastIndexOf("/") + 1;
                    int dot = url.lastIndexOf(".");
                    if (slash < url.length()) {
                        String id;
                        if (dot > slash) {
                            id = url.substring(slash, dot);
                        } else {
                            id = url.substring(slash);
                        }
                        new Wallpaper(id).saveUnique();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        presenter.onFetchComplete();
    }

    @Override public void onFailure(Request request, IOException e) {

    }
}