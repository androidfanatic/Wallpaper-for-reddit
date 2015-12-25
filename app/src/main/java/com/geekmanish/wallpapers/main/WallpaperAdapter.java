package com.geekmanish.wallpapers.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geekmanish.wallpapers.R;
import com.geekmanish.wallpapers.models.Wallpaper;
import com.geekmanish.wallpapers.views.GridImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.Holder> {

    protected List<Wallpaper> wallpapers;
    private MainView view;

    public WallpaperAdapter(MainView view) {
        this.view = view;
        refresh();
    }

    public void refresh() {
        wallpapers = Wallpaper.listAll();
        notifyDataSetChanged();
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(view);
    }

    @Override public void onBindViewHolder(Holder holder, int position) {
        Picasso.with(holder.imageView.getContext())
                .load(wallpapers.get(position).getIconUrl())
                .into(holder.imageView);
        holder.placeholder.setVisibility(View.INVISIBLE);
    }

    @Override public int getItemCount() {
        return wallpapers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_wallpaper) GridImageItem imageView;
        @Bind(R.id.iv_placeholder) ImageView placeholder;

        public Holder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_wallpaper) public void wpClick(View view1) {
            view.onWallpaperItemClick(wallpapers.get(getLayoutPosition()));
        }
    }
}

