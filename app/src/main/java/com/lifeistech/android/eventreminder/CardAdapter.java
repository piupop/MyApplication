package com.lifeistech.android.eventreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lifeistech.android.eventreminder.model.MyModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Marina Hayashi on 2017/05/13.
 */

public class CardAdapter extends ArrayAdapter<MyModel> {
    Context context;
    List<MyModel> mMyModel; //MyModelの情報をリストに入れたい
    Realm realm;
    Activity activity;


    public CardAdapter(Context context, int layoutResourceId, List<MyModel> objects,Realm realm,Activity activity){
        super(context, layoutResourceId, objects);
        this.realm = realm;
        this.context = context;
        this.activity =activity;

        mMyModel = objects;
    }
    @Override
    public  int getCount(){
        return  mMyModel.size();
    }
    @Override
    public MyModel getItem(int position){
        return mMyModel.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MyModel item = getItem(position);

        if (item != null){
            Log.e("id", String.valueOf(item.getId()));
            String rateNumText = String.valueOf(item.getRate());
            //set data
            viewHolder.dateTextView.setText(item.getDate1());
            viewHolder.date2TextView.setText(item.getDate2());
            viewHolder.titleTextView.setText(item.getTitle()); //modelの情報を取得
            viewHolder.rateNumTextView.setText(rateNumText);
            viewHolder.memoTextView.setText(item.getMemo());
            viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final RealmResults<MyModel> results = realm.where(MyModel.class).equalTo("id", item.getId()).findAll();
// All changes to data must happen in a transaction
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            results.deleteAllFromRealm();//realm側のデータを削除
                        }
                    });
                    mMyModel.remove(position); //listviewから削除
                    notifyDataSetChanged();//変更を知らせる
                }

            });
            viewHolder.editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final RealmResults<MyModel> reEditData = realm.where(MyModel.class).equalTo("id", item.getId()).findAll(); //クリックされた部分のid
                    //データの取得
                    String reEditDate1 = item.getDate1();
                    String reEditDate2 = item.getDate2();
                    String reEditTitle = item.getTitle();
                    String reEditMemo = item.getMemo();
                    int reEditId = item.getId();
                    int reEditRate = item.getRate();
                    String reEditRateNum = String.valueOf(reEditRate);

                    Intent intent = new Intent(context, AddeventActivity.class);
                    //データの引き渡し
                    intent.putExtra("date1ToEdit", reEditDate1);
                    intent.putExtra("date2ToEdit", reEditDate2);
                    intent.putExtra("titleToEdit", reEditTitle);
                    intent.putExtra("memoToEdit", reEditMemo);
                    intent.putExtra("rateToEdit", reEditRateNum);
                    intent.putExtra("idToEdit", reEditId);
                    activity.startActivity(intent);
                }
            });

        }

        return convertView;
    }



    private class ViewHolder{
        TextView dateTextView;
        TextView date2TextView;
        TextView titleTextView;
        TextView rateNumTextView;
        TextView memoTextView;
        ImageButton deleteButton;
        ImageButton editButton;

        public ViewHolder(View view){
            //instance
            dateTextView = (TextView) view.findViewById(R.id.card_dateTextView);
            date2TextView = (TextView) view.findViewById(R.id.card_date2TextView);
            titleTextView = (TextView) view.findViewById(R.id.card_titleTextView);
            rateNumTextView = (TextView) view.findViewById(R.id.card_rateTextView);
            memoTextView = (TextView) view.findViewById(R.id.card_memoTextView);
            deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
            editButton = (ImageButton) view.findViewById(R.id.editButton);

        }
    }
}
