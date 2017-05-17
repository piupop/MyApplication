package com.lifeistech.android.eventreminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifeistech.android.eventreminder.model.MyModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Realm realm;
    CardAdapter mCardAdapter;
    ListView mListView;
    Button button;
    ImageButton mEditButton;
    ImageButton mDeleteButton;

    private Spinner spinner;
    private String spinnerItem[] = {"優先度が高い順", "優先度が低い順"};


    ArrayList<MyModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.add_button);
        mEditButton = (ImageButton) findViewById(R.id.editButton);
        mDeleteButton = (ImageButton) findViewById(R.id.deleteButton);

        //spinnerの設定
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Realm.init(this);
        realm = Realm.getDefaultInstance();

        setTitle("イベントリスト");
        //realmに入っているものをすべて表示
        RealmResults<MyModel> model = realm.where(MyModel.class)
                .findAll();
        arrayList = new ArrayList<MyModel>();//インスタンスを先に作成
        arrayList.addAll(model);
        mCardAdapter = new CardAdapter(this, 0, arrayList,realm,this); //アダプターのインスタンス化
        mListView.setAdapter(mCardAdapter);        //リストにアダプターをセット
    }

    ;

    public void onClick(View v) {
        Intent intent = new Intent(this, AddeventActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        String item = (String) spinner.getSelectedItem();

        if (item.equals("優先度が高い順")) {
            arrayList.clear();
            RealmResults<MyModel> model = realm.where(MyModel.class).findAll();
            model = model.sort("rate", Sort.DESCENDING);// 降順にソート
            arrayList.addAll(model);

            Log.e("TAG",model.toString());

            mCardAdapter.notifyDataSetChanged();//再度
        } else if (item.equals("優先度が低い順")) {
            arrayList.clear();
            RealmResults<MyModel> model = realm.where(MyModel.class).findAll();
            model = model.sort("rate", Sort.ASCENDING); // 昇順にソート
            arrayList.addAll(model);
            Log.e("TAG",model.toString());
            mCardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
