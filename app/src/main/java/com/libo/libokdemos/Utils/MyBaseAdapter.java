package com.libo.libokdemos.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.libo.libokdemos.Bean.Person;
import com.libo.libokdemos.R;

import java.util.List;

/**
 * Created by libok on 2018-01-29.
 */

public class MyBaseAdapter extends BaseAdapter {

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_LOADING = 1;
    private static final int TYPE_COUNT = 2;

    private List<Person> mDatas;
    private Context mContext;

    public MyBaseAdapter(Context context, List<Person> datas) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getId() == -1) {
            return TYPE_LOADING;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (getItemViewType(position) == TYPE_CONTENT) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sqlite_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.idText = convertView.findViewById(R.id.sqlite_list_item_id);
                viewHolder.nameText = convertView.findViewById(R.id.sqlite_list_item_name);
                viewHolder.ageText = convertView.findViewById(R.id.sqlite_list_item_age);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.idText.setText(mDatas.get(position).getId() + "");
            viewHolder.nameText.setText(mDatas.get(position).getName());
            viewHolder.ageText.setText(mDatas.get(position).getAge() + "");
        } else if (getItemViewType(position) == TYPE_LOADING) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sqlite_list_loading_item, null);
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView idText;
        TextView nameText;
        TextView ageText;
    }
}
