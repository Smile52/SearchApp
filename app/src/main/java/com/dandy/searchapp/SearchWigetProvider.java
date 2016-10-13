package com.dandy.searchapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

/**
 * Created by Dandy on 2016/10/13.
 */

public class SearchWigetProvider  extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SearchRemoteView remoteView=new SearchRemoteView(context);
        remoteView.setOpenAppClickListener();
    }

    //第一次创建时调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        SearchRemoteView remoteView=new SearchRemoteView(context);
        remoteView.setOpenAppClickListener();
    }
}
