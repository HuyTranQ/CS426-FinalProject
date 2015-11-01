package com.example.huytr.finalproject20;

import java.util.ArrayList;

/**
 * Created by huytr on 22-08-2015.
 */
public class Transaction {

    public static ArrayList<Transaction> currentList;

    public long id;
    public long category;
    public int money;
    public String description;
    public Coordinate location;

    public Transaction(long id,
                       long category,
                       int money,
                       String description,
                       Coordinate location)
    {
        this.id = id;
        this.category = category;
        this.money = money;
        this.description = description;
        this.location = location;
    }

}
