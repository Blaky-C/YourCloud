package com.yourcloud.yourcloud.View.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yourcloud.yourcloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.line1, R.id.line2})
    public void doSignUp(View view){
        switch (view.getId()) {
            case R.id.line1:
                Intent emailIntent = new Intent(this, SignEmailActivity.class);
                startActivity(emailIntent, ActivityOptions.makeSceneTransitionAnimation(this, line1, "signUpWithEmail").toBundle());
                break;
            case R.id.line2:
                Intent phoneIntent = new Intent(this, SignPhoneActivity.class);
                startActivity(phoneIntent, ActivityOptions.makeSceneTransitionAnimation(this, line2, "signUpWithPhone").toBundle());
                break;
            default:
                break;
        }
    }
}
