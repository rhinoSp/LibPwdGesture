package com.rhino.pgv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.LimitTryCountUtils;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.view.PwdGestureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author LuoLin
 * @since Create on 2018/7/9.
 */
public class PwdGestureInputActivity extends AppCompatActivity implements PwdGestureView.OnGestureFinishedListener {

    public PwdGestureView mPwdGestureView;
    public TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_gesture_input);
        initView();
    }

    @Override
    public void onGestureFinished(PwdGestureView view, boolean right) {
        long limitTime = LimitTryCountUtils.getInstance(getApplicationContext()).limitDuration("pwd_gesture");
        if (0 < limitTime) {
            mTvTips.setVisibility(View.VISIBLE);
            int sec = (int) (limitTime / 1000);
            int min = sec / 60;
            if (0 < min) {
                mTvTips.setText(String.format(Locale.getDefault(), "请%d分钟后再尝试", min));
            } else {
                mTvTips.setText(String.format(Locale.getDefault(), "请%d秒后再尝试", sec));
            }
        } else if (right) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            mTvTips.setVisibility(View.VISIBLE);
            if (view.getMinPointCount() > view.getInputPassword().size()) {
                mTvTips.setText("请至少连接4个点");
            } else {
                mTvTips.setText("密码错误");
                LimitTryCountUtils.getInstance(getApplicationContext()).addTryCount("pwd_gesture");
            }
        }
    }

    public void initView() {
        mTvTips = findViewById(R.id.tv_tips);
        mPwdGestureView = findViewById(R.id.pwd_gesture_view);
        mPwdGestureView.setOnGestureFinishedListener(this);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            PwdGestureUtils.initPwdGestureView(mPwdGestureView, extra);
        }
    }


}
