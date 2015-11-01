package com.example.huytr.finalproject20;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by huytr on 25-08-2015.
 */
public class AdapterDayView extends RecyclerView.Adapter<AdapterDayView.TransactionHolder> {

    Context context;
    ArrayList<Transaction> data;

    public AdapterDayView(Context context,
                          ArrayList<Transaction> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public AdapterDayView.TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionHolder(LayoutInflater.from(context).inflate(R.layout.item_transaction , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterDayView.TransactionHolder holder, int position) {
        Transaction currentTransaction = data.get(position);
        Category currentCategory = Category.list.get(currentTransaction.category);
        holder.money.setText(Wallet.current.currency + " " + String.valueOf(currentTransaction.money));
        holder.category.setText(currentCategory.name);
        holder.description.setText(currentTransaction.description);
        Picasso.with(context).load(Category.rootResource[currentCategory.category][currentCategory.offset]).into(holder.avatar);
        if (currentCategory.type == 0)
        {
            holder.money.setTextColor(context.getResources().getColorStateList(R.color.colorExpenseDark));
            holder.category.setTextColor(context.getResources().getColorStateList(R.color.colorExpenseDark));
            Picasso.with(context).load(R.drawable.background_expense).into(holder.background);
        }
        else
        {
            holder.money.setTextColor(context.getResources().getColorStateList(R.color.colorIncomeDark));
            holder.category.setTextColor(context.getResources().getColorStateList(R.color.colorIncomeDark));
            Picasso.with(context).load(R.drawable.background_income).into(holder.background);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder {

        TextView money , category , description;
        ImageView avatar;
        CircleImageView background;

        public TransactionHolder(View itemView) {
            super(itemView);

            money = (TextView) itemView.findViewById(R.id.Money);
            category = (TextView) itemView.findViewById(R.id.Category);
            description = (TextView) itemView.findViewById(R.id.Description);
            avatar = (ImageView) itemView.findViewById(R.id.Avatar);
            background = (CircleImageView) itemView.findViewById(R.id.Background);

            itemView.findViewById(R.id.Edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.findViewById(R.id.Delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String[] args = {String.valueOf(data.get(position).id)};
                    if (0 != DatabaseUtility.database.delete("[Transaction]" , "[id] = ?" , args))
                    {
                        data.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            itemView.findViewById(R.id.Location).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Transaction chosen = data.get(getAdapterPosition());
                    Intent intent = new Intent(context , ActivityLocationView.class);
                    intent.putExtra("lat" , chosen.location.lat);
                    intent.putExtra("lng" , chosen.location.lng);
                    intent.putExtra("money" , chosen.money);
                    intent.putExtra("category" , Category.list.get(chosen.category).name);
                    context.startActivity(intent);
                }
            });
        }
    }
}
