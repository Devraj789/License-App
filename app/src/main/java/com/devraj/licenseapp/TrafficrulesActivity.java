package com.devraj.licenseapp;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TrafficrulesActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trafficrules);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        TrafficsignalsFragment trafficsignalsFragment = new TrafficsignalsFragment();

        fragmentTransaction.replace(R.id.framelayout, trafficsignalsFragment);
        fragmentTransaction.commit();
    }
}
