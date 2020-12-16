package com.u.app2.wal;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.u.app2.gong.c001;
import com.u.app2.httpconnect.RequestHttpURLConnection;
import com.u.app2.R;
import com.u.app2.reg.regActivity;
import com.u.app2.wal.pop.config.PopupConfigActivity;
import com.u.app2.wal.pop.order.PopupOrderActivity;
import com.u.app2.wal.pop.vlist.PopupVlistActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

//import com.example.dia.dialogAppLoadingApp;

public class walActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<walinfo> walinfo_list;
    String TAG = "walActivity";

    walAdpter myadapter;
    String deviceid = "";
    String popcurrency = "";

    ContentValues pams = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d(TAG, "walActivity :seo::>> start ");
        SharedPreferences sf = getSharedPreferences(c001.SharedPreferencesName1,MODE_PRIVATE);
        deviceid = sf.getString("deviceid","").toString();
        Log.d(TAG, "walActivity :seo::>> getdeviceid[" + deviceid + "]");

        setContentView(R.layout.activity_wal);

        listView = (ListView)findViewById(R.id.listView);

        Log.d(TAG, "walActivity :seo::>> 123213");

        JSONObject jsonObject = new JSONObject();

        // 전송하기. !!
        try {

            jsonObject.put("stcode" , "DGREH");
            jsonObject.put("deviceid" , deviceid);
            //Log.d(TAG, "seo::22222");
        } catch (JSONException e) {

            Log.d(TAG, "seo::JSONException[" +e.getMessage() + "]");
            e.printStackTrace();
        }
        //Log.d(TAG, "seo::4444");

        String reqsdatasend = jsonObject.toString();
        //Log.d(TAG, "seo::s11111");

        //dialogApplication.getInstance().progressON(this, "dddd");


        //startProgress();

        pams.put("reqsdata",reqsdatasend);
        Log.d(TAG, "seo::reqsdata[" + reqsdatasend + "]");

        walActivity.NetworkTask networkTask = new walActivity.NetworkTask("", pams);
        networkTask.execute();


        TextView serchdtm = (TextView) this.findViewById(R.id.serchdtm);

        serchdtm.setOnClickListener(
                new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Log.d(TAG, "seo:: onClick");
                                            //startProgress();
                                            c001.progressON(walActivity.this, "로딩중...");
                                            walActivity.NetworkTask networkTask = new walActivity.NetworkTask("", pams);
                                            networkTask.execute();

                                        }
                                    }

        );


        TextView tt = (TextView) this.findViewById(R.id.tt);
        tt.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : click event
                        //Log.d(TAG, "setWelListView :seo::>>tt 클릭 했단.!!!!! ");
                        configpopup();

                    }
                }
        );


        TextView tv_wal_order_list_pop = (TextView) this.findViewById(R.id.tv_wal_order_list_pop);
        tv_wal_order_list_pop.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO : click event
                        //Log.d(TAG, "setWelListView :seo::>>tt 클릭 했단.!!!!! ");
                        tv_wal_order_list_pop();

                    }
                }
        );

    }



    protected void setWelListView(JSONObject jsonObject ) {

        Log.d(TAG, "setWelListView :seo::>> start ");

        try{

            Log.d(TAG, "setWelListView :seo::>> list[ " + jsonObject.get("list") + "]");

            //c001.getJsonValue("retcode" ,resultjsonObject);

            walinfo_list = new ArrayList<walinfo>();

            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(jsonObject.get("list").toString());

            JsonArray jArray = element.getAsJsonArray();

            String serchdtm1 = "";
            String k_bal1 = "";

            for(int i = 0; i <jArray.size(); i++) {

                JSONObject jsonObject_jArray = c001.jsonParser(jArray.get(i).toString());

                String currency         = c001.getJsonValue("currency", jsonObject_jArray);
                String balance          = c001.getJsonValue("balance_cnt", jsonObject_jArray);
                String locked           = c001.getJsonValue("locked_cnt", jsonObject_jArray);
                String avg_buy_price    = c001.getJsonValue("avg_buy_price", jsonObject_jArray);
                //String balance_price    = c001.getJsonValue("c7", jsonObject_jArray);
                String trade            = c001.getJsonValue("trade", jsonObject_jArray);

                //String bal_price = balance + " * " + avg_buy_price + " = " + c001.won + " " + balance_price ;
/*

                int price = 10000;
                String result = "";

                // 현재 국가 설정의 통화 표시
                result = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(price);

                // '달러' 단위 통화 표시
                result = NumberFormat.getCurrencyInstance(Locale.US).format(price);

                // '원' 단위 통화 표시
                result = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price);*/

                int price = Integer.parseInt(jsonObject.get("wall_price").toString());
                String krw = NumberFormat.getCurrencyInstance(Locale.KOREA).format(price);


                if ( i == 0){

                    k_bal1      = krw;
                    serchdtm1   = jsonObject.get("last_dtm").toString();
                }

                //Log.d(TAG, "setWelListView :seo::>>[" +  currency + "][" +bal_price +"][" + balance_price + "]");

                walinfo  walinfo_1 = new walinfo(currency, balance, locked,avg_buy_price, trade );

                walinfo_list.add(walinfo_1);


            }

            TextView serchdtm = (TextView)findViewById(R.id.serchdtm);
            TextView krwwon = (TextView)findViewById(R.id.krwwon);


            Log.d(TAG, "setWelListView :seo::>>[" + serchdtm1 + "]");

            serchdtm.setText(serchdtm1);
            krwwon.setText(k_bal1);

            myadapter = new walAdpter(this, getApplicationContext(),R.layout.activity_wal_list, walinfo_list);
            listView.setAdapter(myadapter);

        }catch (Exception E){
            c001.getLogEx(TAG, E);
        }finally {
            Log.d(TAG, "setWelListView :seo::>> finally ");

        }


    }

    public void ttonclick(String currency){

        Log.d(TAG, ":seo::>> ttonclick");

        popcurrency = currency;
        TextView tt = (TextView) this.findViewById(R.id.tt);
        tt.performClick();

    }

    public void poporderclick(String currency){

        Log.d(TAG, ":seo::>> poporderclick");

        popcurrency = currency;
        TextView tt = (TextView) this.findViewById(R.id.tv_wal_order_list_pop);
        tt.performClick();

    }

    /**
     * 오더 리스트 열기.
     */
    public void tv_wal_order_list_pop(){

        Log.d(TAG, ":seo::>> tv_wal_order_list_pop ");

        if (popcurrency== null || popcurrency.equals("")){return;}

        Intent intent = new Intent(this, PopupOrderActivity.class);
        intent.putExtra("currency", popcurrency);
        intent.putExtra("deviceid", deviceid);
        intent.putExtra("all", "Y");
        popcurrency = "";
        startActivity(intent);

//        startActivityForResult(intent, 1);

    }

    /**
     * 전체
     */

    public void all_order_list_pop(View view){

        Log.d(TAG, ":seo::>> tv_wal_order_list_pop ");


        Intent intent = new Intent(this, PopupOrderActivity.class);
        intent.putExtra("currency", popcurrency);
        intent.putExtra("deviceid", deviceid);
        intent.putExtra("all", "Y");
        popcurrency = "";
        startActivity(intent);

//        startActivityForResult(intent, 1);

    }
    /**
     * 설정 열기.
     */
    public void configpopup(){

        Log.d(TAG, ":seo::>> configpopup ");

        if (popcurrency== null || popcurrency.equals("")){return;}

        Intent intent = new Intent(this, PopupConfigActivity.class);
        intent.putExtra("currency", popcurrency);
        intent.putExtra("deviceid", deviceid);
        popcurrency = "";
        startActivity(intent);

//        startActivityForResult(intent, 1);

    }

    public void getVlist(View view) {

        Log.d(TAG, "getVlist :seo::>> start ");

        //팝업창 만들기.!!!!

        Intent intent = new Intent(this, PopupVlistActivity.class);
        intent.putExtra("deviceid", deviceid);
        startActivity(intent);


    }
    public void movereg(View view) {

        Log.d(TAG, "movereg :seo::>> start ");

        //팝업창 만들기.!!!!

        Intent intent = new Intent (this, regActivity.class);
        startActivity(intent);

        this.finish();


    }

    /**
     *
     * 통신을 한다.
     */

    // 통신을 한다.
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }



        @Override
        protected String doInBackground(Void... params) {

            String result = ""; // 요청 결과를 저장할 변수.


            try {


                Log.d(TAG, "NetworkTask :seo::>> start ");



                //dialogAppLoadingApp.getInstance().progressON(walActivity.this, "dd");


                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("stcode" , "ERHI3");
                jsonObject.put("deviceid" , deviceid);

                String reqsdatasend = jsonObject.toString();


                Log.d(TAG, "NetworkTask :seo::>> [" +  reqsdatasend + "]");

                ContentValues pams = new ContentValues();

                pams.put("reqsdata",reqsdatasend);

                result = requestHttpURLConnection.request(pams);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

            //Log.d(TAG, "onPostExecute : seo::전송결과>> [" +  result + "]");

            JSONObject resultjsonObject =  c001.jsonParser(result);

            String retcode = c001.getJsonValue("retcode" ,resultjsonObject);
            String retmsg  = c001.getJsonValue("retmsg" ,resultjsonObject);

            Log.d(TAG, "seo::retcode>> [" +  retcode + "]retmsg>> [" +  retmsg + "]");

            c001.progressOFF();
            if(retcode.equals("V001")){

                setWelListView(resultjsonObject);

            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(walActivity.this);
                builder.setTitle("실패!!");
                builder.setMessage("조회 실패. !!! [" + retmsg  + "]");
                builder.setPositiveButton("확인", null );
                builder.create().show();

                //finish();
                return;

                }



        }
    }





}
