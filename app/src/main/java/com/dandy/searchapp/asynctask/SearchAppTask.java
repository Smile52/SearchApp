package com.dandy.searchapp.asynctask;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;


import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**搜索APP异步
 * Created by Smile on 2016/10/8.
 */

public class SearchAppTask extends BaseAsyncTask {
    private Context mContext;

    public SearchAppTask(Context context) {
        mContext = context;
    }

    /**
     * 异步操作
     * @param strings
     * @return
     */
    @Override
    protected Map<Integer,List<Result>> doInBackground(String... strings) {
        List<Result> results=new ArrayList<>();

        //判断是不是小写，是就小写转换成大写
        String reg = "[a-z]";
        if (strings[0].matches(reg)){
            strings[0]=strings[0].toUpperCase();
        }

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null)
                .addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager packageManager = mContext.getPackageManager();
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info:apps){
            String appName=info.loadLabel(packageManager).toString();
            if (appName.contains(strings[0])){
                Result result=new Result();
                result.setName(appName);
                result.setEvent(info.activityInfo.packageName);
                result.setIcon(info.loadIcon(packageManager));
                results.add(result);
            }

        }

        Map<Integer,List<Result>> listMap=new HashMap<>();
        listMap.put(Config.TYPE_APP,results);
        return listMap;
    }


}
