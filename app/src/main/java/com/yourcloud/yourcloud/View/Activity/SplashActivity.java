package com.yourcloud.yourcloud.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yourcloud.yourcloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    private static int ALL_TIME = 5000;
    private static int TIME_INTERVAL = 1000;

    @BindView(R.id.tv_counter)
    TextView mCounter;
    private MyCountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ButterKnife.bind(this);

        mTimer = new MyCountDownTimer(ALL_TIME, TIME_INTERVAL);
        mTimer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, ALL_TIME);


    }

    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mCounter.setText("倒计时 " +l/TIME_INTERVAL+ "S");
        }

        @Override
        public void onFinish() {
            mCounter.setText("正在跳转");
        }
    }

}
