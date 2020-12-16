package com.u.app2.wal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.u.app2.gong.c001;
import com.u.app2.R;

import java.util.ArrayList;

public class walAdpter extends BaseAdapter {
    private walActivity mwalActivity ;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<walinfo> data = null;
    private LayoutInflater inflater = null;
    String TAG = "walAdpter";



    public walAdpter(walActivity walActivity, Context c, int l, ArrayList<walinfo> d) {
        this.mwalActivity = walActivity;
        this.mContext = c;
        this.layout = l;
        this.data = d;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data;
    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub

        if(convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }
       // ImageView Hu_image = (ImageView) convertView.findViewById(R.id.Human_image);
        TextView currency = (TextView) convertView.findViewById(R.id.currency);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);
        TextView locked = (TextView) convertView.findViewById(R.id.locked);
        TextView avg_buy_price = (TextView) convertView.findViewById(R.id.avg_buy_price);
        //TextView balance_price = (TextView) convertView.findViewById(R.id.balance_price);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.trad);


        TextView lockedtext = (TextView) convertView.findViewById(R.id.lockedtext);


        lockedtext.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : click event
                        Log.d(TAG, "setWelListView :seo::>>lockedtext [" + position  + "]view[" + data.get(position).currency+ "][" + data.get(position).trade + "]");

                        if(data.get(position).trade.toLowerCase().equals("t")){

                            Log.d(TAG, "setWelListView :seo::>>lockedtext 클릭 했단.!!!!! 화면 이동 OK");

                            mwalActivity.poporderclick(data.get(position).currency);

                        }else{

                            c001.alterMsg(mwalActivity, "[EHRD]","진행 할 수 없습니다.");

                        }
                    }
                }
        );
        locked.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : click event
                        Log.d(TAG, "setWelListView :seo::>>locked [" + position  + "]view[" + data.get(position).currency+ "][" + data.get(position).trade + "]");

                        if(data.get(position).trade.toLowerCase().equals("t")){

                            Log.d(TAG, "setWelListView :seo::>>locked 클릭 했단.!!!!! 화면 이동 OK");

                            mwalActivity.poporderclick(data.get(position).currency);

                        }else{

                            c001.alterMsg(mwalActivity, "[STH3]","진행 할 수 없습니다.");

                        }

                    }
                }
        );


        currency.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : click event
                        //Log.d(TAG, "setWelListView :seo::>>currency 클릭 했단.!!!!!  view[" + data.get(position).currency+ "][" + data.get(position).trade + "]");

                        if(data.get(position).trade.toLowerCase().equals("t")){

                            Log.d(TAG, "setWelListView :seo::>>currency 클릭 했단.!!!!! 화면 이동 OK");

                            //parent.mOnPopupClick();

                            //walActivity.mOnPopupClick();

                            mwalActivity.ttonclick(data.get(position).currency);

                            //Intent intent = new Intent(mContext, PopupActivity.class);
                            //intent.putExtra("data", "Test Popup");
                            //startActivity(intent);

                            //walActivity a = new walActivity();
                            //a.mOnPopupClick(mContext);

                            //a.ttonclick


//                            parent.findViewById(R.id.tt).callOnClick();


                        }else{

                            c001.alterMsg(mwalActivity, "[DSSA]"," 할 수 없습니다.");

                        }

                    }
                }
        );


        final View finalConvertView = convertView;
        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBoxc = (CheckBox) finalConvertView.findViewById(R.id.trad) ;
                if( data.get(position).trade.toLowerCase().equals("t")){
                    checkBoxc.setChecked(true);
                }else{
                    checkBoxc.setChecked(false);
                }

            }
        }) ;



        //Hu_image.setImageBitmap(data.get(position).image);
        currency.setText(data.get(position).currency);
        balance.setText(data.get(position).balance);
        locked.setText(data.get(position).locked);
        avg_buy_price.setText(data.get(position).avg_buy_price);
        if( data.get(position).trade.toLowerCase().equals("t")){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

        //avg_buy_price.setText(data.get(position).avg_buy_price);
        //balance_price.setText(data.get(position).balance_price);




        return convertView;
    }


}

