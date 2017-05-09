package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lifeistech.android.eventreminder.model.MyModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setTitle("イベントリスト");

        ListView listView = (ListView) findViewById(R.id.listView);
        Button addlist = (Button) findViewById(R.id.);

        RealmResults<MyModel> model = realm.where(MyModel.class)
                .findAll();

        ArrayList<MyModel> arrayList = new ArrayList(model);



    }

    @Override
    public void add(View v){
        Intent intent = new Intent(this, AddeventActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
