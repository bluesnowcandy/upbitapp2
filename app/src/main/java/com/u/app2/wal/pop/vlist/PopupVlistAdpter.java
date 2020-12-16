package com.u.app2.wal.pop.vlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.u.app2.R;

import java.util.ArrayList;

public class PopupVlistAdpter extends BaseAdapter {
    private PopupVlistActivity mwalActivity ;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<PopupVlistInfo> data = null;
    private LayoutInflater inflater = null;
    String TAG = "PopupOrderAdpter";



    public PopupVlistAdpter(PopupVlistActivity activity, Context c, int l, ArrayList<PopupVlistInfo> d) {
        this.mwalActivity = activity;
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
        //TextView locked = (TextView) convertView.findViewById(R.id.locked);
        //TextView avg_buy_price = (TextView) convertView.findViewById(R.id.avg_buy_price);
        //TextView balance_price = (TextView) convertView.findViewById(R.id.balance_price);
        //CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.trad);


        //TextView lockedtext = (TextView) convertView.findViewById(R.id.lockedtext);



        TextView pop_vlist_base_dt  = (TextView) convertView.findViewById(R.id.pop_vlist_base_dt);
        TextView pop_vlist_currency = (TextView) convertView.findViewById(R.id.pop_vlist_currency);
        TextView pop_wal_rate       = (TextView) convertView.findViewById(R.id.pop_wal_rate);


        pop_vlist_base_dt.setText(data.get(position).base_dt);
        pop_vlist_currency.setText(data.get(position).currency);
        pop_wal_rate.setText(data.get(position).rate);





        return convertView;
    }


}

