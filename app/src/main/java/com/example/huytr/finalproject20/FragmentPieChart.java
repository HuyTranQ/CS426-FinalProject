package com.example.huytr.finalproject20;


import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPieChart extends Fragment {

    PieChart[] pieChart = new PieChart[2];
    String[] nameChart = {"Income\nCategories" , "Expense\nCategories"};
    int[] idChart = {R.id.PieChartExpense, R.id.PieChartIncome };
    AdapterLegend[] adapter = new AdapterLegend[2] , adapterAdd = new AdapterLegend[2];
    int[] legend = {R.id.ExpenseView , R.id.IncomeView} , neglected = {R.id.AdditionalExpense , R.id.AdditionalIncome };
    String[] color = {
            "#f44336",
            "#2196f3",
            "#795548",
            "#8bc34a",
            "#607d8b",
            "#ff5722",
            "#9e9e9e",
            "#3f51b5",
            "#00bcd4",
            "#673ab7",
            "#009688",
            "#9c27b0",
            "#ffc107",
            "#e91e63",
            "#c62828",
            "#1565c0",
            "#4e342e",
            "#558b2f",
            "#37474f",
            "#d84315",
            "#424242",
            "#00838f",
            "#4527a0",
            "#00695c",
            "#6a1b9a",
            "#ff8f00",
            "#ad1457"
    };

    public FragmentPieChart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        RecyclerView recyclerView;

        PieChart tempChart;
        for (int i = 0; i < 2; ++i)
        {
            tempChart = (PieChart) view.findViewById(idChart[i]);
            tempChart.setDrawHoleEnabled(true);
            tempChart.setDrawHoleEnabled(true);
            tempChart.setHoleColorTransparent(true);
            tempChart.setTransparentCircleColor(R.color.White);
            tempChart.setHoleRadius(49f);
            tempChart.setTransparentCircleRadius(47f);
            tempChart.setDrawCenterText(true);
            tempChart.setRotationAngle(0f);
            tempChart.setRotationEnabled(true);
            tempChart.setCenterText(nameChart[i]);
            tempChart.setDescription("");
            pieChart[i] = tempChart;

            recyclerView = (RecyclerView) view.findViewById(legend[i]);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false));
            adapter[i] = new AdapterLegend(getActivity() , null , null);
            recyclerView.setAdapter(adapter[i]);

            recyclerView = (RecyclerView) view.findViewById(neglected[i]);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL , false));
            adapterAdd[i] = new AdapterLegend(getActivity() , null , null);
            recyclerView.setAdapter(adapterAdd[i]);
        }

        (new LoadPieData()).execute();

        return view;
    }

    private class LoadPieData extends AsyncTask<Void , Void , Void>
    {
        ArrayList<Integer> listColor = new ArrayList<>();

        ArrayList<Entry>[] listValue = new ArrayList[2];
        ArrayList<String>[] listName = new ArrayList[2];
        ArrayList<String>[] addValue = new ArrayList[2];
        ArrayList<Integer>[] addColor = new ArrayList[2];

        PieData[] listData = new PieData[2];
        String[] chartName = {"Income Categories" , "Expense Categories"};

        @Override
        protected Void doInBackground(Void... params) {

            String query = "SELECT * FROM [Transaction] WHERE [wallet] = '" + Wallet.current.name + "' AND [year] = " + Year.current.value + ";";
            Cursor cursor = DatabaseUtility.database.rawQuery(query , null);
            cursor.moveToPosition(-1);
            int money;
            long category;
            Category temp;

            HashMap<Long , DataWrapper>[] listCategory = new HashMap[2];

            for (int i = 0; i < color.length; ++i)
                listColor.add(Color.parseColor(color[i]));

            for (int i = 0; i < 2; ++i)
            {
                listValue[i] = new ArrayList<>();
                listName[i] = new ArrayList<>();
                listData[i] = new PieData();
                listCategory[i] = new HashMap<>();
            }

            for (HashMap.Entry<Long , Category> entry : Category.list.entrySet())
            {
                temp = entry.getValue();
                listCategory[temp.type].put(entry.getKey(), new DataWrapper(temp));
            }

            while (cursor.moveToNext())
            {
                money = cursor.getInt(cursor.getColumnIndex("money"));
                category = cursor.getLong(cursor.getColumnIndex("category"));
                listCategory[Category.list.get(category).type].get(category).money += money;
            }
            DataWrapper wrapper;
            int counter;
            int sum;
            ArrayList<String> hideName;
            PieDataSet dataSet;
            for (int i = 0; i < 2; ++i)
            {
                counter = 0;
                sum = 0;
                hideName = new ArrayList<>();
                addValue[i] = new ArrayList<>();
                addColor[i] = new ArrayList<>();
                for (HashMap.Entry<Long , DataWrapper> entry : listCategory[i].entrySet())
                {
                    wrapper = entry.getValue();
                    listValue[i].add(new Entry(wrapper.money , counter));
                    listName[i].add(wrapper.category.name);
                    hideName.add("");
                    sum += wrapper.money;
                    ++counter;
                }
                counter = 0;
                for (HashMap.Entry<Long , DataWrapper> entry : listCategory[i].entrySet())
                {
                    wrapper = entry.getValue();
                    if (wrapper.money / (float) sum < 0.1f)
                    {
                        addValue[i].add(Wallet.current.currency + " " + String.valueOf(wrapper.money));
                        addColor[i].add(listColor.get(counter));
                    }
                    ++counter;

                }
                dataSet = new PieDataSet(listValue[i] , chartName[i]);
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(listColor);
                listData[i] = new PieData(hideName , dataSet);
                listData[i].setValueFormatter(new MyValueFormatter(sum));
                listData[i].setValueTextSize(16f);
                listData[i].setValueTextColor(Color.parseColor("#FFFFFF"));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i = 0; i < 2; ++i)
            {
                pieChart[i].setData(listData[i]);
                pieChart[i].invalidate();
                pieChart[i].animateY(1000, Easing.EasingOption.EaseInOutCirc);
                pieChart[i].spin(2000, 0f, 360f, Easing.EasingOption.EaseInOutCirc);
                pieChart[i].getLegend().setEnabled(false);
                adapter[i].DigestLegend(listColor , listName[i]);
                adapterAdd[i].DigestLegend(addColor[i] , addValue[i]);
            }
        }

        private class DataWrapper
        {
            public int money;
            Category category;

            public DataWrapper(Category category)
            {
                this.category = category;
                money = 0;
            }
        }

        private class MyValueFormatter implements ValueFormatter {

            int sum;

            public MyValueFormatter(int sum)
            {
                this.sum = sum;
            }

            @Override
            public String getFormattedValue(float value) {
                if (value / sum < 0.1f)
                    return "";
                return Wallet.current.currency + " " + NumberFormat.getNumberInstance(Locale.US).format((int) value);
            }
        }
    }
}
