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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.LimitTryCountUtils;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.utils.StatusBarUtils;
import com.rhino.pgv.view.PwdGestureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>验证手势密码</p>
 *
 * @author LuoLin
 * @since Create on 2018/7/9.
 */
public class PwdGestureInputActivity extends FragmentActivity implements PwdGestureView.OnGestureChangedListener,
        View.OnClickListener {

    public static final String LIMIT_KEY = "pwd_gesture";
    public LinearLayout mLlActionBarContainer;
    public View mVStatusBar;
    public View mVTitleContainer;
    public ImageView mIvTitleBack;
    public TextView mTvTitle;
    public TextView mTvTips;
    public PwdGestureView mPwdGestureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        StatusBarUtils.setSemiTransparentStatusBarColor(this, Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_gesture_input);
        initView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.action_bar_back_key_icon == id) {
            onBackPressed();
        }
    }

    @Override
    public void onGestureStarted(PwdGestureView view) {
        mTvTips.setText("");
    }

    @Override
    public void onGestureMoving(PwdGestureView view) {

    }

    @Override
    public void onGestureFinished(PwdGestureView view, boolean right) {
        int limitSecond = (int) (LimitTryCountUtils.getInstance(getApplicationContext()).limitDuration(LIMIT_KEY) / 1000);
        if (0 < limitSecond) {
            mTvTips.setVisibility(View.VISIBLE);
            int limitMin = limitSecond / 60;
            if (0 < limitMin) {
                mTvTips.setText(String.format(Locale.getDefault(), "请%d分钟后再尝试", limitMin));
            } else {
                mTvTips.setText(String.format(Locale.getDefault(), "请%d秒后再尝试", limitSecond));
            }
        } else if (right) {
            LimitTryCountUtils.getInstance(getApplicationContext()).reset(LIMIT_KEY);
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            mTvTips.setVisibility(View.VISIBLE);
            if (view.getMinSelectCount() > view.getInputPassword().size()) {
                mTvTips.setText(String.format(Locale.getDefault(), "至少需连接%d个点，请重试", mPwdGestureView.getMinSelectCount()));
            } else {
                mTvTips.setText("密码错误");
//                LimitTryCountUtils.getInstance(getApplicationContext()).addTryCount(LIMIT_KEY);
            }
        }
    }

    public void initView() {
        mLlActionBarContainer = findViewById(R.id.action_bar_container);
        mVStatusBar = findViewById(R.id.action_bar_status);
        mVTitleContainer = findViewById(R.id.action_bar_title_container);
        mIvTitleBack = findViewById(R.id.action_bar_back_key_icon);
        mIvTitleBack.setOnClickListener(this);
        mTvTitle = findViewById(R.id.action_bar_title);
        mTvTips = findViewById(R.id.tv_tips);
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
