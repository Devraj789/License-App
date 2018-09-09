package com.devraj.licenseapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    ViewPager viewpager;
    TextView homepage, rules, trafficsignals, questions, trialvideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new SliderpagerActivity(getSupportFragmentManager()));

        homepage = findViewById(R.id.homepage);
        rules = findViewById(R.id.rules);
        trafficsignals = findViewById(R.id.trafficsignals);
        questions = findViewById(R.id.questions);
        trialvideo = findViewById(R.id.trialvideo);

        homepage.setTextColor(Color.BLACK);
        homepage.setBackgroundResource(R.drawable.line_selector);


        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                homepage.setBackgroundColor(Color.WHITE);
                rules.setBackgroundColor(Color.WHITE);
                trafficsignals.setBackgroundColor(Color.WHITE);
                questions.setBackgroundColor(Color.WHITE);
                trialvideo.setBackgroundColor(Color.WHITE);

                homepage.setTextColor(Color.GRAY);
                rules.setTextColor(Color.GRAY);
                trafficsignals.setTextColor(Color.GRAY);
                questions.setTextColor(Color.GRAY);
                trialvideo.setTextColor(Color.GRAY);

                if (position == 0) {
                    homepage.setTextColor(Color.BLACK);
                    homepage.setBackgroundResource(R.drawable.line_selector);

                } else if (position == 1) {
                    rules.setTextColor(Color.BLACK);
                    rules.setBackgroundResource(R.drawable.line_selector);

                } else if (position == 2) {
                    questions.setTextColor(Color.BLACK);
                    questions.setBackgroundResource(R.drawable.line_selector);
                } else if (position == 3) {
                    trafficsignals.setTextColor(Color.BLACK);
                    trafficsignals.setBackgroundResource(R.drawable.line_selector);
                } else {
                    trialvideo.setTextColor(Color.BLACK);
                    trialvideo.setBackgroundResource(R.drawable.line_selector);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void tabclick(View view) {

        homepage.setBackgroundColor(Color.WHITE);
        rules.setBackgroundColor(Color.WHITE);
        trafficsignals.setBackgroundColor(Color.WHITE);
        questions.setBackgroundColor(Color.WHITE);
        trialvideo.setBackgroundColor(Color.WHITE);

        homepage.setTextColor(Color.GRAY);
        rules.setTextColor(Color.GRAY);
        trafficsignals.setTextColor(Color.GRAY);
        questions.setTextColor(Color.GRAY);
        trialvideo.setTextColor(Color.GRAY);

        if (view.getId() == R.id.homepage) {
            homepage.setTextColor(Color.BLACK);
            homepage.setBackgroundResource(R.drawable.line_selector);
            viewpager.setCurrentItem(0);

        } else if (view.getId() == R.id.rules) {
            rules.setTextColor(Color.BLACK);
            rules.setBackgroundResource(R.drawable.line_selector);
            viewpager.setCurrentItem(1);

        } else if (view.getId() == R.id.questions) {

            questions.setTextColor(Color.BLACK);
            questions.setBackgroundResource(R.drawable.line_selector);
            viewpager.setCurrentItem(2);

        } else if (view.getId() == R.id.trafficsignals) {
            trafficsignals.setTextColor(Color.BLACK);
            trafficsignals.setBackgroundResource(R.drawable.line_selector);
            viewpager.setCurrentItem(3);

        } else {
            trialvideo.setTextColor(Color.BLACK);
            trialvideo.setBackgroundResource(R.drawable.line_selector);
            viewpager.setCurrentItem(4);
        }
    }

    public class SliderpagerActivity extends FragmentPagerAdapter {

        public SliderpagerActivity(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomepageFragment();
            } else if (position == 1) {
                return new RulesFragment();
            } else if (position == 2) {
                return new QuestionsFragment();
            } else if (position == 3) {
                return new TrafficsignalsFragment();
            } else {
                return new TrialvideoFragment();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
