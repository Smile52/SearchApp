package com.dandy.searchapp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.dandy.searchapp.R;
import com.dandy.searchapp.entity.SeetingIntent;
import com.dandy.searchapp.util.Config;
import com.dandy.searchapp.util.SeetingPresenter;
import com.dandy.searchapp.util.UtilSharedPreferences;

import java.util.List;

/**搜索设置适配器
 * Created by Smile on 2016/10/8.
 */

public class SeetingListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<SeetingIntent> mList;
    private SeetingPresenter mPresenter;

    public SeetingListViewAdapter(Context context, List<SeetingIntent> list) {
        mContext = context;
       // mList = list;
        mPresenter=new SeetingPresenter(mContext);
        mList=mPresenter.setSearchIntentData();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int i, View convertView, ViewGroup group) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).from(mContext).inflate(R.layout.seeting_item,group,false);
            holder.mIcon= (ImageView) convertView.findViewById(R.id.seeting_item_icon);
            holder.mName= (TextView) convertView.findViewById(R.id.seeting_item_name);
            holder.mStatus= (RadioButton) convertView.findViewById(R.id.seeting_item_status);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final SeetingIntent bean=mList.get(i);
        holder.mName.setText(bean.getName());
        holder.mIcon.setBackground(mContext.getDrawable(bean.getIcon()));
        holder.mStatus.setChecked(bean.isStatus());

        holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("smile",""+i);
                setSearchIntentStatus(i);
                mList.clear();
                mList=mPresenter.setSearchIntentData();
                Log.e("smile",""+mList.toString());
                notifyDataSetChanged();
            }
        });




        return convertView;
    }

    /**
     * 设置搜索网络选择的状态，确保唯一
     * @param key
     */
    private void setSearchIntentStatus(int key){
        for (int i=0;i<4;i++){
            if (i==key){
                UtilSharedPreferences.saveBooleanData(mContext, Config.INTENT_ARRAY[key],true);
                Log.e("smile","状态ww"+UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_ARRAY[i])+"  "+Config.INTENT_ARRAY[i]);
            }else {
                UtilSharedPreferences.saveBooleanData(mContext,Config.INTENT_ARRAY[i],false);
            }
        }

        for (int i=0;i<4;i++){
            Log.e("smile","状态"+UtilSharedPreferences.getBooleanData(mContext,Config.INTENT_ARRAY[i])+"  "+Config.INTENT_ARRAY[i]);
        }



    }


    class ViewHolder{
        ImageView mIcon;
        TextView mName;
        RadioButton mStatus;
    }


}
