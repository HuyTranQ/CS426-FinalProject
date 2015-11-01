package com.example.huytr.finalproject20;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityCategoryAdd extends AppCompatActivity {

    public static int offset, category;
    public static int type;
    FloatingActionButton button;
    EditText name;
    CircleImageView background;
    FrameLayout frameLayout;
    ImageView avatar;
    Toolbar toolbar;
    TextView root;
    String categoryName;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        activity = this;

        offset = -1;
        category = 0;

        type = getIntent().getIntExtra("type" , 0);

        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Add Category");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        button = (FloatingActionButton) findViewById(R.id.ActionButton);
        name = (EditText) findViewById(R.id.CategoryName);
        frameLayout = (FrameLayout) findViewById(R.id.FrameLayout);
        background = (CircleImageView) findViewById(R.id.Background);
        avatar = (ImageView) findViewById(R.id.Avatar);
        root = (TextView) findViewById(R.id.RootName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryName = name.getText().toString().trim();
                if (categoryName.equals("") || offset == -1)
                    return;
                (new SaveCategory()).execute();
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity , ActivityIcon.class));
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
        if (offset != -1)
        {
            Picasso.with(this).load(Category.rootResource[category][offset]).into(avatar);
            root.setText(Category.listName[category]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category_add, menu);
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
                startActivity(new Intent(this , ActivityMain.class));
                //NavUtils.navigateUpTo(this, upIntent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SaveCategory extends AsyncTask<Void , Void , Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ContentValues values = new ContentValues();
            values.put("category" , category);
            values.put("name" , categoryName);
            values.put("type" , type);
            values.put("offset", offset);

            long id = DatabaseUtility.database.insert("Category", null, values);
            if (id != -1)
                Category.list.put(id , new Category(categoryName , type , category , offset));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onBackPressed();
        }
    }
}
