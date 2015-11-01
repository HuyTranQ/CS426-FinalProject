package com.example.huytr.finalproject20;

/**
 * Created by huytr on 22-08-2015.
 */
public class Year {

    public static Year current;

    public int value;
    public Month[] listMonth = new Month[12];

    public Year(int value)
    {
        this.value = value;
        for (int i = 0; i < 12; ++i)
            listMonth[i] = null;
    }

}
