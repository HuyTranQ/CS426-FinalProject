package com.example.huytr.finalproject20;

import java.util.ArrayList;

/**
 * Created by huytr on 22-08-2015.
 */
public class Day {

    public static Day current;

    public int value;
    public ArrayList<Transaction> listTransaction = new ArrayList<>();

    public Day(int value)
    {
        this.value = value;
    }
}
