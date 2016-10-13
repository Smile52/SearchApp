package com.dandy.searchapp.asynctask;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**搜索音乐异步，根据歌名或者专辑名进行模糊查询
 * Created by Smile on 2016/10/8.
 */

public class SearchMusicTask extends BaseAsyncTask {
    private Context mContext;
    public SearchMusicTask(Context context) {
        mContext = context;
    }

    @Override
    protected Map<Integer,List<Result>> doInBackground(String... strings) {
        List<Result> results=new ArrayList<>();
        ContentResolver cr = mContext.getContentResolver();

        String[] projection=new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ARTIST};//查询结果
        String selection=""+ MediaStore.Audio.Media.TITLE+ " LIKE ? OR "+
                MediaStore.Audio.Media.ALBUM+ " LIKE ?";
        String[] args=new String[]{"%"+ strings[0] +"%","%"+ strings[0] +"%"};

        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, args, null);
        while (cursor.moveToNext()){
            String musicName= cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));//歌名
            String path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//路径
            String singerName=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            // String alubm=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));


            Result result=new Result();
            result.setName(musicName);
            result.setEvent(path);
            result.setDetail(singerName);
            results.add(result);
        }
        cursor.close();

        Map<Integer,List<Result>> listMap=new HashMap<>();
        listMap.put(Config.TYPE_MUSIC,results);
        return listMap;
    }
}
