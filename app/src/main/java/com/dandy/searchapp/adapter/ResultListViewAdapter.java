package com.dandy.searchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.dandy.searchapp.R;
import com.dandy.searchapp.entity.SeetingResult;
import com.dandy.searchapp.util.Config;
import com.dandy.searchapp.util.SeetingPresenter;
import com.dandy.searchapp.util.UtilSharedPreferences;

import java.util.List;

/**搜索结果适配器
 * Created by Smile on 2016/10/8.
 */

public class ResultListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<SeetingResult> mList;
    private SeetingPresenter mPresenter;

    public ResultListViewAdapter(Context context, List<SeetingResult> list) {
        mContext = context;

        mPresenter=new SeetingPresenter(mContext);
        mList=mPresenter.setSearchResultData();
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

    @Override
    public View getView(final int i, View convertView, ViewGroup group) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.result_item,group,false);
            holder.mName= (TextView) convertView.findViewById(R.id.result_item_name);
            holder.mBox= (CheckBox) convertView.findViewById(R.id.result_item_status);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final SeetingResult bean=mList.get(i);
        final String title=bean.getName();
        holder.mName.setText(title);
        holder.mBox.setChecked(bean.isStatus());
        //先保存默认状态
        UtilSharedPreferences.saveBooleanData(mContext, Config.KEY_ARRAY[i],bean.isStatus());

        holder.mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean.isStatus()){
                    UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_ARRAY[i],false);
                }else {
                    UtilSharedPreferences.saveBooleanData(mContext,Config.KEY_ARRAY[i],true);
                }
                mList=mPresenter.setSearchResultData();
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

     class ViewHolder{
         private TextView mName;
         private CheckBox mBox;
    }
}
