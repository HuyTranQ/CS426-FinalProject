package com.example.huytr.finalproject20;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by huytr on 27-08-2015.
 */
public class AdapterLegend extends RecyclerView.Adapter<AdapterLegend.LegendHolder>{

    Context context;
    ArrayList<Integer> listColor;
    ArrayList<String> listName;

    public AdapterLegend(Context context,
                         ArrayList<Integer> listColor,
                         ArrayList<String> listName)
    {
        this.context = context;
        this.listColor = listColor;
        this.listName = listName;
    }

    public void DigestLegend(ArrayList<Integer> listColor,
                        ArrayList<String> listName)
    {
        this.listColor = listColor;
        this.listName = listName;
        notifyDataSetChanged();
    }

    @Override
    public AdapterLegend.LegendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LegendHolder(LayoutInflater.from(context).inflate(R.layout.item_legend , parent , false));
    }

    @Override
    public void onBindViewHolder(AdapterLegend.LegendHolder holder, int position) {
        holder.color.setBackgroundColor(listColor.get(position));
        holder.name.setText(listName.get(position));
    }

    @Override
    public int getItemCount() {
        if (listName == null)
            return 0;
        return listName.size();
    }

    public class LegendHolder extends RecyclerView.ViewHolder {

        View color;
        TextView name;

        public LegendHolder(View itemView) {
            super(itemView);

            color = itemView.findViewById(R.id.LegendColor);
            name = (TextView) itemView.findViewById(R.id.LegendName);
        }
    }
}
