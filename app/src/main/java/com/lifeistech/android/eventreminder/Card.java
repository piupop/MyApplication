package com.lifeistech.android.eventreminder;

/**
 * Created by Marina Hayashi on 2017/05/13.
 */

public class Card {
    public int rate;
    public String date;
    public String date2;
    public String title;
    public String memo;

    public Card(int rate, String date, String date2, String title, String memo){
        this.rate = rate;
        this.date = date;
        this.date2 = date2;
        this.title = title;
        this.memo = memo;
    }
}
