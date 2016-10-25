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
     * @param strings 传递的参数
     * @return
     */
    @Override
    protected Map<Integer,List<Result>> doInBackground(String... strings) {
        List<Result> results=new ArrayList<>();
        strings[0]=toLowerCase(strings[0]);

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null)
                .addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager packageManager = mContext.getPackageManager();
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info:apps){
            String appName=info.loadLabel(packageManager).toString();
            String tempName=toLowerCase(appName);
            if (tempName.contains(strings[0])){
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

    /**
     * 转换成小写
     * @param s
     * @return
     */
    private String toLowerCase(String s){
        //XLog.e("smile","转化成小写后 "+);
        return s.toLowerCase();
    }

    /**
     * 判断首字符是否为字母，是则转换成大写
     * @param s
     * @return
     */
    private String check(String s){
        String result = s;
        char fir=s.charAt(0);
        if (Character.isLetter(fir)){
            result =toUpperCaseFirstOne(result);
        }
        //XLog.e("smile"," ww  "+result);
        return result;
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))) {
            return s;
        }else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


}
