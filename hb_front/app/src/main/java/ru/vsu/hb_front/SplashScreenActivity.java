package ru.vsu.hb_front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.ActivitySplashScreenBinding;
import ru.vsu.hb_front.store.PreferenceStore;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 2000;
    private ActivitySplashScreenBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Api.init("http://185.246.66.190:8080");
            PreferenceStore.init(getApplicationContext());
            if(PreferenceStore.getInstance().getToken()==null || PreferenceStore.getInstance().getToken().length()==0){
                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
            }else{
                b.helloText.setText("Привет, "+PreferenceStore.getInstance().getName()+"!");
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
            }
            SplashScreenActivity.this.finish();
        }, SPLASH_DISPLAY_LENGHT);

    }
}