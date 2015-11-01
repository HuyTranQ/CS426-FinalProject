package com.example.huytr.finalproject20;


import android.content.Intent;
import android.os.Build;
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
import android.view.Window;
import android.view.WindowManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {

    FloatingActionButton button;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    public static int counter = 0;
    public View view;

    public FragmentCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.Toolbar);
        ActivityMain.SetUpToolbar("Categories" , toolbar);

        button = (FloatingActionButton) view.findViewById(R.id.ActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , ActivityCategoryAdd.class);
                intent.putExtra("type", counter);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.ViewPager);
        viewPager.setAdapter(new AdapterCategory(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            float oldOffset = 1f;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset < oldOffset) {
                    if (oldOffset - positionOffset < 0.0001f)
                        return;
                    oldOffset = positionOffset;
                    positionOffset = 1 - positionOffset;
                } else {
                    if (positionOffset - oldOffset < 0.0001f)
                        return;
                    oldOffset = positionOffset;
                }
                if (positionOffset < 0.2f)
                    button.hide();
                if (positionOffset > 0.9f)
                    button.show();
            }

            @Override
            public void onPageSelected(int position) {
                counter = position;
                ChangeTheme();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(counter);
            }
        });

        ChangeTheme();

        return view;
    }

    public void ChangeTheme()
    {
        int colorPrimary , colorDark;
        if (counter == 0)
        {
            colorPrimary = R.color.colorExpense;
            colorDark = R.color.colorExpenseDark;
        }
        else
        {
            colorPrimary = R.color.colorIncome;
            colorDark = R.color.colorIncomeDark;
        }
        toolbar.setBackgroundResource(colorPrimary);
        tabLayout.setBackgroundResource(colorPrimary);
        button.setBackgroundTintList(getResources().getColorStateList(colorPrimary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(colorDark));
        }
    }

    private class AdapterCategory extends FragmentStatePagerAdapter {

        public AdapterCategory(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentCategoryTab.Create(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (position == 0) ? "Expense" : "Income";
        }
    }
}
