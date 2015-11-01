package com.example.huytr.finalproject20;

import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityTransactionAdd extends AppCompatActivity {

    public static double lat , lng;
    public static long idCategory;
    public static boolean chosen = false;
    int counter = -1;
    Toolbar toolbar;
    Date date = new Date();
    int money;
    String description;
    LocationService locationService = new LocationService();
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);

        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("New Wallet");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.button_cross);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ((TextView) findViewById(R.id.DateView)).setText(date.day + "-" + (date.month + 1) + "-" + date.year);

        location = locationService.getLocation(this);
        if (location == null)
        {
            ((TextView) findViewById(R.id.Location)).setText("Unavailable");
            lat = 10.827132f;
            lng = 106.646297f;
        }
        else
        {
            lat = location.getLatitude();
            lng = location.getLongitude();
            ((TextView) findViewById(R.id.Location)).setText(
                    "(" +
                            String.format("%.3f", new BigDecimal(lat)) + " , " +
                            String.format("%.3f" , new BigDecimal(lng)) +
                            ")"
            );
        }

        findViewById(R.id.FrameLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), ActivityCategoryChoose.class));
            }
        });

        findViewById(R.id.ActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chosen)
                    return;
                String sMoney = ((EditText) findViewById(R.id.Money)).getText().toString().trim();
                if (sMoney.equals(""))
                    return;
                money = Integer.parseInt(sMoney);
                description = ((EditText) findViewById(R.id.Description)).getText().toString();
                chosen = false;
                (new SaveTransaction()).execute();
            }
        });

        findViewById(R.id.DateView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = DialogDate.CreateDateDialog(
                        (TextView) findViewById(R.id.DateView),
                        date);
                fragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        findViewById(R.id.Location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , ActivityLocationChoose.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , ActivityMain.class));
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!chosen)
            return;
        Category current = Category.list.get(idCategory);
        counter = current.type;
        ((TextView) findViewById(R.id.RootName)).setText(current.name);
        Picasso.with(this).load(Category.rootResource[current.category][current.offset]).into((ImageView) findViewById(R.id.Avatar));
        int background = (current.type == 0) ? R.drawable.background_expense : R.drawable.background_income;
        Picasso.with(this).load(background).into((CircleImageView) findViewById(R.id.Background));
        ChangeTheme();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(colorDark));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_add, menu);
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
                startActivity(new Intent(this, ActivityMain.class));
                //NavUtils.navigateUpTo(this, upIntent);
            }
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public class Date {

        public Date()
        {
            final Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
        public int day, month, year;
    }

    public class SaveTransaction extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... params) {

            ContentValues values = new ContentValues();
            values.put("day" , date.day);
            values.put("money" , money);
            values.put("month" , date.month);
            values.put("description" , description);
            values.put("category", idCategory);
            values.put("wallet", Wallet.current.name);
            values.put("year", date.year);
            values.put("lat" , lat);
            values.put("lng" , lng);
            long id = DatabaseUtility.database.insert("[Transaction]", null, values);

            if (id != -1)
            {
                if (Year.current.listMonth[date.month] == null)
                    Year.current.listMonth[date.month] = new Month();
                HashMap<Integer , ArrayList<Transaction>> currentMonth = Year.current.listMonth[date.month].listDay;
                ArrayList<Transaction> listTransaction;
                if (!currentMonth.containsKey(date.day))
                    currentMonth.put(date.day ,  new ArrayList<Transaction>());
                listTransaction = currentMonth.get(date.day);
                listTransaction.add(new Transaction(id , idCategory , money , description , new Coordinate(lat , lng)));

                if (Category.list.get(idCategory).type == 0)
                    Wallet.current.balance -= money;
                else
                    Wallet.current.balance += money;
                values = new ContentValues();
                values.put("balance", Wallet.current.balance);
                String[] args = {Wallet.current.name};
                if (0 == DatabaseUtility.database.update("[Wallet]" , values , "[name] = ?" , args))
                    Log.e("Wallet" , "Cannot update balance of wallet->" + Wallet.current.name);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }

}
