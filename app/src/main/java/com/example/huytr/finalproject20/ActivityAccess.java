package com.example.huytr.finalproject20;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActivityAccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        DatabaseUtility.Initialize(this);

        Picasso.with(this).load(R.drawable.background_access).into((ImageView) findViewById(R.id.Background));

        Calendar calendar = Calendar.getInstance();
        Year.current = new Year(calendar.get(Calendar.YEAR));
        Month.current = calendar.get(Calendar.MONTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_access, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void SignIn(View view)
    {
        (new LoadData()).execute();
    }

    public class LoadData extends AsyncTask<Void , Void , Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = DatabaseUtility.database.rawQuery("SELECT * FROM [Wallet]" , null);
            cursor.moveToPosition(-1);
            Wallet.list = new ArrayList<>();
            while (cursor.moveToNext()){
                Wallet.list.add(new Wallet(
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("avatar")),
                        cursor.getString(cursor.getColumnIndex("currency")),
                        cursor.getInt(cursor.getColumnIndex("balance")),
                        cursor.getString(cursor.getColumnIndex("description"))
                ));
            }
            if (Wallet.list.size() == 0)
            {
                int currency;
                String name , description;
                currency = 0;
                name = "Personal";
                description = "Default Wallet";
                ContentValues values = new ContentValues();
                values.put("name" , name);
                values.put("avatar" , currency);
                values.put("currency", Wallet.listCurrency[currency]);
                values.put("balance" , 0);
                values.put("description" , description);
                if (-1 != DatabaseUtility.database.insert("Wallet", null, values))
                    Wallet.list.add(new Wallet(name, Wallet.listAvatar[currency], Wallet.listCurrency[currency], 0, description));
            }
            Wallet.current = Wallet.list.get(0);

            cursor = DatabaseUtility.database.rawQuery("SELECT * FROM [Category]" , null);
            cursor.moveToPosition(-1);
            int type , offset , category;
            long id;
            String name;
            Category.list = new HashMap<>();
            while (cursor.moveToNext())
            {
                type = cursor.getInt(cursor.getColumnIndex("type"));
                category = cursor.getInt(cursor.getColumnIndex("category"));
                id = cursor.getInt(cursor.getColumnIndex("id"));
                offset = cursor.getInt(cursor.getColumnIndex("offset"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                Category.list.put(id , new Category(name , type , category , offset));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(getApplicationContext(), ActivityMain.class));
        }
    }

}
