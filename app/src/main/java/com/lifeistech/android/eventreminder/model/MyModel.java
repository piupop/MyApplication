package com.lifeistech.android.eventreminder.model;

import io.realm.RealmObject;

/**
 * Created by Marina Hayashi on 2017/05/09.
 */

public class MyModel extends RealmObject {
    private String date1;
    private String date2;
    private String title;
    private String rate;
    private String memo;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate(){ return rate;}

    public void setRate(String rate){ this.rate = rate;}

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
