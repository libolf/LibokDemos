package com.libo.libokdemos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//slash斜线
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Map<String, Object>> mList;
    private PackageManager mPackageManager;
    private List<ResolveInfo> mResolveInfos;
    private Map<String, Intent> mIntentMap;
    private List<String> mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listview);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
        mainIntent.addCategory("leedemos");
        mPackageManager = getPackageManager();
        mResolveInfos = mPackageManager.queryIntentActivities(mainIntent, 0);
        Log.e("tag", "activity size = " + mResolveInfos.size());

        mList = new ArrayList<>();
        mIntentMap = new HashMap<>();
        mStringList = new ArrayList<>();

        String prefix = getIntent().getStringExtra("key");
        String header = "";
        String[] prefixWith;
        if (prefix == null) {
            header = "";
            prefixWith = null;
        } else {
            header = prefix + "/";
            prefixWith = prefix.split("/");
        }

        for (int i = 0; i < mResolveInfos.size(); i++) {
            Log.e("tag", "name = " + mResolveInfos.get(i).activityInfo.name);
            ResolveInfo info = mResolveInfos.get(i);
            CharSequence charSequence = info.loadLabel(mPackageManager);
            Log.e("tag", "char = " + charSequence + " " + info.activityInfo.applicationInfo.packageName);
            String label = charSequence.toString();
//            if (label.equals("Main")) {
//                continue;
//            }
            if (header.length() == 0 || label.startsWith(header)) {//第一层或者已经进入指定的第二层
                String[] split = label.split("/");
                String nextLabel = prefixWith == null ? split[0] : split[prefixWith.length];
                Log.e("tag", "prefixWith = " + header);
                if (prefixWith != null && prefixWith.length == split.length - 1) {
                    //已经是最后一层，再次点击就会进入具体的Activity
                    addListItem(nextLabel, getConcreteIntent(info.activityInfo.packageName, info.activityInfo.name));
                    Log.e("tag", "prefixWith = " + prefixWith.length);
                } else {
                    //还没到最后一层，还需要继续点击才能查看具体的Activity
                    Log.e("tag", "nextlabel = " + nextLabel);
                    if (!mStringList.contains(nextLabel)) {
                        addListItem(nextLabel, getNextIntent(header.equals("") ? nextLabel : header + nextLabel));
                        mStringList.add(nextLabel);
                    }
                }
            }
        }

        mListView.setAdapter(new SimpleAdapter(this, mList, android.R.layout.simple_list_item_1,  new String[]{"title"}, new int[]{android.R.id.text1}));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> stringObjectMap = mList.get(position);
                Intent intent = (Intent) stringObjectMap.get("intent");
                startActivity(intent);
            }
        });
    }

    public void addListItem(String title, Intent intent) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("intent", intent);
        mList.add(map);
    }

    /**
     * 没到最后一层需要继续前进
     * @param path
     * @return
     */
    public Intent getNextIntent(String path) {
        Log.e("tag", "path = " + path);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key", path);
        return intent;
    }

    /**
     * 最后一层具体的Intent
     * @param packName
     * @param componentName
     * @return
     */
    public Intent getConcreteIntent(String packName, String componentName) {
        Intent intent = new Intent();
        intent.setClassName(packName, componentName);
        return intent;
    }
}
