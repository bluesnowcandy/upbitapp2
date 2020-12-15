package com.u.app2;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.u.app2.errors.errorsActivity;
import com.u.app2.getInfo.getDevicesInfo;
import com.u.app2.gong.c001;
import com.u.app2.httpconnect.RequestHttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    public static Activity ps_SplashActivity;
    static String TAG = "seo::SplashActivity";
    public String device_id = "";
    public String app_version  = c001.app_version;
    boolean loop = true;
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        setContentView(R.layout.activity_splash);

        ps_SplashActivity = SplashActivity.this;

        try {
            Log.d(TAG, "Start !!");


            //이미지
            /**
            ImageView load =  (ImageView)findViewById(R.id.loading);
            load.setBackground(new ShapeDrawable(new OvalShape()));  //동그랗게.
            load.setClipToOutline(true); //동그랗게
            Glide.with(this).load(R.drawable.loading_main).into(load);

             **/


            device_id = getDevicesInfo.GetDevicesUUID(this);

            SharedPreferences sf = getSharedPreferences(c001.SharedPreferencesName1,MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.putString("device_id", device_id);
            editor.commit();
            Log.d(TAG, "device_id>>>" + device_id);

        } catch (Exception e) {
            c001.getLogEx(TAG, e);
        }


        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
        final Runnable runnableC = new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();

                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String strTime = sdf.format(cal.getTime());
                    Log.d(TAG + "run", "Splash time[" +strTime + "]");
                    TextView clockTextView ;
                    clockTextView = findViewById(R.id.act_splch_clock);
                    clockTextView.setText(strTime);



                }catch (Exception e){
                    Thread.currentThread().interrupt();
                    c001.getLogEx(TAG, e);


                }
            }
        } ;

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnableSplashClock  implements Runnable {
            @Override
            public void run() {

                while (loop) {
                    try {

                        Thread.sleep(1000);
                        // 메인 스레드에 runnable 전달.
                        runOnUiThread(runnableC) ;
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                       c001.getLogEx(TAG, e);
                    }


                }
            }
        }






        NewRunnableSplashClock nr = new NewRunnableSplashClock() ;
        t = new Thread(nr) ;
        t.start() ;

        //t.interrupt();


        NetworkTask networkTask = new NetworkTask("", null);
        networkTask.execute();





           //3초뒤에 화면 이동.
/*            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            },3000);*/
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
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

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("version" , app_version);
                jsonObject.put("stcode" , "VERC");
                jsonObject.put("deviceid" , device_id);

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

            if(result == null){

                Log.d(TAG, "seo::001>>result null !!! [ERR 이동]");
                //망했다.
                Intent intent = new Intent (SplashActivity.this, errorsActivity.class);
                startActivity(intent);
                loop = false;
                SplashActivity.this.finish();
                finish();
                return;

            }


            JSONObject resultjsonObject =  c001.jsonParser(result);

            String retcode      = c001.getJsonValue("retcode" ,resultjsonObject);
            String keycnt       = c001.getJsonValue("keycnt" ,resultjsonObject);
            String versionchk   = c001.getJsonValue("versionchk" ,resultjsonObject);
            String regdeviceid  = c001.getJsonValue("regdeviceid" ,resultjsonObject);
            String ipaddr       = c001.getJsonValue("ipaddr" ,resultjsonObject);


            Log.d(TAG, "seo::retcode>> [" +  retcode + "]keycnt>> [" +  keycnt + "][" +versionchk  + "]");


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(retcode.equals("EXGS")){
                Log.d(TAG, "seo::001>>1");
                loop = false;
                //망했다.
                //moveTaskToBack(true);						// 태스크를 백그라운드로 이동
                //finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
                //android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
                finishAffinity();
                System.runFinalization();
                System.exit(0);

                return;
            }else if(!retcode.equals("V000")){
                Log.d(TAG, "seo::001>>1" +retcode);
                //망했다.
                //Intent intent = new Intent (SplashActivity.this,errorsActivity.class);
                //startActivity(intent);
                finish();
                return;
            }else if(keycnt.equals("0") || regdeviceid.equals("Y")){
                Log.d(TAG, "seo::001>>2 [등록페이지로 이동]");
                //등록페이지로 이동.
                //Intent intent = new Intent (SplashActivity.this, regActivity.class);
                //intent.putExtra("ipaddr",ipaddr);
                //startActivity(intent);

                loop = false;
                SplashActivity.this.finish();

                return;
            }else if (keycnt.equals("1")){
                Log.d(TAG, "seo::001>>3 [지갑조회페이지로 이동]");
                //지갑조회페이지로 이동.
                //Intent intent = new Intent (SplashActivity.this , walActivity.class);
                //startActivity(intent);

                loop = false;
                SplashActivity.this.finish();



                return;
            }else{
                Log.d(TAG, "seo::001>>4 [ERR 이동]");
                //망했다.
                Intent intent = new Intent (SplashActivity.this, errorsActivity.class);
                startActivity(intent);
                loop = false;
                SplashActivity.this.finish();

                finish();
                return;
            }



        }
    }

}
