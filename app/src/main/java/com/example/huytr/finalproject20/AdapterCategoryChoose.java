package com.example.huytr.finalproject20;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by huytr on 24-08-2015.
 */
public class AdapterCategoryChoose extends RecyclerView.Adapter<AdapterCategoryChoose.CategoryHolder> {

    Activity activity;
    ArrayList<CategoryWrapper> data = new ArrayList<>();
    int backgroundResource;
    int type;
    int position;

    public AdapterCategoryChoose(Activity activity,
                                 int type)
    {
        this.type = type;
        this.activity = activity;
        DigestCategories();
    }

    public void DigestCategories()
    {
        if (Category.list == null)
            return;
        data = new ArrayList<>();
        backgroundResource = (type == 0) ? R.drawable.background_expense : R.drawable.background_income;
        for (HashMap.Entry<Long , Category> entry : Category.list.entrySet())
            if (type == entry.getValue().type)
                data.add(new CategoryWrapper(entry.getValue() , entry.getKey()));
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return super.getItemViewType(position);
    }

    @Override
    public AdapterCategoryChoose.CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(activity).inflate(R.layout.item_category_choose , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterCategoryChoose.CategoryHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        public CategoryHolder(View itemView) {
            super(itemView);

            Category current = data.get(position).category;
            Picasso.with(activity).load(Category.rootResource[current.category][current.offset]).into((ImageView) itemView.findViewById(R.id.Avatar));
            Picasso.with(activity).load(backgroundResource).into((CircleImageView) itemView.findViewById(R.id.Background));
            ((TextView) itemView.findViewById(R.id.Title)).setText(current.name);
            itemView.findViewById(R.id.LinearLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityTransactionAdd.chosen = true;
                    ActivityTransactionAdd.idCategory = data.get(getAdapterPosition()).id;
                    activity.onBackPressed();
                }
            });
        }
    }
}
