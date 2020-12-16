package com.u.app2.wal.pop.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.u.app2.R;

import java.util.ArrayList;

public class PopupOrderAdpter extends BaseAdapter {
    private PopupOrderActivity mwalActivity ;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<PopupOrderInfo> data = null;
    private LayoutInflater inflater = null;
    String TAG = "PopupOrderAdpter";



    public PopupOrderAdpter(PopupOrderActivity activity, Context c, int l, ArrayList<PopupOrderInfo> d) {
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



        TextView pop_wal_side   = (TextView) convertView.findViewById(R.id.pop_wal_side);
        TextView pop_wal_volume = (TextView) convertView.findViewById(R.id.pop_wal_volume);
       // TextView pop_wal_locked = (TextView) convertView.findViewById(R.id.pop_wal_locked);
        //TextView pop_wal_uuid   = (TextView) convertView.findViewById(R.id.pop_wal_uuid);
        TextView pop_wal_state  = (TextView) convertView.findViewById(R.id.pop_wal_state);
        TextView pop_wal_price  = (TextView) convertView.findViewById(R.id.pop_wal_price);
        TextView pop_wal_created_at = (TextView) convertView.findViewById(R.id.pop_wal_created_at);
        TextView pop_wal_currency   = (TextView) convertView.findViewById(R.id.pop_wal_currency);


        pop_wal_side.setText(data.get(position).side);
        pop_wal_volume.setText(data.get(position).volume);
        //pop_wal_locked.setText(data.get(position).locked);
        //pop_wal_uuid.setText(data.get(position).uuid);
        pop_wal_state.setText(data.get(position).state);
        pop_wal_price.setText(data.get(position).price);
        pop_wal_created_at.setText(data.get(position).created_at);
        pop_wal_currency.setText(data.get(position).market);





        return convertView;
    }


}

