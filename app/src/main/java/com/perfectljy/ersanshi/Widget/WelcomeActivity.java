package com.perfectljy.ersanshi.Widget;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.perfectljy.ersanshi.Adapter.WelcomeViewPagerAdapter;
import com.perfectljy.ersanshi.MyView.MyTextViewDelay;
import com.perfectljy.ersanshi.R;
import com.perfectljy.ersanshi.Transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    //自定义的textview，用于逐字显示
    private MyTextViewDelay textViewDelay1;
    private MyTextViewDelay textViewDelay2;
    private MyTextViewDelay textViewDelay3;
    private MyTextViewDelay textViewDelay4;
    private MyTextViewDelay textViewDelay5;
    private MyTextViewDelay textViewDelay6;
    private MyTextViewDelay textViewDelay7;
    private MyTextViewDelay textViewDelay8;
    private ImageButton startBt;

    private ViewPager viewPager;
    private WelcomeViewPagerAdapter welcomeViewPagerAdapter;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        views = new ArrayList<>();
        init();
        viewPager = (ViewPager) findViewById(R.id.WelcomViewPager);
        welcomeViewPagerAdapter = new WelcomeViewPagerAdapter(views);
        viewPager.setAdapter(welcomeViewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(this);
        textViewDelay1.Begin(200);
        textViewDelay2.Begin(300);
        startBt.setOnClickListener(this);
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.welcomelayout, null, false);
        View view1 = inflater.inflate(R.layout.welcomelayout1, null, false);
        View view2 = inflater.inflate(R.layout.welcomelayout2, null, false);
        View view3 = inflater.inflate(R.layout.welcomelayout3, null, false);
        View view4 = inflater.inflate(R.layout.welcomelayout4, null, false);
        textViewDelay1 = (MyTextViewDelay) view.findViewById(R.id.MyView1);
        textViewDelay2 = (MyTextViewDelay) view.findViewById(R.id.MyView2);
        textViewDelay3 = (MyTextViewDelay) view1.findViewById(R.id.MyView3);
        textViewDelay4 = (MyTextViewDelay) view1.findViewById(R.id.MyView4);
        textViewDelay5 = (MyTextViewDelay) view2.findViewById(R.id.MyView5);
        textViewDelay6 = (MyTextViewDelay) view2.findViewById(R.id.MyView6);
        textViewDelay7 = (MyTextViewDelay) view3.findViewById(R.id.MyView7);
        textViewDelay8 = (MyTextViewDelay) view3.findViewById(R.id.MyView8);
        startBt = (ImageButton) view4.findViewById(R.id.startBt);
        views.add(view);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(WelcomeActivity.this, SafeActivity.class));
        this.finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //每次滑动判断页面，启动文字显示
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                textViewDelay1.Begin(200);
                textViewDelay2.Begin(300);
                break;
            case 1:
                textViewDelay3.Begin(200);
                textViewDelay4.Begin(300);
                break;
            case 2:
                textViewDelay5.Begin(200);
                textViewDelay6.Begin(300);
                break;
            case 3:
                textViewDelay7.Begin(200);
                textViewDelay8.Begin(300);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
