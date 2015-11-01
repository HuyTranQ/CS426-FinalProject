package com.example.huytr.finalproject20;


import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStackedBarChart extends Fragment {

    BarChart barChart;

    public FragmentStackedBarChart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stacked_bar_chart, container, false);

        barChart = (BarChart) view.findViewById(R.id.BarChart);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(31);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return Wallet.current.currency + " " + NumberFormat.getNumberInstance(Locale.US).format((int) value);
            }
        });
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        (new LoadBarData()).execute();

        return view;
    }

    private class LoadBarData extends AsyncTask<Void , Void , Void>
    {
        BarData data;

        @Override
        protected Void doInBackground(Void... params) {

            String query = "SELECT * FROM [Transaction] WHERE [wallet] = '" + Wallet.current.name + "' AND [year] = " + Year.current.value + ";";
            Cursor cursor = DatabaseUtility.database.rawQuery(query , null);
            cursor.moveToPosition(-1);
            int money , month;
            long category;
            int[] income = new int[12];
            int[] expense = new int[12];
            ArrayList<String> listMonth = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 12; ++i)
            {
                income[i] = 0;
                expense[i] = 0;
                calendar.set(Calendar.MONTH , i);
                listMonth.add(dateFormat.format(calendar.getTime()));
            }

            while (cursor.moveToNext())
            {
                money = cursor.getInt(cursor.getColumnIndex("money"));
                month = cursor.getInt(cursor.getColumnIndex("month"));
                category = cursor.getLong(cursor.getColumnIndex("category"));
                if (Category.list.get(category).type == 0)
                    expense[month] += money;
                else
                    income[month] += money;
            }

            ArrayList<BarEntry> listMoney = new ArrayList<>();
            for (int i = 0; i < 12; ++i)
                listMoney.add(new BarEntry(new float[] {income[i] , expense[i]} , i));

            BarDataSet dataSet = new BarDataSet(listMoney , "Income/Expense Statistics");
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#8bc34a"));
            colors.add(Color.parseColor("#f44336"));
            dataSet.setColors(colors);

            ArrayList<BarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            data = new BarData(listMonth , dataSets);
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return Wallet.current.currency + " " + NumberFormat.getNumberInstance(Locale.US).format((int) value);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            barChart.setData(data);
            barChart.invalidate();
        }
    }

}
