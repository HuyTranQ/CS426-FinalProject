package com.example.huytr.finalproject20;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransactions extends Fragment {

    FloatingActionButton button;
    TabLayout tabLayout;
    ViewPager viewPager;

    public FragmentTransactions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        if (Wallet.current == null)
            ActivityMain.SetUpToolbar("Wallet not found!" , (Toolbar) view.findViewById(R.id.Toolbar));
        else
        {
            ActivityMain.SetUpToolbar("Overview" , (Toolbar) view.findViewById(R.id.Toolbar));

            button = (FloatingActionButton) view.findViewById(R.id.ActionButton);
            tabLayout = (TabLayout) view.findViewById(R.id.TabLayout);
            viewPager = (ViewPager) view.findViewById(R.id.ViewPager);

            viewPager.setAdapter(new AdapterViewPager(getChildFragmentManager()));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Month.current = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            tabLayout.setupWithViewPager(viewPager);
            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setCurrentItem(Month.current);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity() , ActivityTransactionAdd.class));
                }
            });
        }
        return view;
    }

    public class AdapterViewPager extends FragmentStatePagerAdapter {

        public AdapterViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentOverview.Create(position);
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return new DateFormatSymbols().getMonths()[position];
        }
    }
}
