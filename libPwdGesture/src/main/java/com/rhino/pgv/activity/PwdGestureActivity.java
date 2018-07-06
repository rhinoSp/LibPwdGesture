package com.rhino.pgv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rhino.pgv.R;
import com.rhino.pgv.utils.PwdGestureUtils;
import com.rhino.pgv.view.PwdGestureView;

/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureActivity extends AppCompatActivity {

    private PwdGestureView mPwdGestureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_gesture);
        initPwdGestureView();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void initPwdGestureView() {
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            return;
        }

        mPwdGestureView = findViewById(R.id.pwd_gesture_view);

        mPwdGestureView.setRowCount(extra.getInt(PwdGestureUtils.Build.KEY_ROW_COUNT));
        mPwdGestureView.setColumnCount(extra.getInt(PwdGestureUtils.Build.KEY_COLUMN_COUNT));

        mPwdGestureView.setCircleColor(extra.getInt(PwdGestureUtils.Build.KEY_CIRCLE_COLOR));
        mPwdGestureView.setCircleLineWidth(extra.getInt(PwdGestureUtils.Build.KEY_CIRCLE_LINE_WIDTH));
        mPwdGestureView.setCircleRadius(extra.getInt(PwdGestureUtils.Build.KEY_CIRCLE_RADIUS));

        mPwdGestureView.setCircleSelectColor(extra.getInt(PwdGestureUtils.Build.KEY_CIRCLE_SELECT_COLOR));
        mPwdGestureView.setCircleSelectRadius(extra.getInt(PwdGestureUtils.Build.KEY_CIRCLE_SELECT_RADIUS));

        mPwdGestureView.setLineColor(extra.getInt(PwdGestureUtils.Build.KEY_LINE_COLOR));
        mPwdGestureView.setLineWidth(extra.getInt(PwdGestureUtils.Build.KEY_LINE_WIDTH));

        mPwdGestureView.setShowLine(extra.getBoolean(PwdGestureUtils.Build.KEY_SHOW_LINE, true));

        mPwdGestureView.setRightPassword(extra.getIntArray(PwdGestureUtils.Build.KEY_RIGHT_PASSWORD));

        mPwdGestureView.setOnGestureFinishedListener(new PwdGestureView.OnGestureFinishedListener() {
            @Override
            public void onFinish(PwdGestureView view, boolean right) {
                if (right) {
                    showToast("输入正确，" + view.getInputPassword().toString());
                } else {
                    showToast("输入错误！");
                }
            }
        });

    }


}
