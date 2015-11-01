package com.example.huytr.finalproject20;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by huytr on 22-08-2015.
 */
public class AdapterWallets extends RecyclerView.Adapter<AdapterWallets.WalletHolder> {

    Context context;
    ArrayList<Wallet> data;
    int position;

    public AdapterWallets(Context context,
                          ArrayList<Wallet> data)
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
        public AdapterWallets.WalletHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WalletHolder(LayoutInflater.from(context).inflate(R.layout.item_wallet , viewGroup , false));
        }

        @Override
        public void onBindViewHolder(AdapterWallets.WalletHolder viewHolder, int i) {
            Wallet current = data.get(i);
            viewHolder.title.setText(current.name);
        viewHolder.description.setText(current.description);
        Picasso.with(context).load(Wallet.listAvatar[current.avatar % 3]).into(viewHolder.avatar);
        if (current.balance < 0)
        {
            viewHolder.balance.setText(String.valueOf(-current.balance));
            viewHolder.balance.setTextColor(context.getResources().getColorStateList(R.color.colorExpenseDark));
        }
        else if (current.balance > 0)
        {
            viewHolder.balance.setText(String.valueOf(current.balance));
            viewHolder.balance.setTextColor(context.getResources().getColorStateList(R.color.colorIncomeDark));
        }
    }

    @Override
    public int getItemCount() {
        return (this.data == null) ? 0 : data.size();
    }

    public class WalletHolder extends RecyclerView.ViewHolder {

        TextView balance , title , description;
        ImageView avatar;

        public WalletHolder(View itemView) {
            super(itemView);

            balance = (TextView) itemView.findViewById(R.id.Balance);
            title = (TextView) itemView.findViewById(R.id.Title);
            description = (TextView) itemView.findViewById(R.id.Description);
            avatar = (ImageView) itemView.findViewById(R.id.Avatar);

            ((TextView) itemView.findViewById(R.id.Currency)).setText(data.get(position).currency);
            itemView.findViewById(R.id.Edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            itemView.findViewById(R.id.Delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Wallet.list.size() == 1)
                        return;
                    int position = getAdapterPosition();
                    String[] args = {data.get(position).name};
                    DatabaseUtility.database.delete("Wallet", "[name] = ?", args);
                    DatabaseUtility.database.delete("[Transaction]" , "[wallet] = ?" , args);
                    data.remove(position);
                    Wallet.current = Wallet.list.get(0);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
