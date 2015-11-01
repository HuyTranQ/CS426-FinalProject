package com.example.huytr.finalproject20;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityWalletAdd extends AppCompatActivity {

    Spinner spinner;
    int currency;
    String name , description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("New Wallet");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.button_cross);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.Currency);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_spinner_item , Wallet.listCurrency);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.ActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = ((EditText) findViewById(R.id.WalletName)).getText().toString().trim();
                description = ((EditText) findViewById(R.id.WalletDescription)).getText().toString();

                if (name.equals(""))
                    return;

                (new SaveWallet()).execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , ActivityMain.class));
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)
        {
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                                // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public class SaveWallet extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ContentValues values = new ContentValues();
            values.put("name" , name);
            values.put("avatar" , currency);
            values.put("currency", Wallet.listCurrency[currency]);
            values.put("balance" , 0);
            values.put("description" , description);
            if (-1 != DatabaseUtility.database.insert("Wallet", null, values))
                Wallet.list.add(new Wallet(name, Wallet.listAvatar[currency], Wallet.listCurrency[currency], 0, description));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }
}
