package com.u.app2.gong;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.icu.util.Currency;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.u.app2.R;

import org.json.JSONObject;

import java.util.Locale;

;

public class c001 {

    //public static String version;
    static AppCompatDialog progressDialog;
    public static String url = "http://192.168.55.132:8080/seoEx/rcv.jsp";
    public static String app_version = "asdgasgae3w";
    public static String SharedPreferencesName1 = "asdgasgae3w";
    private static String TAG = "c001";

    public static String won = Currency.getInstance(Locale.KOREA).getSymbol();

    public static void sleepToRunSet(int sleepSec) {



        try {




            sleepSec = sleepSec * 1000;

            Thread.sleep(sleepSec);


        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }




    public static void getLogEx ( String tag ,  Exception E ) {


        Log.d(TAG, "getLogEx :seo::>>start  ");

        try {

            StackTraceElement[] exTra = E.getStackTrace();
            for (int ex = 0; ex < exTra.length ; ex++){
                Log.d(TAG, "getLogEx :seo::>>Exception [" + exTra[ex] + "] ");
            }

            Log.d(TAG, "getLogEx :seo::>>Exception [" + E.getMessage() + "] ");

        } catch (Exception e) {

            StackTraceElement[] exTra = e.getStackTrace();
            for (int ex = 0; ex < exTra.length ; ex++){
                Log.d(TAG, "getLogEx :seo::>>XXXXX [" + exTra[ex] + "] ");
            }

            Log.d(TAG, "getLogEx :seo::>>XXXXX [" + e.getMessage() + "] ");



        } finally {

            Log.d(TAG, "getLogEx :seo::>>to finally");
            //setlog(logpath, "finally");

        }


    }


    public static void alterMsg(Context screnContext, String title, String msg){

        try{

            AlertDialog.Builder builder = new AlertDialog.Builder(screnContext);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("확인", null );
            builder.create().show();

        } catch (Exception e) {

            getLogEx("alterMsg",e);
        }

        return;

    }

    public static <JSONParser> JSONObject jsonParser ( String jsonData ) {

        JSONObject return_object = null;


        try {

            //setlog(logpath, "START");

            JSONObject jsonObject = new JSONObject(jsonData);

            //JSON데이터를 넣어 JSON Object 로 만들어 준다.
            return_object = jsonObject;

            //setlog(logpath, "return_object Size[" + return_object.size()  + "]");

        } catch (Exception e) {


            Log.d(TAG, "Exception message[" + e.getMessage() + "]");

            e.printStackTrace();

        } finally {

            //setlog(logpath, "finally");

        }

        return return_object;
    }


    public static String getJsonValue(String jKey, JSONObject jsonob) {

        try{



            String result = jsonob.get(jKey) != null ? jsonob.get(jKey).toString() : "";

            //String result = element.getElementsByTagName(sTag).item(0).getTextContent();

            return result;

        } catch(NullPointerException e){

            return "";

        } catch(Exception e){

            return "";

        }

    }




    public static void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();



        }


        //final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        img_loading_frame.setBackground(new ShapeDrawable(new OvalShape()));  //동그랗게.
        img_loading_frame.setClipToOutline(true); //동그랗게.
        //final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                //   frameAnimation.start();
            }
        });

        Glide.with(activity).load(R.drawable.loading_pop).into(img_loading_frame);
        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public static void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public static void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

