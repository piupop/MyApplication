package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lifeistech.android.eventreminder.model.MyModel;

import io.realm.Realm;

import static com.lifeistech.android.eventreminder.R.id.card_dateTextView;
import static com.lifeistech.android.eventreminder.R.id.date1;

public class AddeventActivity extends AppCompatActivity {
    Realm realm;
    Intent intent;
    Button addevent;
    EditText editDate1;
    EditText editDate2;
    EditText editTitle;
    EditText editRateNum;
    EditText editMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);

        setTitle("イベント追加");
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        intent = new Intent();
        addevent = (Button)findViewById(R.id.addevent);
        editDate1 = (EditText) findViewById(R.id.date1);
        editDate2 = (EditText) findViewById(R.id.date2);
        editTitle = (EditText) findViewById(R.id.title);
        editRateNum = (EditText) findViewById(R.id.rateNum);
        editMemo = (EditText) findViewById(R.id.memo);


    }

    public void add(View v){
        realm.beginTransaction();
        MyModel model = realm.createObject(MyModel.class);
        String date1Text = editDate1.getText().toString();
        String date2Text = editDate2.getText().toString();
        String titleText = editTitle.getText().toString();
        String rateNumText = editRateNum.getText().toString();
        int rateNum = Integer.parseInt(rateNumText);//int型への変換
        String memoText = editMemo.getText().toString();

        model.setDate1(date1Text);
        model.setDate2(date2Text);
        model.setTitle(titleText);
        model.setRate(rateNum);
        model.setMemo(memoText);
        realm.commitTransaction();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                MyModel model = realm.createObject(MyModel.class);
                String date1Text = editDate1.getText().toString();
                String date2Text = editDate2.getText().toString();
                String titleText = editTitle.getText().toString();
                String rateNumText = editRateNum.getText().toString();
                int rateNum = Integer.parseInt(rateNumText);//int型への変換
                String memoText = editMemo.getText().toString();

                model.setDate1(date1Text);
                model.setDate2(date2Text);
                model.setTitle(titleText);
                model.setRate(rateNum);
                model.setMemo(memoText);    }
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
                finish();
            }
        });
    }



}
