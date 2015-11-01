package com.example.huytr.finalproject20;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMain extends AppCompatActivity {

    public static AppCompatActivity activity;
    public static DrawerLayout drawerLayout;
    public static NavigationView navigationView;

    FrameLayout frameLayout;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    public static int current = 0;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        frameLayout = (FrameLayout) findViewById(R.id.FrameLayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.NavigationView);

        Picasso.with(this).load(R.drawable.background_header).into((ImageView) drawerLayout.findViewById(R.id.Background));
        Picasso.with(this).load(R.drawable.avatar).into((CircleImageView) drawerLayout.findViewById(R.id.Avatar));

        fragmentManager = getSupportFragmentManager();
        switch (current)
        {
            case 0:
                AddFragment(new FragmentWallet());
                break;
            case 1:
                AddFragment(new FragmentTransactions());
                break;
            case 2:
                AddFragment(new FragmentCategory());
                break;
            case 3:
                AddFragment(new FragmentStatistics());
                break;
            case 4:
                AddFragment(new FragmentEvents());
                break;
            case 5:
                AddFragment(new FragmentEvents());
                break;
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId())
                {
                    case R.id.MenuWallets:
                        current = 0;
                        ReplaceFragment(new FragmentWallet());
                        break;
                    case R.id.MenuTransactions:
                        current = 1;
                        ReplaceFragment(new FragmentTransactions());
                        break;
                    case R.id.MenuCategories:
                        current = 2;
                        ReplaceFragment(new FragmentCategory());
                        break;
                    case R.id.MenuStatistics:
                        current = 3;
                        ReplaceFragment(new FragmentStatistics());
                        break;
                    case R.id.MenuEvents:
                        current = 4;
                        ReplaceFragment(new FragmentEvents());
                        break;
                    case R.id.MenuSavings:
                        current = 5;
                        ReplaceFragment(new FragmentSavings());
                        break;
                    default:
                }

                return true;
            }
        });

        navigationView.getMenu().getItem(current).setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void AddFragment(Fragment fragment)
    {
        this.fragment = fragment;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FrameLayout , fragment);
        fragmentTransaction.commit();
    }

    public void ReplaceFragment(Fragment fragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout , fragment);
        fragmentTransaction.commit();
    }

    public static void SetUpToolbar(String title,
                                    Toolbar toolbar)
    {
        toolbar.setTitle(title);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null)
            Log.d("Error" , "Action Bar is null");
        else
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity , drawerLayout , toolbar , R.string.open , R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ArrayList<String> name = new ArrayList<>();
                for (int i = 0; i < Wallet.list.size(); ++i)
                    name.add(Wallet.list.get(i).name);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity , R.layout.item_spinner , name);
                Spinner spinner = (Spinner) drawerLayout.findViewById(R.id.spinner);
                arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Wallet.current = Wallet.list.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
