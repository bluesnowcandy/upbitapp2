package com.u.app2.wal.pop.config;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.u.app2.gong.c001;
import com.u.app2.httpconnect.RequestHttpURLConnection;
import com.u.app2.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

//import com.example.dia.dialogAppLoadingApp;
public class PopupConfigActivity extends Activity {
    TextView txtText;

    String TAG = "PopupConfigActivity";

    String currency = "";
    String device_id = "";


    ContentValues pams = new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_wal_config);


        Log.d(TAG, "seo::>> onCreate");

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.configcurrency);

        //데이터 가져오기
        Intent intent = getIntent();

        Log.d(TAG, "seo::>> onCreate get Extra[" + intent.getDataString() + "]");
        currency = intent.getStringExtra("currency");
        device_id = intent.getStringExtra("device_id");


        Log.d(TAG, "seo::>> onCreate!![" + currency+ "][" + device_id+ "]");

        txtText.setText(currency);

        c001.progressON(PopupConfigActivity.this, "로딩중...");
        PopupConfigActivity.NetworkTask networkTask = new PopupConfigActivity.NetworkTask("", pams);
        networkTask.execute();
    }

    //저장버튼 클릭
    public void mOnSave(View v){

        c001.progressON(PopupConfigActivity.this, "로딩중...");
        PopupConfigActivity.NetworkTaskSave networkTaskSave = new PopupConfigActivity.NetworkTaskSave("", pams);
        networkTaskSave.execute();


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


                Log.d(TAG, "NetworkTask :seo::>> start ");


                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("stcode" , "ERHI4");
                jsonObject.put("currency" , currency);
                jsonObject.put("device_id" , device_id);

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

            Log.d(TAG, "seo::retcode>> [" +  retcode + "]retmsg>> [" +  retmsg + "]");

            c001.progressOFF();
            if(retcode.equals("V001")){


                String data = c001.getJsonValue("list" ,resultjsonObject);


                Log.d(TAG, "seo::retcode>> [" +  data + "]");



                JsonParser parser = new JsonParser();

                JsonElement element = parser.parse(data);

                JsonArray jArray = element.getAsJsonArray();


                Log.d(TAG, "seo::retcode>> [" +   jArray.size() + "]");


                if ( jArray.size() != 1 ){

                    c001.alterMsg(PopupConfigActivity.this, "[SDGU]","진행 할 수 없습니다.");
/*

                    AlertDialog.Builder builder = new AlertDialog.Builder(PopupConfigActivity.this);
                    builder.setTitle("실패!!");
                    builder.setMessage("조회 실패. !!! [TG#S]");
                    builder.setPositiveButton("확인", null );
                    builder.create().show();
*/

                    //finish();
                    return;

                }

                JSONObject jsonObject_jArray = c001.jsonParser(jArray.get(0).toString());

                String currncy          = c001.getJsonValue("currency", jsonObject_jArray);
                String bal              = c001.getJsonValue("balance", jsonObject_jArray);
                String avg_buy_price    = c001.getJsonValue("avg_buy_price", jsonObject_jArray);


                String currency_now_price    = c001.getJsonValue("trade_price", jsonObject_jArray);


                //이제부터 시작.

                String h1_chbox         = c001.getJsonValue("h1_useyn", jsonObject_jArray);
                String h1_sel_cnt1      = c001.getJsonValue("h1_sel_cnt1", jsonObject_jArray);
                String h1_sel_per1      = c001.getJsonValue("h1_sel_per1", jsonObject_jArray);
                String h1_sel_per2      = "";




                String h2_chbox         = c001.getJsonValue("h2_useyn", jsonObject_jArray);
                String h2_sel_cnt1      = c001.getJsonValue("h2_sel_cnt1", jsonObject_jArray);
                String h2_sel_per1      = c001.getJsonValue("h2_sel_per1", jsonObject_jArray);
                String h2_sel_per2      = "";

                String h4_chbox         = c001.getJsonValue("h4_useyn", jsonObject_jArray);
                String h4_sel_cnt1      = c001.getJsonValue("h4_sel_cnt1", jsonObject_jArray);
                String h4_sel_per1      = c001.getJsonValue("h4_sel_per1", jsonObject_jArray);
                String h4_sel_per2      = c001.getJsonValue("h4_sel_per2", jsonObject_jArray);

                String h6_chbox         = c001.getJsonValue("h6_useyn", jsonObject_jArray);
                String h6_sel_cnt1      = c001.getJsonValue("h6_sel_cnt1", jsonObject_jArray);
                String h6_sel_per1      = c001.getJsonValue("h6_sel_per1", jsonObject_jArray);
                String h6_sel_per2      = c001.getJsonValue("h6_sel_per2", jsonObject_jArray);



                String h12_chbox         = c001.getJsonValue("h12_useyn", jsonObject_jArray);
                String h12_sel_cnt1      = c001.getJsonValue("h12_sel_cnt1", jsonObject_jArray);
                String h12_sel_per1      = c001.getJsonValue("h12_sel_per1", jsonObject_jArray);
                String h12_sel_per2      = c001.getJsonValue("h12_sel_per2", jsonObject_jArray);



                String d1_chbox         = c001.getJsonValue("d1_useyn", jsonObject_jArray);
                String d1_sel_cnt1      = c001.getJsonValue("d1_sel_cnt1", jsonObject_jArray);
                String d1_sel_per1      = c001.getJsonValue("d1_sel_per1", jsonObject_jArray);
                String d1_sel_per2      = c001.getJsonValue("d1_sel_per2", jsonObject_jArray);

                String d2_chbox         = c001.getJsonValue("d2_useyn", jsonObject_jArray);
                String d2_sel_cnt1      = c001.getJsonValue("d2_sel_cnt1", jsonObject_jArray);
                String d2_sel_per1      = c001.getJsonValue("d2_sel_per1", jsonObject_jArray);
                String d2_sel_per2      = c001.getJsonValue("d2_sel_per2", jsonObject_jArray);

                String d4_chbox         = c001.getJsonValue("d4_useyn", jsonObject_jArray);
                String d4_sel_cnt1      = c001.getJsonValue("d4_sel_cnt1", jsonObject_jArray);
                String d4_sel_per1      = c001.getJsonValue("d4_sel_per1", jsonObject_jArray);
                String d4_sel_per2      = c001.getJsonValue("d4_sel_per2", jsonObject_jArray);

                String d7_chbox         = c001.getJsonValue("d7_useyn", jsonObject_jArray);
                String d7_sel_cnt1      = c001.getJsonValue("d7_sel_cnt1", jsonObject_jArray);
                String d7_sel_per1      = c001.getJsonValue("d7_sel_per1", jsonObject_jArray);
                String d7_sel_per2      = c001.getJsonValue("d7_sel_per2", jsonObject_jArray);

                /**
                 * config_chk1h
                 * config_sel_cnt1_h1
                 * config_sel_per1_h1
                 * config_sel_per2_h1
                 */








                TextView txconfigcurrency1      = (TextView)findViewById(R.id.configcurrency1);
                TextView configcurrency1h       = (TextView)findViewById(R.id.configcurrency1h);
                TextView configcurrency2h       = (TextView)findViewById(R.id.configcurrency2h);
                TextView configcurrency4h       = (TextView)findViewById(R.id.configcurrency4h);
                TextView configcurrency6h       = (TextView)findViewById(R.id.configcurrency6h);
                TextView configcurrency12h      = (TextView)findViewById(R.id.configcurrency12h);
                TextView configcurrency1d      = (TextView)findViewById(R.id.configcurrency1d);
                TextView configcurrency2d      = (TextView)findViewById(R.id.configcurrency2d);
                TextView configcurrency4d      = (TextView)findViewById(R.id.configcurrency4d);
                TextView configcurrency7d      = (TextView)findViewById(R.id.configcurrency7d);
                TextView txconfigcurrency_bal   = (TextView)findViewById(R.id.configcurrency_bal);
                TextView configavg_buy_price    = (TextView)findViewById(R.id.configavg_buy_price);
                TextView confignow_price        = (TextView)findViewById(R.id.confignow_price);





                // 화면에서 가져오기.
                CheckBox h1_checkBox                      = (CheckBox)findViewById(R.id.config_chk1h);
                EditText editText_h1_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_h1);
                EditText editText_h1_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_h1);
                TextView editText_h1_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_h1);


                CheckBox h2_checkBox                      = (CheckBox)findViewById(R.id.config_chk2h);
                EditText editText_h2_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_h2);
                EditText editText_h2_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_h2);
                TextView editText_h2_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_h2);

                CheckBox h4_checkBox                      = (CheckBox)findViewById(R.id.config_chk4h);
                EditText editText_h4_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_h4);
                EditText editText_h4_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_h4);
                TextView editText_h4_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_h4);

                CheckBox h6_checkBox                      = (CheckBox)findViewById(R.id.config_chk6h);
                EditText editText_h6_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_h6);
                EditText editText_h6_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_h6);
                TextView editText_h6_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_h6);

                CheckBox h12_checkBox                      = (CheckBox)findViewById(R.id.config_chk12h);
                EditText editText_h12_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_h12);
                EditText editText_h12_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_h12);
                TextView editText_h12_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_h12);


                CheckBox d1_checkBox                      = (CheckBox)findViewById(R.id.config_chk1d);
                EditText editText_d1_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_d1);
                EditText editText_d1_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_d1);
                TextView editText_d1_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_d1);

                CheckBox d2_checkBox                      = (CheckBox)findViewById(R.id.config_chk2d);
                EditText editText_d2_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_d2);
                EditText editText_d2_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_d2);
                TextView editText_d2_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_d2);

                CheckBox d4_checkBox                      = (CheckBox)findViewById(R.id.config_chk4d);
                EditText editText_d4_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_d4);
                EditText editText_d4_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_d4);
                TextView editText_d4_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_d4);


                CheckBox d7_checkBox                      = (CheckBox)findViewById(R.id.config_chk7d);
                EditText editText_d7_sel_cnt1             = (EditText)findViewById(R.id.config_sel_cnt1_d7);
                EditText editText_d7_sel_per1             = (EditText)findViewById(R.id.config_sel_per1_d7);
                TextView editText_d7_sel_per2             = (TextView)findViewById(R.id.config_sel_per2_d7);

                //화면에 넣기.
                txconfigcurrency1.setText(currncy);
                txconfigcurrency_bal.setText(bal);
                configavg_buy_price.setText(avg_buy_price);
                confignow_price.setText(currency_now_price);

                if(h1_chbox.equals("Y")){h1_checkBox.setChecked(true);}
                editText_h1_sel_cnt1.setText(h1_sel_cnt1);
                editText_h1_sel_per1.setText(h1_sel_per1);
                editText_h1_sel_per2.setText(h1_sel_per2);
                configcurrency1h.setText(currncy);


                if(h2_chbox.equals("Y")){h2_checkBox.setChecked(true);}
                editText_h2_sel_cnt1.setText(h2_sel_cnt1);
                editText_h2_sel_per1.setText(h2_sel_per1);
                editText_h2_sel_per2.setText(h2_sel_per2);
                configcurrency2h.setText(currncy);



                if(h4_chbox.equals("Y")){h4_checkBox.setChecked(true);}
                editText_h4_sel_cnt1.setText(h4_sel_cnt1);
                editText_h4_sel_per1.setText(h4_sel_per1);
                editText_h4_sel_per2.setText(h4_sel_per2);
                configcurrency4h.setText(currncy);




                if(h6_chbox.equals("Y")){h6_checkBox.setChecked(true);}
                editText_h6_sel_cnt1.setText(h6_sel_cnt1);
                editText_h6_sel_per1.setText(h6_sel_per1);
                editText_h6_sel_per2.setText(h6_sel_per2);
                configcurrency6h.setText(currncy);

                if(h12_chbox.equals("Y")){h12_checkBox.setChecked(true);}
                editText_h12_sel_cnt1.setText(h12_sel_cnt1);
                editText_h12_sel_per1.setText(h12_sel_per1);
                editText_h12_sel_per2.setText(h12_sel_per2);
                configcurrency12h.setText(currncy);

                if(d1_chbox.equals("Y")){d1_checkBox.setChecked(true);}
                editText_d1_sel_cnt1.setText(d1_sel_cnt1);
                editText_d1_sel_per1.setText(d1_sel_per1);
                editText_d1_sel_per2.setText(d1_sel_per2);
                configcurrency1d.setText(currncy);


                if(d2_chbox.equals("Y")){d2_checkBox.setChecked(true);}
                editText_d2_sel_cnt1.setText(d2_sel_cnt1);
                editText_d2_sel_per1.setText(d2_sel_per1);
                editText_d2_sel_per2.setText(d2_sel_per2);
                configcurrency2d.setText(currncy);


                if(d4_chbox.equals("Y")){d4_checkBox.setChecked(true);}
                editText_d4_sel_cnt1.setText(d4_sel_cnt1);
                editText_d4_sel_per1.setText(d4_sel_per1);
                editText_d4_sel_per2.setText(d4_sel_per2);
                configcurrency4d.setText(currncy);


                if(d7_chbox.equals("Y")){d7_checkBox.setChecked(true);}
                editText_d7_sel_cnt1.setText(d7_sel_cnt1);
                editText_d7_sel_per1.setText(d7_sel_per1);
                editText_d7_sel_per2.setText(d7_sel_per2);
                configcurrency7d.setText(currncy);





            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(PopupConfigActivity.this);
                builder.setTitle("실패!!");
                builder.setMessage("조회 실패. !!! [" + retmsg  + "]");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();;
                    }
                }  );
                builder.create().show();

                //finish();
                return;

            }



        }
    }


    /**
     * 저장하기.
     */
    public class NetworkTaskSave extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTaskSave(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }



        @Override
        protected String doInBackground(Void... params) {

            String result = ""; // 요청 결과를 저장할 변수.


            try {


                Log.d(TAG, "NetworkTaskSave :seo::>> start ");


                RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();


                JSONObject jsonObject = new JSONObject();

                jsonObject.put("stcode" , "SDRH");
                jsonObject.put("currency" , currency);
                jsonObject.put("device_id" , device_id);



                CheckBox config_chk1h = (CheckBox)findViewById(R.id.config_chk1h);
                if(config_chk1h.isChecked()){jsonObject.put("config_chk1h" , "Y");}
                jsonObject.put("sel_cnt1_1h" , ((EditText) findViewById(R.id.config_sel_cnt1_h1)).getText().toString() );
                jsonObject.put("sel_per1_1h" , ((EditText) findViewById(R.id.config_sel_per1_h1)).getText().toString() );



                CheckBox config_chk2h = (CheckBox)findViewById(R.id.config_chk2h);
                if(config_chk2h.isChecked()){jsonObject.put("config_chk2h" , "Y");}
                jsonObject.put("sel_cnt1_2h" , ((EditText) findViewById(R.id.config_sel_cnt1_h2)).getText().toString() );
                jsonObject.put("sel_per1_2h" , ((EditText) findViewById(R.id.config_sel_per1_h2)).getText().toString() );


                CheckBox config_chk4h = (CheckBox)findViewById(R.id.config_chk4h);
                if(config_chk4h.isChecked()){jsonObject.put("config_chk4h" , "Y");}
                jsonObject.put("sel_cnt1_4h" , ((EditText) findViewById(R.id.config_sel_cnt1_h4)).getText().toString() );
                jsonObject.put("sel_per1_4h" , ((EditText) findViewById(R.id.config_sel_per1_h4)).getText().toString() );
                jsonObject.put("sel_per2_4h" , ((EditText) findViewById(R.id.config_sel_per2_h4)).getText().toString() );


                CheckBox config_chk6h = (CheckBox)findViewById(R.id.config_chk6h);
                if(config_chk6h.isChecked()){jsonObject.put("config_chk6h" , "Y");}
                jsonObject.put("sel_cnt1_6h" , ((EditText) findViewById(R.id.config_sel_cnt1_h6)).getText().toString() );
                jsonObject.put("sel_per1_6h" , ((EditText) findViewById(R.id.config_sel_per1_h6)).getText().toString() );
                jsonObject.put("sel_per2_6h" , ((EditText) findViewById(R.id.config_sel_per2_h6)).getText().toString() );



                CheckBox config_chk12h = (CheckBox)findViewById(R.id.config_chk12h);
                if(config_chk12h.isChecked()){jsonObject.put("config_chk12h" , "Y");}
                jsonObject.put("sel_cnt1_12h" , ((EditText) findViewById(R.id.config_sel_cnt1_h12)).getText().toString() );
                jsonObject.put("sel_per1_12h" , ((EditText) findViewById(R.id.config_sel_per1_h12)).getText().toString() );
                jsonObject.put("sel_per2_12h" , ((EditText) findViewById(R.id.config_sel_per2_h12)).getText().toString() );


                CheckBox config_chk1d = (CheckBox)findViewById(R.id.config_chk1d);
                if(config_chk1d.isChecked()){jsonObject.put("config_chk1d" , "Y");}
                jsonObject.put("sel_cnt1_1d" , ((EditText) findViewById(R.id.config_sel_cnt1_d1)).getText().toString() );
                jsonObject.put("sel_per1_1d" , ((EditText) findViewById(R.id.config_sel_per1_d1)).getText().toString() );
                jsonObject.put("sel_per2_1d" , ((EditText) findViewById(R.id.config_sel_per2_d1)).getText().toString() );



                CheckBox config_chk2d = (CheckBox)findViewById(R.id.config_chk2d);
                if(config_chk2d.isChecked()){jsonObject.put("config_chk2d" , "Y");}
                jsonObject.put("sel_cnt1_2d" , ((EditText) findViewById(R.id.config_sel_cnt1_d2)).getText().toString() );
                jsonObject.put("sel_per1_2d" , ((EditText) findViewById(R.id.config_sel_per1_d2)).getText().toString() );
                jsonObject.put("sel_per2_2d" , ((EditText) findViewById(R.id.config_sel_per2_d2)).getText().toString() );



                CheckBox config_chk4d = (CheckBox)findViewById(R.id.config_chk4d);
                if(config_chk4d.isChecked()){jsonObject.put("config_chk4d" , "Y");}
                jsonObject.put("sel_cnt1_4d" , ((EditText) findViewById(R.id.config_sel_cnt1_d4)).getText().toString() );
                jsonObject.put("sel_per1_4d" , ((EditText) findViewById(R.id.config_sel_per1_d4)).getText().toString() );
                jsonObject.put("sel_per2_4d" , ((EditText) findViewById(R.id.config_sel_per2_d4)).getText().toString() );


                CheckBox config_chk7d = (CheckBox)findViewById(R.id.config_chk7d);
                if(config_chk7d.isChecked()){jsonObject.put("config_chk7d" , "Y");}
                jsonObject.put("sel_cnt1_7d" , ((EditText) findViewById(R.id.config_sel_cnt1_d7)).getText().toString() );
                jsonObject.put("sel_per1_7d" , ((EditText) findViewById(R.id.config_sel_per1_d7)).getText().toString() );
                jsonObject.put("sel_per2_7d" , ((EditText) findViewById(R.id.config_sel_per2_d7)).getText().toString() );




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


            Log.d(TAG, "onPostExecute : seo::전송결과>> [" +  result + "]");

            JSONObject resultjsonObject =  c001.jsonParser(result);

            String retcode = c001.getJsonValue("retcode" ,resultjsonObject);
            String retmsg  = c001.getJsonValue("retmsg" ,resultjsonObject);

            Log.d(TAG, "seo::retcode>> [" +  retcode + "]retmsg>> [" +  retmsg + "]");

            c001.progressOFF();
            if(retcode.equals("V001")){

                AlertDialog.Builder builder = new AlertDialog.Builder(PopupConfigActivity.this);
                builder.setTitle("성공 !!");
                builder.setMessage("저장 성공 !!! [" + retmsg  + "]");
                builder.setPositiveButton("확인", null );
                builder.create().show();

                finish();


            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(PopupConfigActivity.this);
                builder.setTitle("실패!!");
                builder.setMessage("저장 실패 !!! [" + retmsg  + "]");
                builder.setPositiveButton("확인", null );
                builder.create().show();

                //finish();
                return;

            }



        }
    }


}

