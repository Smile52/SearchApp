package com.dandy.searchapp.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.dandy.searchapp.R;
import com.dandy.searchapp.entity.Result;

import java.util.List;
import java.util.Map;

/**ExpandListView  最后搜索出来的结果适配器
 * Created by Dandy on 2016/10/10.
 */

public class SearchResultAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private Map<Integer,List<Result>> mResultMap;
    private List<String> mGroup;
    private final int TYPE_NORMAL=0;//普通的类型
    private final  int TYPE_CONTACT=1;//联系人类型

    private static final int DEFAULT_MAX_NUM=3;//默认最多显示多少条数据
    private int[] mChildCounts={DEFAULT_MAX_NUM,DEFAULT_MAX_NUM,DEFAULT_MAX_NUM,DEFAULT_MAX_NUM,DEFAULT_MAX_NUM};//子view显示的数量

    public SearchResultAdapter(Context mContext, Map<Integer, List<Result>> mResultMap, List<String> mTitle) {
        this.mContext = mContext;
        this.mResultMap = mResultMap;
        this.mGroup = mTitle;
    }

    @Override
    public int getGroupCount() {

        return mGroup.size();
    }

    public void addData(String title, int key, List<Result> results){
        mGroup.add(title);
        mResultMap.put(key,results);
        notifyDataSetChanged();
    }

    @Override
    public int getChildrenCount(int i) {
        if (mResultMap.get(i)==null||mResultMap.get(i).size()==0){
            return 0;
        }
        Log.e("smile  ","getChildrenCount  i="+i);
        if (mResultMap.get(i).size()>DEFAULT_MAX_NUM){
            return mChildCounts[i];
        }else
        return mResultMap.get(i).size() ;
    }

    @Override
    public Object getGroup(int i) {
        if (mResultMap.get(i).size()==0){
            return null;
        }
        return mGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mResultMap.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        if (mResultMap.get(i).size()==0){
            return 0;
        }
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public synchronized View getGroupView(final int i, boolean b, View convertView, ViewGroup viewGroup) {
       GroupHolder holder=null;

        if (convertView ==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_group_result,viewGroup,false);
            holder=new GroupHolder();
            holder.mTitleName= (TextView) convertView.findViewById(R.id.group_title_tv);
            holder.mMore= (ImageView) convertView.findViewById(R.id.group_more_img);
            convertView.setTag(holder);
        }else {
            holder= (GroupHolder) convertView.getTag();
        }
        holder.mTitleName.setText(mGroup.get(i));
        final ImageView more=holder.mMore;
        if (mResultMap.get(i).size()<=DEFAULT_MAX_NUM){
            more.setVisibility(View.GONE);
        }
        Log.e("smile","父标题 "+mGroup.get(i));

            more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getChildrenCount(i)==DEFAULT_MAX_NUM){
                    addItemNum(i,mResultMap.get(i).size());
                    more.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_instructions_press));

                    notifyDataSetChanged();
                }else {
                    addItemNum(i,DEFAULT_MAX_NUM);
                    more.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_instructions_normal));
                    notifyDataSetChanged();
                }
            }
        });

        convertView.setClickable(true);
        return convertView;
    }

    /**
     * 会加载两种布局
     * @param i
     * @param i1
     * @param b
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public synchronized View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        ChildHolder holder=null;//普通viewholder
        ContactHolder contactHolder=null;//联系人viewholder
        int type=getChildType(i,i1);
        if (convertView == null){
            switch (type){
                case TYPE_NORMAL:
                    holder=new ChildHolder();
                    convertView= LayoutInflater.from(mContext).inflate(R.layout.item_child_result,viewGroup,false);
                    holder.mTitleName= (TextView) convertView.findViewById(R.id.result_name);

                    holder.mDetail= (TextView) convertView.findViewById(R.id.result_detail);
                    //holder.mContactName= (TextView) convertView.findViewById(R.id.result_contact_name);
                    holder.mIcon= (ImageView) convertView.findViewById(R.id.result_img);
                    convertView.setTag(holder);
                    break;
                case TYPE_CONTACT:
                    contactHolder=new ContactHolder();
                    convertView= LayoutInflater.from(mContext).inflate(R.layout.item_contact,viewGroup,false);
                    contactHolder.mContactName= (TextView) convertView.findViewById(R.id.item_contact_name);
                    convertView.setTag(contactHolder);
                    break;
            }
        }else {
            switch (type){
                case TYPE_NORMAL:
                    holder= (ChildHolder) convertView.getTag();
                    break;
                case TYPE_CONTACT:
                    contactHolder= (ContactHolder) convertView.getTag();
                    break;
            }
        }
        //设置值
        switch (type){
            //普通类型
            case TYPE_NORMAL:
                if (mGroup.get(i).equals(mContext.getResources().getString(R.string.result_app))){

                    holder.mIcon.setImageDrawable(mResultMap.get(i).get(i1).getIcon());
                    holder.mTitleName.setText(mResultMap.get(i).get(i1).getName());
                    holder.mDetail.setText("应用程序");
                    Log.e("smile","应用");
                    break;
                    //联系人
                }else if (mGroup.get(i).equals(mContext.getResources().getString(R.string.result_music))){

                    holder.mTitleName.setText(mResultMap.get(i).get(i1).getName());
                    holder.mIcon.setImageResource(R.drawable.list_icon_music);
                    holder.mDetail.setText(mResultMap.get(i).get(i1).getDetail());
                    Log.e("smile","音乐");
                    break;
                    //短信
                }else if (mGroup.get(i).equals(mContext.getResources().getString(R.string.result_msn))){
                    String titleName=mResultMap.get(i).get(i1).getName();
                    if (titleName ==null||titleName.isEmpty()){
                        holder.mTitleName.setText(mResultMap.get(i).get(i1).getEvent());
                    }else {
                        holder.mTitleName.setText(titleName);
                    }
                    //   XLog.e("smile","短信内容"+mResultMap.get(i).get(i1).getDetail());

                    //   XLog.e("smile","短信联系人名字 "+mResultMap.get(i).get(i1).getDetail()+"  号码"+mResultMap.get(i).get(i1).getEvent());
                    holder.mDetail.setText(mResultMap.get(i).get(i1).getDetail());
                    holder.mIcon.setImageResource(R.drawable.list_icon_message);
                    //  XLog.e("smile","短信");
                    break;

                }else if (mGroup.get(i).equals(mContext.getResources().getString(R.string.result_calendar))){

                    holder.mTitleName.setText(mResultMap.get(i).get(i1).getName());
                    holder.mDetail.setText(mResultMap.get(i).get(i1).getDetail());
                    holder.mIcon.setImageResource(R.drawable.list_icon_schedule);
                    break;
                }
            //联系人
            case TYPE_CONTACT:
                contactHolder.mContactName.setText(mResultMap.get(i).get(i1).getName());
                break;

        }

     return convertView;
    }

    @Override
    public int getChildTypeCount() {
        return 2;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (mGroup.get(groupPosition).equals(mContext.getResources().getString(R.string.result_contact))){
            return TYPE_CONTACT;
        }else
        return TYPE_NORMAL;


    }

    public void clearData(){
        mResultMap.clear();
        mGroup.clear();
    }

    /**
     * 添加显示数量
     * @param i
     * @param number
     */
    public synchronized void addItemNum(int i,int number) {
        mChildCounts[i] = number;
    }

    ExpandableListView.OnChildClickListener listener=new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
            Log.e("dandy","点击了 "+mResultMap.get(i).get(i1).getName()+"  "+mGroup.get(i));
            String groupName=mGroup.get(i);
            if (groupName.equals(mContext.getResources().getString(R.string.result_app))){
                openApp(mResultMap.get(i).get(i1).getEvent());
            }else if (groupName.equals(mContext.getResources().getString(R.string.result_contact))){
                openContact(mResultMap.get(i).get(i1).getEvent());

            }else if (groupName.equals(mContext.getResources().getString(R.string.result_msn))){
                openMsn(mResultMap.get(i).get(i1).getEvent());

            }else if (groupName.equals(mContext.getResources().getString(R.string.result_music))){
                openMusic(mResultMap.get(i).get(i1).getEvent());

            }else if (groupName.equals(mContext.getResources().getString(R.string.result_calendar))){
                openSchedule(mResultMap.get(i).get(i1).getEvent());

            }


            return false;
        }
    };

    /**
     * 获取点击事件
     * @return
     */
    public ExpandableListView.OnChildClickListener getListener(){
        return listener;
    }



    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    class GroupHolder{
        TextView mTitleName;
        ImageView mMore;
    }

    class ChildHolder{
        TextView mTitleName;
        TextView mDetail;

        ImageView mIcon;


    }

    class ContactHolder{
        TextView mContactName;
    }

    /**
     * 打开app
     * @param event
     */
    private void  openApp(String event){
        Intent intent;
        intent=mContext.getPackageManager().getLaunchIntentForPackage(event);
       // XLog.e("dandy","context "+mContext);
        if (intent==null||mContext==null){
            return;
        }
        mContext.startActivity(intent);
    }

    private void openContact(String event){
        Uri personUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Integer.valueOf(event));//最后的ID参数为联系人Provider中的数据库BaseID，即哪一行
        Intent intent = new Intent(); intent.setAction(Intent.ACTION_VIEW);
        intent.setData(personUri);
        mContext.startActivity(intent);

    }

    private void openMsn(String event){
        if (event==null){
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SENDTO");
        intent.setData(Uri.parse("sms:" + Long.valueOf(event)));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void openMusic(String event){
        Log.e("smile",""+event);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+event), "audio/mp3");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void openSchedule(String event){
        Uri uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Integer.valueOf(event));
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
