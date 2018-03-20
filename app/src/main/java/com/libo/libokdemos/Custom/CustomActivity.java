package com.libo.libokdemos.Custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.libo.libokdemos.Custom.CustomView.CustomTextView;
import com.libo.libokdemos.Custom.CustomView.SwitchButton;
import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * @author 李波
 * @date 2018-03-15 下午 04:17
 * @e-mail libolf@outlook.com
 * @description 自定义View的Activity
 */
public class CustomActivity extends AppCompatActivity {

    private static final String TAG = "CustomActivity";
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.switch_button)
    SwitchButton mSwitchButton;
    @BindView(R.id.custom_button)
    Button mCustomButton;
    @BindView(R.id.switch1)
    Switch mSwitch1;
    @BindView(R.id.custom_listview)
    ListView mCustomListview;
//    @BindView(R.id.chrome_refresh)
//    ChromeLikeSwipeLayout chromeRefresh;

    public List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        ButterKnife.bind(this);
        Utils.transparentNavigationAndStatusBar(this, mMainToolbar);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CustomActivity");

        mStringList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mStringList.add("item " + (i + 1));
        }

        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(CustomActivity.this, "switch" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        mSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
                Toast.makeText(CustomActivity.this, "mSwitch " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        mSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });

//        mSwitchButton.setCustomDrawSwitchListener(new SwitchButton.OnCustomDrawSwitchListener() {
//            @Override
//            public void onCustomDrawSwitch(Canvas canvas, float switchX, RectF viewRectf) {
//                Log.e(TAG, "onCustomDrawSwitch: " + switchX);
//            }
//        });

        RxView
                .clicks(mCustomButton)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        int currentType = mSwitchButton.getCurrentType();
                        switch (currentType) {
                            case SwitchButton.TYPE_CHANGE_COLOR_DIMENSION_1://√
                                currentType = SwitchButton.TYPE_CHANGE_COLOR_DIMENSION_2;
                                break;
                            case SwitchButton.TYPE_CHANGE_COLOR_DIMENSION_2://√:
                                currentType = SwitchButton.TYPE_CHANGE_COCLOR;
                                break;
                            case SwitchButton.TYPE_CHANGE_COCLOR://×
                                currentType = SwitchButton.TYPE_CHANGE_BACKGROUND_1;
                                break;
                            case SwitchButton.TYPE_CHANGE_BACKGROUND_1://√
                                currentType = SwitchButton.TYPE_CHANGE_BACKGROUND_2;
                                break;
                            case SwitchButton.TYPE_CHANGE_BACKGROUND_2://√:
                                currentType = SwitchButton.TYPE_CHANGE_BACKGROUND_UP;
                                break;
                            case SwitchButton.TYPE_CHANGE_BACKGROUND_UP://×
                                currentType = SwitchButton.TYPE_CHANGE_COLOR_DIMENSION_1;
                                break;
                        }
                        mSwitchButton.setCurrentType(currentType);
                    }
                });

        MyAdapter adapter = new MyAdapter();
        mCustomListview.setAdapter(adapter);

    }

    private void init1() {
        //        ChromeLikeSwipeLayout.makeConfig()
//                .addIcon(R.mipmap.ic_mail)
//                .addIcon(R.mipmap.ic_call)
//                .addIcon(R.mipmap.ic_friends)
//                .setMaxHeight(DisplayUtils.dp2px(this, 40))
//                .radius(DisplayUtils.dp2px(this, 35))
//                .listenItemSelected(new ChromeLikeSwipeLayout.IOnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(int i) {
//                        Toast.makeText(CustomActivity.this, "select " + i, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setTo(chromeRefresh);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return mStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(CustomActivity.this).inflate(R.layout.custom_activity_listview, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mCustomListviewItemText.setText(mStringList.get(position));
//            viewHolder.mCustomListviewItemSwitch.setCurrentState(true);
            viewHolder.mCustomListviewItemSwitch1.setChecked(true);
            viewHolder.mCustomTextView.setText(mStringList.get(position));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.custom_listview_item_text)
            TextView mCustomListviewItemText;
            @BindView(R.id.custom_listview_item_switch)
            SwitchButton mCustomListviewItemSwitch;
            @BindView(R.id.custom_listview_item_switch1)
            Switch mCustomListviewItemSwitch1;
            @BindView(R.id.custom_listview_item_text1)
            CustomTextView mCustomTextView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
