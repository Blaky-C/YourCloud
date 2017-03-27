package com.yourcloud.yourcloud.View.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yourcloud.yourcloud.Model.Items.HeaderItem;
import com.yourcloud.yourcloud.Model.Utils.Constant;
import com.yourcloud.yourcloud.Model.Utils.DBDao;
import com.yourcloud.yourcloud.Model.Utils.FindSpecificFile;
import com.yourcloud.yourcloud.Model.Utils.KiiUtils;
import com.yourcloud.yourcloud.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    public FindSpecificFile mFindDocFile;
    private static int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0x001;
    //for test
    private static int ALL_TIME = 5000;

    private static int TIME_INTERVAL = 1000;

    @BindView(R.id.tv_counter)
    TextView mCounter;
    @BindView(R.id.bottom_view)
    CoordinatorLayout mBottomView;
    private MyCountDownTimer mTimer;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        searchData(requestCode, grantResults);

    }

    private void searchData(int requestCode, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SearchFile().start();
            } else {
                Snackbar.make(mBottomView, "您未获得授权，请先授权!", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ButterKnife.bind(this);
        new DBDao(this).clear();

        mTimer = new MyCountDownTimer(ALL_TIME, TIME_INTERVAL);
        mTimer.start();


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }, ALL_TIME);

    }

    class SearchFile extends Thread {
        @Override
        public void run() {
            mFindDocFile = new FindSpecificFile(getBaseContext(), new String[]{".doc", ".docx", ".pdf"}, "1");
//            mFindDocFile = new FindSpecificFile(getBaseContext(), new String[]{".mp3"}, "2");
//            mFindDocFile = new FindSpecificFile(getBaseContext(), new String[]{".img", ".png", ".gif", ".jpeg"}, "3");
//            mFindDocFile = new FindSpecificFile(getBaseContext(), new String[]{".mp4", ".mpg", ".mkv", ".wmv", ".avi", ".mov"}, "4");
//            mFindDocFile = new FindSpecificFile(getBaseContext(), new String[]{".rar", ".zip", ".7z"}, "5");

        }
    }


    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                }

            } else {
                new SearchFile().start();
            }

        }


        @Override
        public void onTick(long l) {
            mCounter.setText("倒计时 " + l / TIME_INTERVAL + "S");
        }

        @Override
        public void onFinish() {
            mCounter.setText("正在跳转");
        }
    }

}
