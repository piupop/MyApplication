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

        //intentの受け取りとテキストの表示
        Intent intent = getIntent();
        String reEditDate1 = intent.getStringExtra("date1ToEdit");
        String reEditDate2 = intent.getStringExtra("date2ToEdit");
        String reEditTitle = intent.getStringExtra("titleToEdit");
        String reEditMemo = intent.getStringExtra("memoToEdit");
        String reEditRate = intent.getStringExtra("rateToEdit");
        int reEditId = intent.getIntExtra("idToEdit", 0);

        editDate1.setText(reEditDate1);
        editDate2.setText(reEditDate2);
        editTitle.setText(reEditTitle);
        editMemo.setText(reEditMemo);
        editRateNum.setText(reEditRate);

    }

    public void add(View v){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                RealmResults<MyModel> result = bgRealm.getDefaultInstance().where(MyModel.class) .findAll();
                int id =0;
                if (result.size() != 0 ){
                    id = result.sort("id", Sort.ASCENDING).last().getId()+1;
                } //realmにデータが存在するときはIDを追加

                MyModel model = bgRealm.createObject(MyModel.class,id);
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
                model.setMemo(memoText);}

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
                Log.e("ErrorTransaction",error.toString());
            }
        });
    }

    public void cancel(View v){
        finish();
    }





}
