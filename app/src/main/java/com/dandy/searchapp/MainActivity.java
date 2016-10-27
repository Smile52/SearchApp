package com.dandy.searchapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dandy.searchapp.adapter.SearchResultAdapter;
import com.dandy.searchapp.asynctask.SearchAppTask;
import com.dandy.searchapp.asynctask.SearchContactTask;
import com.dandy.searchapp.asynctask.SearchMsnTask;
import com.dandy.searchapp.asynctask.SearchMusicTask;
import com.dandy.searchapp.asynctask.SearchScheduleTask;
import com.dandy.searchapp.entity.Result;
import com.dandy.searchapp.imp.SearchResultImp;
import com.dandy.searchapp.util.BackGroundUtil;
import com.dandy.searchapp.util.Config;
import com.dandy.searchapp.util.FastBlur;
import com.dandy.searchapp.util.SeetingPresenter;
import com.dandy.searchapp.util.UtilSharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements SearchResultImp,View.OnClickListener{
    private ImageView mIcon;//搜索引擎
    private Context mContext;
    private Bitmap wallpaperbitmap;
    private LinearLayout mHintLayout,mBackLayout;
    private EditText mInputContent;
    private ImageView mDeleteImg;
    private ExpandableListView mResultListView;
    private   boolean isAPP,isContacts,isMsn,isMusic,isSchedule;
    public static SearchResultImp mSearchResultImp;
    private String mContent;
    private SearchResultAdapter mResultAdapter;
    private SeetingPresenter mPresenter;
    private int mKey=0;
    private List<Integer> mKeys=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
        mContext=getApplicationContext();
        mPresenter=new SeetingPresenter(mContext);
        mIcon= (ImageView) findViewById(R.id.search_seeting_icon_img);
        mHintLayout= (LinearLayout) findViewById(R.id.hint_layout);
        mInputContent= (EditText) findViewById(R.id.input_search_et);
        mDeleteImg= (ImageView) findViewById(R.id.delete_icon_img);
        mHintLayout= (LinearLayout) findViewById(R.id.hint_layout);
        mResultListView= (ExpandableListView) findViewById(R.id.searcg_result_liv);
        mResultListView.setGroupIndicator(null);
        mBackLayout= (LinearLayout) findViewById(R.id.activity_main);
        mIcon.setOnClickListener(this);
        wallpaperbitmap = BackGroundUtil.getWallpaperbitmap(getApplicationContext());
        Drawable drawable=new BitmapDrawable(FastBlur.blur(mContext,8,wallpaperbitmap));
        mBackLayout.setBackground(drawable);
        mInputContent.addTextChangedListener(mTextWatcher);
        mSearchResultImp=this;
        mDeleteImg.setOnClickListener(this);
        mPresenter.setSearchResultStatus();
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            if (temp.length()>=1){
                mResultListView.setVisibility(VISIBLE);
                cleanData();

                // Log.e("dandy","输入的字符串"+temp);
                getSearchConfig( temp.toString());
                mDeleteImg.setVisibility(VISIBLE);
                mHintLayout.setVisibility(INVISIBLE);

                mContent=temp.toString();
                mKeys.clear();


            }else {
                //  XLog.e("smile","清除了数据");
                cleanData();
                mHintLayout.setVisibility(VISIBLE);
                mResultListView.setVisibility(INVISIBLE);
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示

        }
    };

    /**
     * 获取搜索配置信息
     */
    private void getSearchConfig(String content){
        isAPP= UtilSharedPreferences.getBooleanData(mContext, Config.KEY_ARRAY[0]);
        isContacts=UtilSharedPreferences.getBooleanData(mContext,Config.KEY_ARRAY[1]);
        isMsn=UtilSharedPreferences.getBooleanData(mContext,Config.KEY_ARRAY[2]);
        isMusic=UtilSharedPreferences.getBooleanData(mContext,Config.KEY_ARRAY[3]);
        isSchedule=UtilSharedPreferences.getBooleanData(mContext,Config.KEY_ARRAY[4]);
        if (true){
            SearchAppTask searchAppTask=new SearchAppTask(mContext);
            //searchAppTask.execute(content);
            searchAppTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
        }else {
            searchOtherTask(content);
        }

    }

    private void searchOtherTask(String content){
        if (true){
            SearchContactTask searchContactTask=new SearchContactTask(mContext);
            searchContactTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
        }
        if (true){
            SearchMsnTask searchMsnTask=new SearchMsnTask(mContext);
            searchMsnTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
        }
        if (true){
            SearchMusicTask searchMusicTask=new SearchMusicTask(mContext);
            searchMusicTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
        }
        if (true){
            SearchScheduleTask searchScheduleTask=new SearchScheduleTask(mContext);
            searchScheduleTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
        }

    }

    @Override
    public void searchSuccess(Map<Integer, List<Result>> resultMap) {

        List<Result> resultList;
        for (int key : resultMap.keySet()){
            resultList=resultMap.get(key);
            Log.e("smile","结果 "+key+" list"+resultList.toString());
            if (key==0&&resultList.size()>0){
                //XLog.e("dandy","长度"+resultList.size());
                List<String> strings=new ArrayList<>();
                strings.add(mContext.getResources().getString(R.string.result_app));
                ArrayMap<Integer,List<Result>> map=new ArrayMap<>();
                map.put(mKey,resultList);
                mKey++;
                mResultAdapter=new SearchResultAdapter(mContext,map,strings);
                mResultListView.setAdapter(mResultAdapter);

                for(int i=0;i<mResultAdapter.getGroupCount();i++){
                    mResultListView.expandGroup(i);

                }
                mResultListView.setDividerHeight(0);
                mResultListView.setOnChildClickListener(mResultAdapter.getListener());

                searchOtherTask(mContent);
                break;

            }else if (key==0&&resultList.size()==0){
                searchOtherTask(mContent);
                break;
            }

            setResultData(key,resultList);
        }
        //mResultAdapter.getData();
    }

    /**
     * 设置搜索结果数据
     * @param key
     * @param resultList
     */
    private synchronized   void setResultData(int key, final List<Result> resultList){
        String title = "";//标题

        //   XLog.e("smile","返回结果 "+resultList.toString());
        for (int i=0;i<mKeys.size();i++){
            if (key==mKeys.get(i)){
                //     XLog.e("smile","有相同的数据了");
                return;
            }
        }
        mKeys.add(key);

        switch (key){
            case Config.TYPE_CONTACT:
                title=mContext.getResources().getString(R.string.result_contact);
                break;
            case Config.TYPE_MSN:
                title=mContext.getResources().getString(R.string.result_msn);
                break;
            case Config.TYPE_MUSIC:
                title=mContext.getResources().getString(R.string.result_music);
                break;
            case Config.TYPE_SCHEDULE:
                title=mContext.getResources().getString(R.string.result_calendar);
                break;
        }

        if (resultList.size()<=0){
            return;
        }

        if (mResultAdapter==null){
            ArrayMap<Integer, List<Result>> map=new ArrayMap<>();

            map.put(mKey,resultList);
            mKey++;
            List<String> strings=new ArrayList<>();
            strings.add(title);
            mResultAdapter=new SearchResultAdapter(mContext,map,strings);
            mResultListView.setAdapter(mResultAdapter);

            for(int i=0;i<mResultAdapter.getGroupCount();i++){
                mResultListView.expandGroup(i);

            }
          //  XLog.e("smile","设置了新adapter");
        }else {
            Map<Integer, List<Result>> map=new HashMap<>();
            map.put(mKey,resultList);
            mResultAdapter.addData(title,mKey,resultList);
            for(int i=0;i<mResultAdapter.getGroupCount();i++){
                mResultListView.expandGroup(i);

            }
            mKey++;
          //  XLog.e("smile","设置了adapter");
        }
        // invalidate();
        mResultListView.setDividerHeight(0);
        mResultListView.setOnChildClickListener(mResultAdapter.getListener());
    }

    /**
     * 清除数据
     */
    private void cleanData(){
        if (mResultAdapter!=null){
            mResultAdapter.clearData();
            mResultAdapter.notifyDataSetChanged();
            mResultAdapter= null;
        }

        mKey=0;
    }
    @Override
    public void onClick(View v) {

    }
}
