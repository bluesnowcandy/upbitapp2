package com.u.app2.errors;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.u.app2.R;
import android.os.Bundle;
import android.widget.ImageView;

public class errorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        ImageView load =  (ImageView)findViewById(R.id.error);
        Glide.with(this).load(R.drawable.error).into(load);
    }
}
