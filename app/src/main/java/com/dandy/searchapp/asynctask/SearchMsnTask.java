package com.dandy.searchapp.asynctask;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;


import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**搜索信息异步
 * Created by Smile on 2016/10/8.
 */

public class SearchMsnTask extends BaseAsyncTask {
    private Context mContext;
    private Uri SMS_INBOX = Uri.parse("content://sms/");
    private static final String COLUMN_ADDRESS="address";//号码字段
    private static final String COLUMN_BODY="body";//内容字段
    private static final String COLUMN_NAME="person";//联系人名字

    public SearchMsnTask(Context context) {
        mContext = context;
    }

    @Override
    protected Map<Integer,List<Result>> doInBackground(String... strings) {
        List<Result> results=new ArrayList<>();
        ContentResolver cr =mContext. getContentResolver();
        String[] projection=new String[]{COLUMN_ADDRESS,COLUMN_BODY,COLUMN_NAME};
        String selection=COLUMN_ADDRESS+" LIKE ? OR "+COLUMN_BODY+" LIKE ? OR "+COLUMN_NAME+" LIKE ? ";
        String[]args=new String[]{"%"+ strings[0] +"%","%"+ strings[0] +"%","%"+ strings[0] +"%"};
        Cursor cursor=cr.query(SMS_INBOX,projection,selection,args,null);
        while (cursor.moveToNext()){
            String number = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));//手机号
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));//联系人姓名列表
            String body = cursor.getString(cursor.getColumnIndex(COLUMN_BODY));
            Log.e("smile "," num "+number+" body "+body+" name"+name);

            if (name!=null){
                Cursor cd=mContext.getContentResolver().query(
                        ContactsContract.RawContacts.CONTENT_URI,new String[]{ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                        ContactsContract.RawContacts._ID+ "=?",new String[]{name},null);

                while (cd.moveToNext()){
                    String displayName=cd.getString(cd.getColumnIndex(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY));
                   //  Log.e("smile ","id  "+displayName);
                    name=displayName;

                }
            }

            Result result=new Result();
            result.setName(body);
            result.setEvent(number);
            result.setDetail(name);
            results.add(result);
        }
        cursor.close();
        Map<Integer,List<Result>> listMap=new HashMap<>();
        listMap.put(Config.TYPE_MSN,results);
        return listMap;
    }
}
