package com.u.app2.wal.pop.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.u.app2.gong.c001;
import com.u.app2.httpconnect.RequestHttpURLConnection;
import com.u.app2.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import com.example.dia.dialogAppLoadingApp;
public class PopupOrderActivity extends Activity {

    TextView txtText;

    String TAG = "PopupOrderActivity";

    String currency = "";
    String device_id = "";
    String all      = "";

    private ListView listView;

    PopupOrderAdpter myadapter;
    ArrayList<PopupOrderInfo> list_PopupOrderInfo;

    ContentValues pams = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_wal_order_activity);

        Log.d(TAG, "seo::>> onCreate !! ");

        //데이터 가져오기
        Intent intent = getIntent();

        Log.d(TAG, "seo::>> onCreate !!111 ");
        currency = intent.getStringExtra("currency");
        device_id = intent.getStringExtra("device_id");
        all      = intent.getStringExtra("all");


        Log.d(TAG, "seo::>> onCreate!![" + currency+ "][" + device_id+ "]");


        listView = (ListView)findViewById(R.id.listView);




















        c001.progressON(PopupOrderActivity.this, "로딩중...");
        PopupOrderActivity.NetworkTask networkTask = new PopupOrderActivity.NetworkTask("", pams);
        networkTask.execute();
    }







    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
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


                Log.d(TAG, "NetworkTask :seo::>> start[" + device_id + "]");


                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("stcode"     , "TSRT");
                jsonObject.put("currency"   , currency);
                jsonObject.put("device_id"  , device_id);
                jsonObject.put("all"        , all);

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

            Log.d(TAG, "onPostExecute : seo::전송결과>> [" +  result + "]");

            JSONObject resultjsonObject =  c001.jsonParser(result);

            String retcode = c001.getJsonValue("retcode" ,resultjsonObject);
            String retmsg  = c001.getJsonValue("retmsg" ,resultjsonObject);
            String list  = c001.getJsonValue("list" ,resultjsonObject);

            Log.d(TAG, "seo::retcode>> [" +  retcode + "]retmsg>> [" +  retmsg + "]");
            Log.d(TAG, "seo::list>> [" +  list + "]retmsg>> [" +  retmsg + "]");



            c001.progressOFF();

            if(retcode.equals("V001")){


                setListView(resultjsonObject);


            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(PopupOrderActivity.this);
                builder.setTitle("실패!!");
                builder.setMessage("조회 실패. !!! [" + retmsg  + "]");
                builder.setPositiveButton("확인", null );
                builder.create().show();

                finish();
                return;

            }


        }
    }




    protected void setListView(JSONObject jsonObject ) {

        Log.d(TAG, "setListView :seo::>> start ");

        try{

            Log.d(TAG, "setListView :seo::>> list[" + jsonObject.get("list") + "]");

            list_PopupOrderInfo = new ArrayList<PopupOrderInfo>();

            JsonParser parser = new JsonParser();

            JsonElement element = parser.parse(jsonObject.get("list").toString());

            JsonArray jArray = element.getAsJsonArray();

            //String serchdtm1 = "";

            int jsiz = jArray.size();


            Log.d(TAG, "setListView :seo::>>>>>> list[" + jsiz + "]");

            if(jsiz < 1){

                Log.d(TAG, "setListView :seo::>>>>>> list[" + jsiz + "]");

                AlertDialog.Builder builder = new AlertDialog.Builder(PopupOrderActivity.this);
                builder.setTitle("실패!!");
                builder.setMessage("조회 내역이 없습니다. !!");
                builder.setPositiveButton("확인",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();;
                    }
                } );
                builder.create().show();


                Log.d(TAG, "setListView :seo::>>>>>> list[" + jsiz + "]");

                //finish();
                return;
            }



            for(int i = 0; i <jArray.size(); i++) {

                JSONObject jsonObject_jArray = c001.jsonParser(jArray.get(i).toString());

                String uuid             = c001.getJsonValue("uuid"      , jsonObject_jArray);
                String side             = c001.getJsonValue("side"      , jsonObject_jArray);
                String ord_type         = c001.getJsonValue("ord_type"  , jsonObject_jArray);
                String price            = c001.getJsonValue("price"     , jsonObject_jArray);
                String state            = c001.getJsonValue("state"     , jsonObject_jArray);
                String market           = c001.getJsonValue("market"    , jsonObject_jArray);
                String created_at       = c001.getJsonValue("created_at", jsonObject_jArray);
                String volume           = c001.getJsonValue("volume"    , jsonObject_jArray);
                String locked           = c001.getJsonValue("locked"    , jsonObject_jArray);
                String executed_volume  = c001.getJsonValue("executed_volume", jsonObject_jArray);
                String gu               = c001.getJsonValue("gu"        , jsonObject_jArray);

                Log.d(TAG, "setWelListView :seo::>>[" +  market + "][" +side +"][" + ord_type + "][" + price + "]");

                PopupOrderInfo  info_1 = new PopupOrderInfo(
                        market
                        ,uuid
                        ,side
                        ,ord_type
                        ,price
                        ,state
                        ,market
                        ,created_at
                        ,volume
                        ,locked
                        ,executed_volume
                        ,gu
                );

                list_PopupOrderInfo.add(info_1);


            }

           // Log.d(TAG, "setWelListView :seo::>>[" + serchdtm1 + "]");

            myadapter = new PopupOrderAdpter(this, getApplicationContext(),R.layout.popup_wal_order_list, list_PopupOrderInfo);
            listView.setAdapter(myadapter);

        }catch (Exception E){
            c001.getLogEx(TAG, E);
        }finally {
            Log.d(TAG, "setWelListView :seo::>> finally ");

        }


    }

}

