package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.lifeistech.android.eventreminder.model.MyModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    CardAdapter mCardAdapter;
    ListView mListView;
    Button button;

    ArrayList<MyModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.add_button);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        setTitle("イベントリスト");
        //realmに入っているものをすべて表示
        RealmResults<MyModel> model = realm.where(MyModel.class)
                .findAll();
        arrayList.addAll(model);
        arrayList = new ArrayList<MyModel>();
        mCardAdapter = new CardAdapter(this, 0, arrayList); //アダプターのインスタンス化
        mListView.setAdapter(mCardAdapter);        //リストにアダプターをセット

    };

    public void onClick(View v){
        Intent intent =new Intent(this, AddeventActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayList.clear();
        RealmResults<MyModel> model = realm.where(MyModel.class)
                .findAll();
        arrayList.addAll(model);
        mCardAdapter.notifyDataSetChanged();//データの更新を伝える
    }


}
