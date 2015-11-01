package com.example.huytr.finalproject20;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * Created by huytr on 24-08-2015.
 */
public final class DatabaseUtility extends Fragment{

    public static SQLiteDatabase database;
    public static String path;

    public static void Initialize(Activity activity)
    {
        File storagePath = activity.getFilesDir();
        path = storagePath + "/" + "05";
        database = SQLiteDatabase.openDatabase(path , null , SQLiteDatabase.CREATE_IF_NECESSARY);
        for (int i = 0; i < initialize.length; ++i)
            database.execSQL(initialize[i]);
    }

    static String[] initialize = {

            "CREATE TABLE IF NOT EXISTS [Wallet] (\n" +
                    "  [name] TEXT, \n" +
                    "  [avatar] INTEGER NOT NULL, \n" +
                    "  [currency] TEXT NOT NULL, \n" +
                    "  [balance] INTEGER NOT NULL, \n" +
                    "  [description] TEXT, \n" +
                    "  CONSTRAINT [] PRIMARY KEY ([name]));",

            "CREATE TABLE IF NOT EXISTS [Category] (\n" +
                    "  [id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                    "  [category] INTEGER NOT NULL, \n" +
                    "  [name] CHAR NOT NULL, \n" +
                    "  [type] INTEGER NOT NULL, \n" +
                    "  [offset] INTEGER NOT NULL);",

            "CREATE TABLE IF NOT EXISTS [Transaction] (\n" +
                    "  [wallet] TEXT NOT NULL, \n" +
                    "  [year] INTEGER NOT NULL, \n" +
                    "  [month] INTEGER NOT NULL, \n" +
                    "  [day] INTEGER NOT NULL, \n" +
                    "  [money] INTEGER NOT NULL, \n" +
                    "  [description] TEXT NOT NULL, \n" +
                    "  [category] INTEGER NOT NULL CONSTRAINT [FK_Category_Transaction] REFERENCES [Category1]([id]) ON DELETE CASCADE ON UPDATE CASCADE, \n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "  [lat] REAL, \n" +
                    "  [lng] REAL);"
    };
}
