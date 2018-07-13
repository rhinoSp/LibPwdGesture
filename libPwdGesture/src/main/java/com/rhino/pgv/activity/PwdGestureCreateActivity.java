package com.rhino.pgv.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.utils.StatusBarUtils;
import com.rhino.pgv.view.PwdGestureView;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureCreateActivity extends FragmentActivity implements View.OnClickListener,
        PwdGestureView.OnGestureChangedListener {

    public LinearLayout mLlActionBarContainer;
    public View mVStatusBar;
    public View mVTitleContainer;
    public ImageView mIvTitleBack;
    public TextView mTvTitle;
    public TextView mTvTips1;
    public TextView mTvTips2;
    public PwdGestureView mPwdGestureView;
    public TextView mBtRedraw;
    public TextView mBtSure;
    public View mVBtTopLine;
    public View mVBtCenterLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        StatusBarUtils.setSemiTransparentStatusBarColor(this, Color.TRANSPARENT);
        setContentView(R.layout.activity_pwd_gesture_create);
        initView();
        onCLickReset();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.action_bar_back_key_icon == id) {
            onBackPressed();
        } else if (R.id.bt_redraw == id) {
            onCLickReset();
        } else if (R.id.bt_sure == id) {
            onClickSure();
        }
    }

    @Override
    public void onGestureStarted(PwdGestureView view) {
        mTvTips1.setText("完成后松开手指");
    }

    @Override
    public void onGestureMoving(PwdGestureView view) {

    }

    @Override
    public void onGestureFinished(PwdGestureView view, boolean right) {
        if (view.getMinSelectCount() > view.getInputPassword().size()) {
            mTvTips1.setText(String.format(Locale.getDefault(), "至少需连接%d个点，请重试", mPwdGestureView.getMinSelectCount()));
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
                mPwdGestureView.setEnabled(false);
            } else {
                mTvTips1.setText("两次图案不一致");
                mPwdGestureView.showMatchFailedView();
            }
        }
    }

    public void onCLickReset() {
        mPwdGestureView.reset();
        mPwdGestureView.setEnabled(true);
        mPwdGestureView.setRightPassword(new int[]{});
        mBtRedraw.setVisibility(View.INVISIBLE);
        mBtSure.setVisibility(View.INVISIBLE);
        mBtSure.setEnabled(false);
        mVBtTopLine.setVisibility(View.INVISIBLE);
        mVBtCenterLine.setVisibility(View.INVISIBLE);
        mTvTips1.setText(String.format(Locale.getDefault(), "绘制密码图案，请至少连接%d个点", mPwdGestureView.getMinSelectCount()));
    }

    public void onClickSure() {
        Intent intent = new Intent();
        intent.putExtra(PwdGestureUtils.Build.KEY_RIGHT_PASSWORD, mPwdGestureView.getRightPasswordArray());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void initView() {
        mLlActionBarContainer = findViewById(R.id.action_bar_container);
        mVStatusBar = findViewById(R.id.action_bar_status);
        mVTitleContainer = findViewById(R.id.action_bar_title_container);
        mIvTitleBack = findViewById(R.id.action_bar_back_key_icon);
        mIvTitleBack.setOnClickListener(this);
        mTvTitle = findViewById(R.id.action_bar_title);
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
        mPwdGestureView.setOnGestureChangedListener(this);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            PwdGestureUtils.initPwdGestureView(mPwdGestureView, extra);
            initTitle(extra);
        }
    }

    public void initTitle(@NonNull Bundle extra) {
        int actionBarBackgroundColor = extra.getInt(PwdGestureUtils.Build.KEY_ACTION_BAR_BACKGROUND_COLOR);
        if (actionBarBackgroundColor != 0) {
            mLlActionBarContainer.setBackgroundColor(actionBarBackgroundColor);
        }
        int statusBarBackgroundColor = extra.getInt(PwdGestureUtils.Build.KEY_STATUS_BAR_BACKGROUND_COLOR);
        if (statusBarBackgroundColor != 0) {
            mVStatusBar.setBackgroundColor(statusBarBackgroundColor);
        }
        int titleBackgroundColor = extra.getInt(PwdGestureUtils.Build.KEY_TITLE_BACKGROUND_COLOR);
        if (titleBackgroundColor != 0) {
            mVTitleContainer.setBackgroundColor(titleBackgroundColor);
        }

        String title = extra.getString(PwdGestureUtils.Build.KEY_TITLE_TEXT);
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
        mIvTitleBack.setVisibility(extra.getBoolean(PwdGestureUtils.Build.KEY_TITLE_BACK_BUTTON_VISIBLE, true)
                ? View.VISIBLE : View.INVISIBLE);

        int statusBarHeight = StatusBarUtils.getStatusBarHeight(getApplicationContext());
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mVStatusBar.getLayoutParams();
        lp.height = statusBarHeight;

    }

}
