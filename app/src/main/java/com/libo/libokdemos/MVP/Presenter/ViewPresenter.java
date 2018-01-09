package com.libo.libokdemos.MVP.Presenter;

import android.content.Context;

import com.libo.libokdemos.MVP.View.StringRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libok on 2018-01-09.
 */

public class ViewPresenter implements MyContact.ImplPresenter {

    private Context mContext;
    private StringRecyclerAdapter mRecyclerAdapter;

    public ViewPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(MyContact.ImplView view) {
        mRecyclerAdapter = new StringRecyclerAdapter(mContext, getData());
        view.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void detachView() {

    }

    @Override
    public List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("item" + (i+1));
        }

        return data;
    }
}
