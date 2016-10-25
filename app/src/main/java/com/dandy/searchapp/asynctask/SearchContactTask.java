package com.dandy.searchapp.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;


import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**搜索联系人异步
 * Created by Smile on 2016/10/8.
 */

public class SearchContactTask extends BaseAsyncTask {
    private Context mContext;
    private List<String> mEvents=new ArrayList<>();

    public SearchContactTask(Context context) {
        mContext = context;
    }

    @Override
    protected Map<Integer,List<Result>> doInBackground(String... strings) {
        List<Result> results=new ArrayList<>();
        //  XLog.e("dandy","执行了搜索联系人");
        //查询结果，只要名字和号码就行了
        String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER };
        //查询条件
        String selection=""+ ContactsContract.CommonDataKinds.Phone.NUMBER+ " LIKE ? OR "+
                ContactsContract.PhoneLookup.DISPLAY_NAME+ " LIKE ? ";
        //查询参数
        String []selectionArgs=new String[]{"%"+ strings[0] +"%","%"+ strings[0] +"%"};

        // 将自己添加到 msPeers 中
        Cursor cursor = mContext.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Result result=new Result();
                String name = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));//姓名
                String num =cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//号码

                //根据姓名去查询contactID;
                Cursor cd=mContext.getContentResolver().query(
                        ContactsContract.RawContacts.CONTENT_URI,new String[]{ContactsContract.RawContacts.CONTACT_ID},
                        ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY+ "=?",new String[]{name},null);

                while (cd.moveToNext()){
                    int  contactId=cd.getInt(cd.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));//这个ID作为跳转ID
                    //  Log.e("dandy ","id  "+contactId);
                    result.setEvent(""+contactId);
                    mEvents.add(""+contactId);
                }
                cd.close();
                result.setName(name);
                results.add(result);
            }while (cursor.moveToNext());
            cursor.close();

        }


        Map<Integer,List<Result>> listMap=new HashMap<>();
        listMap.put(Config.TYPE_CONTACT,removeDuplicate(results));
        return listMap;
    }
}
 