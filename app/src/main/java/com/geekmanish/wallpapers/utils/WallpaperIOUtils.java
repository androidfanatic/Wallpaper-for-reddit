package com.geekmanish.wallpapers.utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.wallpaper.WallpaperActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WallpaperIOUtils {

    private static String dir = Environment.getExternalStorageDirectory().toString() + "/mwallpaper/";
    private static String MIME_TYPE = "image/png";
    private static String IMAGE_EXTENSION = ".png";

    public static void saveBitmap(final Bitmap bmp, final Wallpaper wallpaper) {
        new Thread() {
            @Override
            public void run() {
                String path = dir + wallpaper.getFileName();
                File folder = new File(dir);
                // folder exists or create it
                // and then check if file exists
                if ((folder.exists() || folder.mkdirs()) && !exists(path)) {
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(path);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                        Log.e("O", "saved");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeFile(path);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static void setWallpaper(final Activity activity, Wallpaper wallpaper) {
        try {
            String path = dir + wallpaper.getFileName();
            System.out.println(path);
            Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            intent.putExtra("mimeType", "image/*");
            activity.startActivity(Intent.createChooser(intent, "Set As Wallpaper:"));
        } catch (ActivityNotFoundException anf) {
            setWallpaperAlt(activity, wallpaper);
        }
    }

    public static void setWallpaperAlt(final Activity activity, Wallpaper wallpaper) {
        final String path = dir + wallpaper.getFileName();
        new Thread() {
            @Override
            public void run() {
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(activity.getApplicationContext());
                try {
                    Bitmap bitmap = WallpaperIOUtils.getBitmap(path);
                    if (bitmap != null) {
                        myWallpaperManager.setBitmap(bitmap);
                    }
                    ((WallpaperActivity) activity).msg("Wallpaper Set!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static String getPath(Wallpaper wallpaper) {
        return dir + wallpaper.getFileName();
    }

    public static void share(Activity activity, Wallpaper wallpaper) {
        String path = WallpaperIOUtils.getPath(wallpaper);
        System.out.println("PATH:" + path);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(MIME_TYPE);
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        activity.startActivity(Intent.createChooser(share, "Share Wallpaper"));
    }
}