package com.geekmanish.wallpapers.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.List;

public class Wallpaper extends SugarRecord {

    @Ignore private static final String baseUrl = "http://i.imgur.com";
    @Ignore private static final String extension = "png";

    @Unique private String imgurId;
    private boolean favorite = false;

    public Wallpaper() {

    }

    public Wallpaper(String imgurId) {
        this.imgurId = imgurId;
    }

    public static List<Wallpaper> listAll() {
        return Wallpaper.listAll(Wallpaper.class, "id DESC");
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getImgurId() {
        return imgurId;
    }

    public void setImgurId(String imgurId) {
        this.imgurId = imgurId;
    }

    public String getIconUrl() {
        return String.format("%s/%sm.%s", baseUrl, imgurId, extension);
    }

    public String getFullUrl() {
        return String.format("%s/%s.%s", baseUrl, imgurId, extension);
    }

    public String getFileName() {
        return String.format("%s.%s", imgurId, extension);
    }

    public long saveUnique() {
        try {
            return Wallpaper.find(Wallpaper.class, "imgur_id = ?", imgurId).get(0).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.save();
    }
}
