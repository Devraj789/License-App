package com.devraj.licenseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TrialvideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialvideo);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new TrialvideoFragment()).commit();
    }
}
