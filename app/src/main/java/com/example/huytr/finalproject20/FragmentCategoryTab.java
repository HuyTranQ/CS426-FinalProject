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
public class FragmentCategoryTab extends Fragment {

    View view;
    int currentType;
    AdapterCategories adapter;

    public FragmentCategoryTab() {
        // Required empty public constructor
    }

    public static FragmentCategoryTab Create(int type)
    {
        FragmentCategoryTab fragment = new FragmentCategoryTab();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_tab, container, false);

        currentType = getArguments().getInt("type");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterCategories(getActivity(), currentType);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
