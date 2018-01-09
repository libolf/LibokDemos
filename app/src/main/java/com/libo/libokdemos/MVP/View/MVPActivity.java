package com.libo.libokdemos.MVP.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.libo.libokdemos.MVP.Presenter.MyContact;
import com.libo.libokdemos.MVP.Presenter.ViewPresenter;
import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MVPActivity extends AppCompatActivity implements MyContact.ImplView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ViewPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);

        mPresenter = new ViewPresenter(this);
        mPresenter.attachView(this);
    }


    @Override
    public void setAdapter(StringRecyclerAdapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void notifyAdapter() {

    }
}
