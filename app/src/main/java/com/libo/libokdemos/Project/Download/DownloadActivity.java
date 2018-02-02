package com.libo.libokdemos.Project.Download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.libo.libokdemos.Bean.FileInfo;
import com.libo.libokdemos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends AppCompatActivity {

    private static final String QQ_TIM_URL = "https://dldir1.qq.com/qqfile/qq/TIM2.1.0/22747/TIM2.1.0.exe";
    private static final String QQ_TIM_URL1 = "http://sqdd.myapp.com/myapp/qqteam/tim/down/tim.apk";

    @BindView(R.id.download_file_name)
    TextView mDownloadFileName;
    @BindView(R.id.download_file_progress)
    ProgressBar mDownloadFileProgress;
    @BindView(R.id.download_file_start)
    Button mDownloadFileStart;
    @BindView(R.id.download_file_end)
    Button mDownloadFileEnd;

    private FileInfo mFileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        mFileInfo = new FileInfo(0, QQ_TIM_URL1, "tim.apk", 0, 0);
    }

    @OnClick({R.id.download_file_start, R.id.download_file_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download_file_start:
                Toast.makeText(this, "开始", Toast.LENGTH_SHORT).show();
                startService(new Intent(this, DownloadService.class).setAction(DownloadService.ACTION_START).putExtra("fileInfo", mFileInfo));
                break;
            case R.id.download_file_end:
                Toast.makeText(this, "停止", Toast.LENGTH_SHORT).show();
                startService(new Intent(this, DownloadService.class).setAction(DownloadService.ACTION_STOP).putExtra("fileInfo", mFileInfo));
                break;
        }
    }
}
