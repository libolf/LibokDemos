package com.libo.libokdemos.Basis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.R;
import com.libo.libokdemos.Utils.AlertDialogUtil;
import com.libo.libokdemos.Utils.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends AppCompatActivity implements PermissionsUtil.RequestPermissionsListener {

    private static final String TAG = "ContentProviderActivity";

    @BindView(R.id.permission)
    Button mPermission;
    @BindView(R.id.content_provider)
    Button mContentProvider;
    @BindView(R.id.textview)
    TextView mTextview;
    @BindView(R.id.listview)
    ListView mListview;

    private List<String> mDatas;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        ButterKnife.bind(this);
//        PermissionsUtil.request(this, "温馨提示", "此权限必须申请将会在XXX处使用\n\n\n请在接下来允许权限的申请", 1, this, new String[]{Manifest.permission.CALL_PHONE});
        mDatas = new ArrayList<>();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mDatas);
        mListview.setAdapter(mAdapter);
        Toast toast = Toast.makeText(this, "居中ᕙ▐° ◯ °▐ᕗ", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @OnClick({R.id.permission, R.id.content_provider})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.permission:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                    Log.e(TAG, "onViewClicked: " + ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE));
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:10086"));
                    startActivity(intent);
                }
                break;
            case R.id.content_provider:
                Log.e(TAG, "onViewClicked11111: " + ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS));
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
                } else {
                    Cursor cursor = null;
                    try {
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                mDatas.add("name: " + name + "\nphone: " + number);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限申请拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限申请成功1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "权限申请拒绝1", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void showRationale() {
        Toast.makeText(this, "showRationale", Toast.LENGTH_SHORT).show();
    }
}
