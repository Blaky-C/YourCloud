package com.yourcloud.yourcloud.View.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;
import com.yourcloud.yourcloud.Model.Utils.KiiUtils;
import com.yourcloud.yourcloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static String userName;
    public static String password;

    @BindView(R.id.username)
    TextInputLayout usernameWrapper;
    @BindView(R.id.password)
    TextInputLayout passwordWrapper;
    @BindView(R.id.tv_username)
    EditText mUserName;
    @BindView(R.id.tv_password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mLogin;
    @BindView(R.id.tv_signup)
    TextView mSignUp;
    @BindView(R.id.container)
    CoordinatorLayout mCoordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        mUserName.setHint("Username/PhoneNumber");
        mPassword.setHint("Password");
        //for test
//        mUserName.setText("ritchie445412@gmail.com");
//        mPassword.setText("wshr445412");

        mSignUp.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mSignUp.getPaint().setAntiAlias(true);

    }

    @OnClick({R.id.tv_signup, R.id.btn_login})
    public void doLogin(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                getUserInfo();
                login();
                break;
            default:
                break;
        }


    }

    public void getUserInfo() {
        userName = mUserName.getText().toString();
        password = mPassword.getText().toString();

    }


    private void login() {
        KiiUser.logIn(new KiiUserCallBack() {
            @Override
            public void onLoginCompleted(int token, KiiUser user, Exception exception) {
                if (exception != null) {
                    snackText("用户名或密码错误");
                    return;
                } else {
                    snackText("登录成功");
//                    KiiBucket mKiiBucket = user.bucket(user.getID() + "_bucket");
                    new KiiUtils().queryObject();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
                }

            }
        }, userName, password);

    }

    private void snackText(String string) {
        Snackbar.make(mCoordinatorLayout, string, Snackbar.LENGTH_SHORT)
                .show();
    }


}
