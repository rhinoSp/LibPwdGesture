package com.rhino.pgv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.view.PwdGestureView;

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
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onGestureFinished(PwdGestureView view, boolean right) {
        if (right) {
            // TODO
            showToast("密码正确，" + view.getInputPassword().toString());
        } else {
            showToast("密码错误！");
            if (view.getMinPointCount() > view.getInputPassword().size()) {
                mTvTips.setText("请至少连接4个点");
                mTvTips.setTextColor(0xFFFF0000);
                mTvTips.setVisibility(View.VISIBLE);
            } else {
                // TODO

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

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
