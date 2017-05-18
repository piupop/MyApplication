package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.lifeistech.android.eventreminder.model.MyModel;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class AddeventActivity extends AppCompatActivity {
    Realm realm;

    Button addevent;
    EditText editDate1;
    EditText editDate2;
    EditText editTitle;
    EditText editRateNum;
    EditText editMemo;
    String reEditDate1;
    String reEditDate2;
    String reEditTitle;
    String reEditRate;
    String reEditMemo;
    int reEditId;
    Intent intent;


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

        Intent intent = getIntent();
        reEditDate1 = intent.getStringExtra("date1ToEdit");
        reEditDate2 = intent.getStringExtra("date2ToEdit");
        reEditTitle = intent.getStringExtra("titleToEdit");
        reEditMemo = intent.getStringExtra("memoToEdit");
        reEditRate = intent.getStringExtra("rateToEdit");
        reEditId = intent.getIntExtra("idToEdit", 0);

        //intentで受け取ったテキストを表示
        editDate1.setText(reEditDate1);
        editDate2.setText(reEditDate2);
        editTitle.setText(reEditTitle);
        editMemo.setText(reEditMemo);
        editRateNum.setText(reEditRate);
    }

    public void add(View v){
        
        if ( ) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {

                    RealmResults<MyModel> result = bgRealm.getDefaultInstance().where(MyModel.class).findAll();
                    int id = 0;
                    if (result.size() != 0) {
                        id = result.sort("id", Sort.ASCENDING).last().getId() + 1;
                    } //realmにデータが存在するときはIDを追加

                    MyModel model = bgRealm.createObject(MyModel.class, id);
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
                    Log.e("ErrorTransaction", error.toString());
                }
            });
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    RealmResults<MyModel> result2 = bgRealm.getDefaultInstance().where(MyModel.class).equalTo("id", reEditId).findAll();
                    String date1Text = editDate1.getText().toString();
                    String date2Text = editDate2.getText().toString();
                    String titleText = editTitle.getText().toString();
                    Log.e("editTitle", titleText);
                    String rateNumText = editRateNum.getText().toString();
                    int rateNum = Integer.parseInt(rateNumText);//int型への変換
                    String memoText = editMemo.getText().toString();

                    MyModel edit = bgRealm.createObject(MyModel.class);
                    edit.setDate1(date1Text);
                    edit.setDate2(date2Text);
                    edit.setTitle(titleText);
                    edit.setRate(rateNum);
                    edit.setMemo(memoText);

                }
            });
        }
    };

    public void cancel(View v){
        finish();
    }





}
