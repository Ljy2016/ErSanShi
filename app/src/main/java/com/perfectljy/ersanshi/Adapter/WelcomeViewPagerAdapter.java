package com.perfectljy.ersanshi.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by PerfectLjy on 2016/3/13.
 * 引导页ViewPager的Adapter
 */
public class WelcomeViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public WelcomeViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }

}
