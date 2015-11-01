package com.example.huytr.finalproject20;

import java.util.ArrayList;

/**
 * Created by huytr on 22-08-2015.
 */
public class Wallet {

    public static Wallet current;
    public static ArrayList<Wallet> list = null;
    public static String[] listCurrency = {"\u20AB" , "$" , "\u00A3"};
    public static int[] listAvatar = {R.drawable.wallet01 , R.drawable.wallet02 , R.drawable.wallet03};

    public int avatar;
    public String name;
    public String currency;
    public int balance;
    public String description;

    public Wallet(String name,
                  int avatar,
                  String currency,
                  int balance,
                  String description)
    {
        this.avatar = avatar;
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.description = description;
    }

}
