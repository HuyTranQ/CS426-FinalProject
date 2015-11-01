package com.example.huytr.finalproject20;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by huytr on 23-08-2015.
 */
public class AdapterOverview extends RecyclerView.Adapter<AdapterOverview.OverviewHolder> {

    Context context;
    ArrayList<DayWrapper> data = null;
    int position;
    int month;

    public AdapterOverview(Context context,
                           ArrayList<Transaction>[] rawData)
        {
            this.context = context;
            DigestMonth(rawData);
        }

    public void DigestMonth(ArrayList<Transaction>[] rawData)
    {
        data = new ArrayList<>();
        if (rawData == null)
            return;
        DayWrapper wrapper;
        data.add(new DayWrapper(0 , null));
        for (int i = rawData.length - 1; i >= 0; --i)
            if (rawData[i] != null)
            {
                wrapper = new DayWrapper(i , rawData[i]);
                data.get(0).income += wrapper.income;
                data.get(0).expense += wrapper.expense;
                data.add(wrapper);
            }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return position;
    }

    @Override
    public AdapterOverview.OverviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = (viewType == 0) ? R.layout.item_overview_month : R.layout.item_overview_day;
        return new OverviewHolder(LayoutInflater.from(context).inflate(layout , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterOverview.OverviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OverviewHolder extends RecyclerView.ViewHolder {

        public OverviewHolder(View itemView) {
            super(itemView);

            int income = data.get(position).income;
            int expense = data.get(position).expense;
            ((TextView) itemView.findViewById(R.id.Income)).setText(String.valueOf(income));
            ((TextView) itemView.findViewById(R.id.Expense)).setText(String.valueOf(expense));
            int balance = income - expense;
            TextView textView = (TextView) itemView.findViewById(R.id.Balance);
            if (balance < 0)
            {
                textView.setText(Wallet.current.currency + " " + String.valueOf(-balance));
                textView.setTextColor(context.getResources().getColorStateList(R.color.colorExpenseDark));
            }
            else
            {
                textView.setText(Wallet.current.currency + " " + String.valueOf(balance));
                textView.setTextColor(context.getResources().getColorStateList(R.color.colorIncomeDark));
            }
            if (position != 0)
            {
                Picasso.with(context).load(DayWrapper.calendar[data.get(position).value - 1]).into((ImageView) itemView.findViewById(R.id.Avatar));
                RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false));
                recyclerView.setAdapter(new AdapterTag(context , data.get(position).listTransaction));
                itemView.findViewById(R.id.CardView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Transaction.currentList = data.get(getAdapterPosition()).listTransaction;
                        context.startActivity(new Intent(context , ActivityTransactionView.class));
                    }
                });
            }
        }
    }
}
