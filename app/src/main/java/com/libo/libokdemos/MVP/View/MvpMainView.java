package com.libo.libokdemos.MVP.View;

import com.libo.libokdemos.MVP.Model.PhoneInfo;

/**
 * Created by libok on 2018-01-10.
 */

public interface MvpMainView {
    void showToast(String msg);

    void updateView(PhoneInfo phoneInfo);

    void showLoading();

    void hidenLoading();
}
