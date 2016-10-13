package com.dandy.searchapp.asynctask;

import android.database.Cursor;
import android.os.AsyncTask;


import com.dandy.searchapp.MainActivity;
import com.dandy.searchapp.entity.Result;

import java.util.List;
import java.util.Map;

/**异步基类
 * Created by Smile on 2016/10/8.
 */

public abstract class BaseAsyncTask extends AsyncTask<String,Void,Map<Integer,List<Result>>> {


    @Override
    protected void onPostExecute(Map<Integer,List<Result>> results) {

        MainActivity.mSearchResultImp.searchSuccess(results);
        super.onPostExecute(results);

    }

    public String getString(Cursor cursor, String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    public int getInt(Cursor cursor, String key) {
        return cursor.getInt(cursor.getColumnIndex(key));
    }
}
