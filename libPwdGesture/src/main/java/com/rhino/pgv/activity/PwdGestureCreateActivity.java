package com.rhino.pgv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.view.PwdGestureView;

import org.w3c.dom.Text;

/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureCreateActivity extends AppCompatActivity implements View.OnClickListener,
        PwdGestureView.OnGestureFinishedListener {

    private PwdGestureView mPwdGestureView;
    public TextView mTvTips1;
    public TextView mTvTips2;
    public TextView mBtRedraw;
    public TextView mBtSure;
    public View mVBtTopLine;
    public View mVBtCenterLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_gesture_create);
        initView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.bt_redraw == id) {
            onCLickReset();
        } else if (R.id.bt_sure == id) {
            onClickSure();
        }
    }

    @Override
    public void onGestureFinished(PwdGestureView view, boolean right) {
        if (view.getMinPointCount() > view.getInputPassword().size()) {
            mTvTips1.setText("至少需连接4个点，请重试");
            view.reset();
            return;
        }
        if (view.getRightPassword().isEmpty()) {
            // 第一次输入
            view.setRightPassword(view.getInputPassword());
            view.reset();
            mTvTips1.setText("再次绘制图案确认");
            mBtRedraw.setVisibility(View.VISIBLE);
            mBtSure.setVisibility(View.VISIBLE);
            mVBtTopLine.setVisibility(View.VISIBLE);
            mVBtCenterLine.setVisibility(View.VISIBLE);
        } else {
            // 第二次输入
            if (right) {
                mTvTips1.setText("您的新密码");
                mBtSure.setEnabled(true);
            } else {
                mTvTips1.setText("两次图案不一致");
            }
        }
    }

    public void onCLickReset() {
        mPwdGestureView.reset();
        mPwdGestureView.setRightPassword(new int[]{});
        mBtRedraw.setVisibility(View.INVISIBLE);
        mBtSure.setVisibility(View.INVISIBLE);
        mBtSure.setEnabled(false);
        mVBtTopLine.setVisibility(View.INVISIBLE);
        mVBtCenterLine.setVisibility(View.INVISIBLE);
        mTvTips1.setText("绘制密码图案，请至少连接4个点");
    }

    public void onClickSure() {
        Intent intent = new Intent();
        intent.putExtra(PwdGestureUtils.Build.KEY_RIGHT_PASSWORD, mPwdGestureView.getRightPasswordArray());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void initView() {
        mTvTips1 = findViewById(R.id.tv_tips1);
        mTvTips2 = findViewById(R.id.tv_tips2);
        mBtRedraw = findViewById(R.id.bt_redraw);
        mBtRedraw.setOnClickListener(this);
        mBtSure = findViewById(R.id.bt_sure);
        mBtSure.setOnClickListener(this);
        mBtSure.setEnabled(false);
        mVBtTopLine = findViewById(R.id.v_bt_top_line);
        mVBtCenterLine = findViewById(R.id.v_bt_center_line);
        mPwdGestureView = findViewById(R.id.pwd_gesture_view);
        mPwdGestureView.setOnGestureFinishedListener(this);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            PwdGestureUtils.initPwdGestureView(mPwdGestureView, extra);
        }
    }

}
