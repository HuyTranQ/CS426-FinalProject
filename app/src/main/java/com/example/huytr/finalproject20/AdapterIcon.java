package com.example.huytr.finalproject20;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by huytr on 23-08-2015.
 */
public class AdapterIcon extends RecyclerView.Adapter<AdapterIcon.IconHolder> {

    Activity activity;
    int[] data;
    int position;

    public AdapterIcon(Activity activity,
                       int[] data)
    {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return super.getItemViewType(position);
    }

    @Override
    public AdapterIcon.IconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconHolder(LayoutInflater.from(activity).inflate(R.layout.item_icon , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterIcon.IconHolder holder, int position) {
        Picasso.with(activity).load(data[position]).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class IconHolder extends RecyclerView.ViewHolder {

        ImageView icon;

        public IconHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.Icon);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCategoryAdd.offset = getAdapterPosition();
                    activity.onBackPressed();
                }
            });
        }
    }
}
