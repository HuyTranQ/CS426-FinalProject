package com.example.huytr.finalproject20;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIcon extends Fragment {


    public FragmentIcon() {
        // Required empty public constructor
    }

    public static FragmentIcon Create(int index)
    {
        FragmentIcon fragment = new FragmentIcon();
        Bundle bundle = new Bundle();
        bundle.putInt("index" , index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_icon, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 3));
        recyclerView.setAdapter(new AdapterIcon(getActivity() , Category.rootResource[getArguments().getInt("index")]));
        return view;
    }

}
