package com.geekmanish.wallpapers.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

public class Wallpaper extends SugarRecord {

    @Unique private String imgurId;

    public Wallpaper() {

    }

    public Wallpaper(String imgurId) {
        this.imgurId = imgurId;
    }

    public static List<Wallpaper> listAll() {
        return Wallpaper.listAll(Wallpaper.class, "id DESC");
    }

    public String getImgurId() {
        return imgurId;
    }

    public void setImgurId(String imgurId) {
        this.imgurId = imgurId;
    }

    public String getIconUrl() {
        return String.format("http://i.imgur.com/%sm.jpg", imgurId);
    }

    public String getFullUrl() {
        return String.format("http://i.imgur.com/%s.jpg", imgurId);
    }

    @Override public long save() {
        try {
            return Wallpaper.find(Wallpaper.class, "imgur_id = ?", imgurId).get(0).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }
}
