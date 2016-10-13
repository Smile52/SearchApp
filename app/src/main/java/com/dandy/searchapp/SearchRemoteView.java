package com.dandy.searchapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.widget.RemoteViews;

/**
 * Created by Dandy on 2016/10/13.
 */

public class SearchRemoteView extends RemoteViews {
    private Context mContext;

    private Class<? extends AppWidgetProvider> getAppWidgetProvider() {
        return SearchWigetProvider.class;
    }
    public SearchRemoteView(Context context) {
        super(context.getPackageName(), R.layout.search_wiget);
        init(context);
    }

    public SearchRemoteView(Parcel parcel) {
        super(parcel);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void setOpenAppClickListener(){
        Intent intent = getProviderIntent();
        intent.setClass(mContext, MainActivity.class);
        PendingIntent weatherPendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        this.setOnClickPendingIntent(R.id.search_input_layout, weatherPendingIntent);
    }


    private Intent getProviderIntent() {
        return new Intent(mContext, getAppWidgetProvider());
    }

}
