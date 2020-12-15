package com.u.app2.getInfo;

import android.content.Context;
import android.media.MediaDrm;
import android.media.UnsupportedSchemeException;
import android.os.Build;
import android.util.Log;

import java.util.UUID;

public class getDevicesInfo {


    public static String GetDevicesUUID(Context mContext) throws UnsupportedSchemeException {

        String tmDevice = "", tmSerial = "", androidId = "";
        UUID deviceUuid;
        String deviceId = "";


        try {

                Log.d("seo::", "SDK_INT::" + String.valueOf(Build.VERSION.SDK_INT));
                Log.d("seo::", "VERSION_CODES::" + String.valueOf(Build.VERSION_CODES.Q));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    deviceUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
                    MediaDrm mediaDrm = new MediaDrm(deviceUuid);
                    deviceId = android.util.Base64.encodeToString(mediaDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID), 0).trim();
                }else{

                    deviceUuid = UUID.randomUUID();
                    deviceId = deviceUuid.toString();
                }


        } catch (Exception e) {
            e.printStackTrace();
        }



        Log.d("seo::","GetDevicesUUID>>deviceId::" +deviceId);
        return deviceId;
    }

}



