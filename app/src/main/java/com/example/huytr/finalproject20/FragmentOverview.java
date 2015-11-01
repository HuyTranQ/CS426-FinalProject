package com.example.huytr.finalproject20;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOverview extends Fragment {

    AdapterOverview adapter;
    int month;
    ArrayList<Transaction>[] data;

    public FragmentOverview() {
        // Required empty public constructor
    }

    public static FragmentOverview Create(int month)
    {
        FragmentOverview fragment = new FragmentOverview();
        Bundle bundle = new Bundle();
        bundle.putInt("month" , month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        month = getArguments().getInt("month");
        data = new ArrayList[DaysOfMonth()];
        for (int i = 0; i < data.length; ++i)
            data[i] = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterOverview(getActivity() , null);
        recyclerView.setAdapter(adapter);

        (new LoadTransactions()).execute();

        return view;
    }

    public int DaysOfMonth()
    {
        switch (month)
        {
            case 4:case 6:case 9:case 11:
            return 30;
            case 2:
                int year = Year.current.value;
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                    return 29;
                else
                    return 28;
            default:
                return 31;
        }
    }

    public class LoadTransactions extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... params) {

            String query = "SELECT * FROM [Transaction] WHERE [wallet] = '" + Wallet.current.name + "' AND [year] = " + Year.current.value + " AND [month] = " + month + ";";
            Cursor cursor = DatabaseUtility.database.rawQuery(query , null);
            cursor.moveToPosition(-1);

            int day , category , money , id;
            double lat , lng;
            String description;

            while (cursor.moveToNext())
            {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                day = cursor.getInt(cursor.getColumnIndex("day"));
                category = cursor.getInt(cursor.getColumnIndex("category"));
                money = cursor.getInt(cursor.getColumnIndex("money"));
                description = cursor.getString(cursor.getColumnIndex("description"));
                lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                lng = cursor.getDouble(cursor.getColumnIndex("lng"));
                --day;
                if (data[day] == null)
                    data[day] = new ArrayList<>();
                data[day].add(new Transaction(id, category, money, description, new Coordinate(lat, lng)));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.DigestMonth(data);
        }
    }
}
