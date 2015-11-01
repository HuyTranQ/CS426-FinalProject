package com.example.huytr.finalproject20;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by huytr on 23-08-2015.
 */
public class AdapterTag extends RecyclerView.Adapter<AdapterTag.TahHolder> {

    Context context;
    ArrayList<Transaction> data;
    int position;

    public AdapterTag(Context context,
                      ArrayList<Transaction> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return super.getItemViewType(position);
    }

    @Override
    public AdapterTag.TahHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TahHolder(LayoutInflater.from(context).inflate(R.layout.item_tag , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterTag.TahHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TahHolder extends RecyclerView.ViewHolder {
        public TahHolder(View itemView) {
            super(itemView);

            Transaction current = data.get(position);
            Category temp = Category.list.get(current.category);
            Picasso.with(context).load(Category.rootResource[temp.category][temp.offset]).into((ImageView) itemView.findViewById(R.id.Tag));
            int background = (temp.type == 0) ? R.drawable.background_expense : R.drawable.background_income;
            Picasso.with(context).load(background).into((CircleImageView) itemView.findViewById(R.id.Background));
            itemView.findViewById(R.id.FrameLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
