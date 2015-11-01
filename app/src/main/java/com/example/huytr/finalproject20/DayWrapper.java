package com.example.huytr.finalproject20;


import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huytr on 23-08-2015.
 */
public class DayWrapper {
    public int income = 0;
    public int expense = 0;
    public int value;
    public ArrayList<Transaction> listTransaction;

    public DayWrapper(int value ,
                      ArrayList<Transaction> listTransaction)
    {
        this.value = value;
        this.listTransaction = listTransaction;
        if (listTransaction == null)
            return;
        for (Transaction transaction : listTransaction)
        {
            if (Category.list.get(transaction.category).type == 0)
                expense += transaction.money;
            else
                income += transaction.money;
        }
    }

    public static int[] calendar = {
            R.drawable.calendar01,
            R.drawable.calendar02,
            R.drawable.calendar03,
            R.drawable.calendar04,
            R.drawable.calendar05,
            R.drawable.calendar06,
            R.drawable.calendar07,
            R.drawable.calendar08,
            R.drawable.calendar09,
            R.drawable.calendar10,
            R.drawable.calendar11,
            R.drawable.calendar12,
            R.drawable.calendar13,
            R.drawable.calendar14,
            R.drawable.calendar15,
            R.drawable.calendar16,
            R.drawable.calendar17,
            R.drawable.calendar18,
            R.drawable.calendar19,
            R.drawable.calendar20,
            R.drawable.calendar21,
            R.drawable.calendar22,
            R.drawable.calendar23,
            R.drawable.calendar24,
            R.drawable.calendar25,
            R.drawable.calendar26,
            R.drawable.calendar27,
            R.drawable.calendar28,
            R.drawable.calendar29,
            R.drawable.calendar30,
            R.drawable.calendar31
    };
}
