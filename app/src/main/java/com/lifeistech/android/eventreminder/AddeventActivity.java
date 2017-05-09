package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lifeistech.android.eventreminder.model.MyModel;

import io.realm.Realm;

public class AddeventActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Intent intent = new Intent();
        Button addevent = (Button)findViewById(R.id.addevent);

    }

    public void add(View v){
        realm.beginTransaction();
        MyModel model = realm.createObject(MyModel.class);
        model.setDate1("1");
        model.setDate2("2");
        model.setTitle("あ");
        model.setRatingbar(3);
        model.setMemo("あいうえお");
        realm.commitTransaction();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                MyModel model = realm.createObject(MyModel.class);
                model.setDate1("1");
                model.setDate2("2");
                model.setTitle("あ");
                model.setRatingbar(3);
                model.setMemo("あいうえお");
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });
    }



}
