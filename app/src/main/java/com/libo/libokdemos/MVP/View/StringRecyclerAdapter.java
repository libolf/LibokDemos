package com.libo.libokdemos.MVP.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libo.libokdemos.R;

import java.util.List;

/**
 * Created by libok on 2018-01-09.
 */

public class StringRecyclerAdapter extends Adapter<StringRecyclerAdapter.StringViewHolder> {

    private Context mContext;
    private List<String> mStringList;

    public StringRecyclerAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mvp_recycler_item, parent, false);
        StringViewHolder viewHolder = new StringViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.textView.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public class StringViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public StringViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mvp_recycler_item_text);
        }
    }
}


