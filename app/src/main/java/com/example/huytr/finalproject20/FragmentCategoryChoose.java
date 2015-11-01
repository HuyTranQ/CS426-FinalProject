package com.example.huytr.finalproject20;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategoryChoose extends Fragment {

    int currentType;
    AdapterCategoryChoose adapter;

    public FragmentCategoryChoose() {
        // Required empty public constructor
    }

    public static FragmentCategoryChoose Create(int type)
    {
        FragmentCategoryChoose fragment = new FragmentCategoryChoose();
        Bundle bundle = new Bundle();
        bundle.putInt("type" , type);
        fragment.setArguments(bundle);
        return fragment;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fagment_category_choose, container, false);

            currentType = getArguments().getInt("type");

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new AdapterCategoryChoose(getActivity() , currentType);
            recyclerView.setAdapter(adapter);

            return view;
    }


}
