package com.github.xb10.scorecard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Simple Custom layout manager to be used to make recyclerview not scrollable
 * http://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling
 */
public class CustomLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        isScrollEnabled = scrollEnabled;
    }

    @Override
    public boolean canScrollVertically(){

        return isScrollEnabled && super.canScrollVertically();
    }
}
