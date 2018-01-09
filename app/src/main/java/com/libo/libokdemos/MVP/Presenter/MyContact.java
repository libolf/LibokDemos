package com.libo.libokdemos.MVP.Presenter;

import com.libo.libokdemos.MVP.View.StringRecyclerAdapter;

import java.util.List;

/**
 * Created by libok on 2018-01-09.
 */

public interface MyContact {

    interface ImplView {
        void setAdapter(StringRecyclerAdapter adapter);

        void notifyAdapter();
    }

    interface ImplModel {
        List<String> getDatas();
    }

    interface ImplPresenter {

        void attachView(ImplView view);

        void detachView();

        List<String> getData();
    }

}
