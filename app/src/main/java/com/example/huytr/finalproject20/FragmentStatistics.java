package com.example.huytr.finalproject20;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStatistics extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public FragmentStatistics() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        ActivityMain.SetUpToolbar("Statistics" , (Toolbar) view.findViewById(R.id.Toolbar));
        setHasOptionsMenu(true);

        viewPager = (ViewPager) view.findViewById(R.id.ViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.TabLayout);

        viewPager.setAdapter(new AdapterStatistics(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_statistics_annual , menu);

    }

    private class AdapterStatistics extends FragmentStatePagerAdapter {
        public AdapterStatistics(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (position == 0) ? new FragmentStackedBarChart() : new FragmentPieChart();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "Income/Expense" : "Categories";
        }
    }

}
