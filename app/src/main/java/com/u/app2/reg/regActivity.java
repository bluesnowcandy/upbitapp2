package com.u.app2.reg;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.u.app2.R;
import com.u.app2.httpconnect.*;
import com.u.app2.gong.*;
import com.u.app2.wal.walActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class regActivity extends AppCompatActivity {

    private static final String TAG = "regActivity" ;
    String device_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Log.d(TAG, "seo::regActivity>>start ");
        String serveripaddr = "";

        try{

            Intent intent = getIntent(); /*데이터 수신*/
            serveripaddr = intent.getExtras().getString("ipaddr","") ;

        }catch (Exception e){
            c001.getLogEx(TAG, e);

        }


        SharedPreferences sf = getSharedPreferences(c001.SharedPreferencesName1,MODE_PRIVATE);
        device_id = sf.getString("device_id","");


        Log.d(TAG, "seo::regActivity>>serveripaddr[" + serveripaddr+ "]");

        if ( serveripaddr.equals("")){



            JSONObject jsonObject = new JSONObject();
            try {


                jsonObject.put("stcode" , "SRTJ");
                jsonObject.put("deviceid" , device_id);
                String reqsdatasend = jsonObject.toString();

                ContentValues pams = new ContentValues();
                pams.put("reqsdata",reqsdatasend);
                Log.d(TAG, "seo::reqsdata[" + reqsdatasend + "]");
                NetworkTask networkTask = new NetworkTask("", pams);
                networkTask.execute();


            } catch (Exception e) {
                c001.getLogEx(TAG, e);
            }


/*


            AlertDialog.Builder builder = new AlertDialog.Builder(regActivity.this);
            builder.setTitle("오류 발생.!!");
            builder.setMessage("IP가 없음.");
            builder.setPositiveButton("확인", null );
            builder.create().show();;
            //finish();
            //return;

            //오류 페이지로 이동. !!!
            Intent intentErr = new Intent (this, errorsActivity.class);
            startActivity(intentErr);
            this.finish();
            finish();
            return;*/


        }


        if(!serveripaddr.equals("")){


            TextView tv_ipaddr = (TextView)findViewById(R.id.reg_tv_ipaddr);
            tv_ipaddr.setText(" 서버IP::"+serveripaddr);

        }

        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        //SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        //String getseogoogleid = sf.getString("getseogoogleid","");


        //RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        //Log.d(TAG, "seo::regActivity>>["  + sf.getString("getseogoogleid","") + "]");




        Button regbt =  (Button)findViewById(R.id.regtt);

        Button regbcn =  (Button)findViewById(R.id.regcancle);

        regbcn.setOnClickListener(new Button.OnClickListener() {

              @Override
              public void onClick(View v) {


                  Log.d(TAG, "seo::regcancle::click!! ");

                  Intent intent = new Intent (regActivity.this, walActivity.class);
                  startActivity(intent);
                  finish();

              }

        });

        regbt.setOnClickListener(new Button.OnClickListener() {


                @Override
                public void onClick(View v) {

                    Log.d(TAG, "seo::regtt::click!! ");

                    EditText akeyText = (EditText)findViewById(R.id.akey);
                    String akey = akeyText.getText().toString();
                    Log.d(TAG, "seo::akey[" +akey + "]");

                    EditText skeyText = (EditText)findViewById(R.id.skey);
                    String skey = skeyText.getText().toString();
                    Log.d(TAG, "seo::skey[" +skey + "]");
                    Log.d(TAG, "seo::akey.length()[" +akey.length() + "]");
                    Log.d(TAG, "seo::akey.length()[" +akey.length() + "]");
                    if(akey.length() == 0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(regActivity.this);
                        builder.setTitle("이럴꺼니?");
                        builder.setMessage("access key 입력해야지!");
                        builder.setPositiveButton("확인", null );
                        builder.create().show();

                        //finish();
                        return;
                    }

                    if(skey.length() == 0){

                        AlertDialog.Builder builder = new AlertDialog.Builder(regActivity.this);
                        builder.setTitle("이럴꺼니?");
                        builder.setMessage("secret key 입력해야지!");
                        builder.setPositiveButton("확인", null );
                        builder.create().show();;
                        //finish();
                        return;

                    }

                    JSONObject jsonObject = new JSONObject();

                    // 전송하기. !!

                    try {

                        SharedPreferences sf = getSharedPreferences(c001.SharedPreferencesName1,MODE_PRIVATE);
                        jsonObject.put("stcode" , "DGREH");
                        jsonObject.put("skey" , skey);
                        jsonObject.put("akey" , akey);
                        jsonObject.put("deviceid" , device_id);

                    } catch (JSONException e) {

                        Log.d(TAG, "seo::JSONException[" +e.getMessage() + "]");
                        c001.getLogEx(TAG, e);
                    }
                    String reqsdatasend = jsonObject.toString();

                    ContentValues pams = new ContentValues();

                    pams.put("reqsdata",reqsdatasend);
                    Log.d(TAG, "seo::reqsdata[" + reqsdatasend + "]");

                    NetworkTask networkTask = new NetworkTask("", pams);
                    networkTask.execute();

                }
            }
        );








    }




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
                result = requestHttpURLConnection.request(values);

            } catch (Exception e) {
                c001.getLogEx(TAG, e);
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
            //String list = c001.getJsonValue("list" ,resultjsonObject);
            String keycnt = c001.getJsonValue("keycnt" ,resultjsonObject);
            String serveripaddr = c001.getJsonValue("serveripaddr" ,resultjsonObject);
            String stcode = c001.getJsonValue("stcode" ,resultjsonObject);

            Log.d(TAG, "seo::retcode>> [" +  retcode + "]keycnt>> [" +  keycnt + "]");


            if(stcode.equals("SRTJ") && !serveripaddr.equals("")){
                TextView tv_ipaddr = (TextView)findViewById(R.id.reg_tv_ipaddr);
                tv_ipaddr.setText(" 서버IP::"+serveripaddr);
                return;
            }



            if (!retcode.equals("V001")){


                AlertDialog.Builder builder = new AlertDialog.Builder(regActivity.this);
                builder.setTitle("오류");
                builder.setMessage("저장실패!!!!");
                builder.setPositiveButton("확인", null );
                builder.create().show();;
                //finish();
                return;




            }

            AlertDialog.Builder builder = new AlertDialog.Builder(regActivity.this);
            builder.setTitle("굿!");
            builder.setMessage("저장성공");
            builder.setPositiveButton("확인", nexpage() );
            builder.create().show();;
            //finish();
            //return;




        }
    }

    private DialogInterface.OnClickListener nexpage() {


        Intent intent = new Intent (regActivity.this, walActivity.class);
        startActivity(intent);
        finish();

        return null;
    }


}
