package com.dandy.searchapp.asynctask;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**搜索日程异步
 * Created by Smile on 2016/10/8.
 */

public class SearchScheduleTask extends BaseAsyncTask {
    private Context mContext;
    private static final String[] EVENT_CONTENT = new String[]{
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_LOCATION,
            CalendarContract.Events._ID
    };

    public SearchScheduleTask(Context context) {
        mContext = context;
    }

    @Override
    protected Map<Integer, List<Result>> doInBackground(String... strings) {
        List<Result> results = new ArrayList<>();
        String title;//标题
        String detail;
        String time;
        String end;
        String location;
        int event_id;//事件ID

        ContentResolver cr = mContext.getContentResolver();
        Cursor cursor = null;
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "" + CalendarContract.Events.TITLE + "  LIKE ? OR "
                + CalendarContract.Events.DESCRIPTION + "  LIKE ? OR  "
                + CalendarContract.Events.EVENT_LOCATION + "   LIKE ? ";

        String[] selectionArgs = new String[]{"%" + strings[0] + "%", "%" + strings[0] + "%", "%" + strings[0] + "%"};

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        cursor = cr.query(uri, EVENT_CONTENT, selection, selectionArgs, null);
        // cursor=cr.query(uri,EVENT_CONTENT,CalendarContract.Events.TITLE+" like ?",new String[]{"%"+ s +"%"},null);
        while (cursor.moveToNext()){
            Result result=new Result();

            title=getString(cursor, CalendarContract.Events.TITLE);
            detail=getString(cursor, CalendarContract.Events.DESCRIPTION);
            time=getString(cursor, CalendarContract.Events.DTSTART);
            location=getString(cursor, CalendarContract.Events.EVENT_LOCATION);
            event_id=getInt(cursor, CalendarContract.Events._ID);
            Log.e("dandy","title "+title+"  detail "+detail+"  location"+location+ "id"+event_id);

            result.setName(title);
            result.setEvent(""+event_id);
            result.setDetail(detail);
            results.add(result);
            //mResult.setText("title "+title+"  detail "+detail+"  location"+location);

        }
        cursor.close();

        Map<Integer,List<Result>> listMap=new HashMap<>();
        listMap.put(Config.TYPE_SCHEDULE,results);
        return listMap;
    }
}
