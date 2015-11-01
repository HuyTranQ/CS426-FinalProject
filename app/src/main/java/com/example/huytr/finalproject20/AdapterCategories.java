package com.example.huytr.finalproject20;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
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
 * Created by huytr on 23-08-2015.
 */
public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.CategoryHolder> {

    Context context;
    ArrayList<CategoryWrapper> data = new ArrayList<>();
    int backgroundResource;
    int type;
    int position;

    public AdapterCategories(Context context,
                             int type)
    {
        this.context = context;
        this.type = type;
        DigestCategories();
    }

    public void DigestCategories()
    {
        data = new ArrayList<>();
        if (Category.list == null)
            return;
        backgroundResource = (type == 0) ? R.drawable.background_expense : R.drawable.background_income;
        for (HashMap.Entry<Long , Category> entry : Category.list.entrySet())
            if (entry.getValue().type == type)
                data.add(new CategoryWrapper(entry.getValue() , entry.getKey()));
        notifyDataSetChanged();
    }

    @Override
    public AdapterCategories.CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.item_category , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterCategories.CategoryHolder holder, int position) {
        Category current = data.get(position).category;
        Picasso.with(context).load(Category.rootResource[current.category][current.offset]).into(holder.avatar);
        Picasso.with(context).load(backgroundResource).into(holder.background);
        holder.title.setText(current.name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        CircleImageView background;
        TextView title;

        public CategoryHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.Avatar);
            background = (CircleImageView) itemView.findViewById(R.id.Background);
            title = (TextView) itemView.findViewById(R.id.Title);

            itemView.findViewById(R.id.Delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String[] args = {String.valueOf(data.get(position).id)};
                    DatabaseUtility.database.delete("[Category]", "[id] = ?", args);
                    Category.list.remove(data.get(position).id);
                    data.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }


}
