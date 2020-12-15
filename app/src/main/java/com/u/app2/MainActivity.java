package com.u.app2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.u.app2.gong.c001;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    static String TAG = "seo::MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "Start !!");

        try{


            SplashActivity sp_Activity =(SplashActivity) SplashActivity.ps_SplashActivity;
            sp_Activity.loop = false;
            sp_Activity.t.interrupt();
            sp_Activity.finish();

        }catch (Exception e){


            c001.getLogEx(TAG, e);
        }


        Log.d(TAG, "SplashActivity finish()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
