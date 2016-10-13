package com.dandy.searchapp.util;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;


import com.dandy.searchapp.R;
import com.dandy.searchapp.adapter.ResultListViewAdapter;
import com.dandy.searchapp.adapter.SeetingListViewAdapter;
import com.dandy.searchapp.entity.SeetingIntent;
import com.dandy.searchapp.entity.SeetingResult;

import java.util.ArrayList;
import java.util.List;

/**搜索设置控制器
 * Created by Smile on 2016/9/24.
 */

public class SeetingPresenter {

    private Context mContext;
    private List<SeetingIntent> mSeetingBeanList;//设置集合
    private List<SeetingResult> mResultBeanList;//结果集合
    private SeetingListViewAdapter mSeetingListViewAdapter;
    private ResultListViewAdapter mListViewAdapter;
    private ListView mSeetingLiv,mResultLiv;
    public SeetingPresenter(Context context) {
        mContext = context;
    }

    /**
     * 显示
     * @param seeting_liv
     * @param reult_liv
     */
    public void showViews(ListView seeting_liv, ListView reult_liv){
        setSearchIntentData();
        setSearchResultData();

        this.mSeetingLiv=seeting_liv;
        this.mResultLiv=reult_liv;

        mSeetingListViewAdapter=new SeetingListViewAdapter(mContext,mSeetingBeanList);
        mSeetingLiv.setAdapter(mSeetingListViewAdapter);

        mListViewAdapter=new ResultListViewAdapter(mContext,mResultBeanList);
        mResultLiv.setAdapter(mListViewAdapter);

    }


    /**
     * 设置搜索结果的数据
     */
    public List<SeetingResult> setSearchResultData(){

        mResultBeanList=new ArrayList<>();

        setSearchResultStatus();

        SeetingResult app=new SeetingResult();
        app.setName(mContext.getResources().getString(R.string.result_app));
        app.setStatus(UtilSharedPreferences.getBooleanData(mContext, Config.KEY_APP));

        SeetingResult contact=new SeetingResult();
        contact.setName(mContext.getResources().getString(R.string.result_contact));
        contact.setStatus(UtilSharedPreferences.getBooleanData(mContext, Config.KEY_CONTACTS));

        SeetingResult msn=new SeetingResult();
        msn.setName(mContext.getString(R.string.result_msn));
        msn.setStatus(UtilSharedPreferences.getBooleanData(mContext, Config.KEY_MSN));

        SeetingResult music=new SeetingResult();
        music.setName(mContext.getResources().getString(R.string.result_music));
        music.setStatus(UtilSharedPreferences.getBooleanData(mContext, Config.KEY_MUSIC));

        SeetingResult calendar =new SeetingResult();
        calendar.setName(mContext.getResources().getString(R.string.result_calendar));
        calendar.setStatus(UtilSharedPreferences.getBooleanData(mContext, Config.KEY_SCHEDULE));



        mResultBeanList.add(app);
        mResultBeanList.add(contact);
        mResultBeanList.add(msn);
        mResultBeanList.add(music);
        mResultBeanList.add(calendar);

        return mResultBeanList;

    }

    /**
     * 设置搜索网络（搜索引擎）的数据
     */
    public List<SeetingIntent> setSearchIntentData(){
        mSeetingBeanList=new ArrayList<>();
        SeetingIntent google=new SeetingIntent();
        google.setIcon(R.drawable.search_icon_google);
        google.setName(mContext.getResources().getString(R.string.seeting_google));
        google.setStatus(UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_GOOGLE));
        Log.e("smile","eeee  "+UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_GOOGLE));
        mSeetingBeanList.add(google);
        SeetingIntent yooho=new SeetingIntent();
        yooho.setIcon(R.drawable.search_icon_yahoo);
        yooho.setName(mContext.getResources().getString(R.string.seeting_yooho));
        yooho.setStatus(UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_YOOHO));
        mSeetingBeanList.add(yooho);
        SeetingIntent ask=new SeetingIntent();
        ask.setIcon(R.drawable.search_icon_ask);
        ask.setName(mContext.getString(R.string.seeting_ask));
        ask.setStatus(UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_ASK));
        SeetingIntent aol=new SeetingIntent();

        aol.setIcon(R.drawable.search_icon_aol);
        aol.setName(mContext.getString(R.string.seeting_aol));
        aol.setStatus(UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_AOL));
        mSeetingBeanList.add(aol);
        mSeetingBeanList.add(ask);
        return mSeetingBeanList;
    }

    /**
     * 设置默认搜索网络的引擎 Google;
     */
    public void setSearchIntentStatus(){

    }


    /**
     * 设置搜索结果状态。如果第一次，全部设置为true
     */
    public void setSearchResultStatus(){

        if (!UtilSharedPreferences.getBooleanData(mContext,Config.KEY_ISFIRST)){
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_APP,true);
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_CONTACTS,true);
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_MSN,true);
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_SCHEDULE,true);
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_MUSIC,true);

            UtilSharedPreferences.saveBooleanData(mContext,Config.INTENT_GOOGLE,true);
            UtilSharedPreferences.saveBooleanData(mContext,Config.INTENT_AOL,false);
            UtilSharedPreferences.saveBooleanData(mContext,Config.INTENT_ASK,false);
            UtilSharedPreferences.saveBooleanData(mContext,Config.INTENT_YOOHO,false);

            //设置成功后将这个设置为true，下次不会重新设置了
            UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_ISFIRST,true);


        }

    }

}
