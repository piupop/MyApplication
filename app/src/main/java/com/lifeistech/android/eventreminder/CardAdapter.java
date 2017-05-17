package com.lifeistech.android.eventreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifeistech.android.eventreminder.model.MyModel;

import java.util.List;

/**
 * Created by Marina Hayashi on 2017/05/13.
 */

public class CardAdapter extends ArrayAdapter<MyModel> {
    List<MyModel> mMyModel; //MyModelの情報をリストに入れたい

    public CardAdapter(Context context, int layoutResourceId, List<MyModel> objects){
        super(context, layoutResourceId, objects);

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
            String rateNumText = String.valueOf(item.getRate());
            //set data
            viewHolder.dateTextView.setText(item.getDate1());
            viewHolder.date2TextView.setText(item.getDate2());
            viewHolder.titleTextView.setText(item.getTitle()); //modelの情報を取得
            viewHolder.rateNumTextView.setText(rateNumText);
            viewHolder.memoTextView.setText(item.getMemo());

        }

        return convertView;
    }

    private class ViewHolder{
        TextView dateTextView;
        TextView date2TextView;
        TextView titleTextView;
        TextView rateNumTextView;
        TextView memoTextView;

        public ViewHolder(View view){
            //instance
            dateTextView = (TextView) view.findViewById(R.id.card_dateTextView);
            date2TextView = (TextView) view.findViewById(R.id.card_date2TextView);
            titleTextView = (TextView) view.findViewById(R.id.card_titleTextView);
            rateNumTextView = (TextView) view.findViewById(R.id.card_rateTextView);
            memoTextView = (TextView) view.findViewById(R.id.card_memoTextView);

        }
    }
}
