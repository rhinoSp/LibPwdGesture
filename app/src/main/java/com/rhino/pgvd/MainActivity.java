package com.rhino.pgvd;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rhino.pgv.view.PwdGestureView;
import com.rhino.pgvd.R;
import com.rhino.pgvd.databinding.ActivityMainBinding;
import com.rhino.pgv.utils.PwdGestureUtils;


/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class MainActivity extends AppCompatActivity {

    private int[] mPwdGestureArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PwdGestureUtils.Build.REQUEST_CODE_CREATE_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    showToast("手势密码创建成功");
                    mPwdGestureArray = data.getIntArrayExtra(PwdGestureUtils.Build.KEY_RIGHT_PASSWORD);
                } else {
                    showToast("取消创建");
                }
                break;
            case PwdGestureUtils.Build.REQUEST_CODE_INPUT_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    // 手势密码验证通过，进入首页
                    showToast("手势密码验证通过");
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                    //finish();
                } else {
                    showToast("放弃验证");
                }
                break;
            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showCreatePwdStyle1(View v) {
        PwdGestureUtils.createPwd()
                .setTitle("创建手势密码1")
                .show(MainActivity.this);
    }

    public void showCreatePwdStyle2(View v) {
        PwdGestureUtils.createPwd()
                .setNormalOvalStrokeWidth(0)
                .setNormalOvalSolidColor(0x1A000000)
                .setNormalOvalSolidSelectColor(0xFFD9534F)
                .setNormalOvalSolidErrorColor(0xFFFF0000)
                .setNormalOvalRadius(10)
                .setSelectOvalSolidColor(0xFFD9534F)
                .setSelectOvalSolidErrorColor(0xFFFF0000)
                .setSelectOvalRadius(10)
                .setGestureLineColor(0xFFD9534F)
                .setGestureLineErrorColor(0xFFFF0000)
                .setActionBarBackgroundColor(0xFFD9534F)
                .setStatusBarBackgroundColor(0x1A000000)
                .setTitleBackgroundColor(0xFFD9534F)
                .setTitle("创建手势密码2")
                .setTitleBackButtonVisible(true)
                .show(MainActivity.this);
    }

    public void showCreatePwdStyle3(View v) {
        PwdGestureUtils.createPwd()
                .setNormalOvalStrokeColor(0x1A000000)
                .setNormalOvalStrokeSelectColor(0xFF6AB74B)
                .setNormalOvalStrokeErrorColor(0xFFFF0000)
                .setNormalOvalSolidColor(0)
                .setSelectOvalStrokeWidth(3)
                .setSelectOvalStrokeColor(0xFF6AB74B)
                .setSelectOvalStrokeErrorColor(0xFFFF0000)
                .setSelectOvalSolidColor(0)
                .setGestureLineColor(0xFF6AB74B)
                .setGestureLineErrorColor(0xFFFF0000)
                .setActionBarBackgroundColor(0xFF6AB74B)
                .setStatusBarBackgroundColor(0x00000000)
                .setTitleBackgroundColor(0xFF6AB74B)
                .setTitle("创建手势密码3")
                .setTitleBackButtonVisible(true)
                .show(MainActivity.this);
    }

    public void showCreatePwdStyle4(View v) {
        PwdGestureUtils.createPwd()
                .setRowCount(3)
                .setColumnCount(3)
                .setNormalOvalStrokeWidth(3)
                .setNormalOvalStrokeColor(0x1A000000)
                .setNormalOvalStrokeSelectColor(0x22038977)
                .setNormalOvalStrokeErrorColor(0x22FF0000)
                .setNormalOvalSolidColor(0)
                .setNormalOvalSolidSelectColor(0x22038977)
                .setNormalOvalSolidErrorColor(0x22FF0000)
                .setNormalOvalRadius(90)
                .setSelectOvalStrokeWidth(0)
                .setSelectOvalSolidColor(0xFF038977)
                .setSelectOvalSolidErrorColor(0xFFFF0000)
                .setSelectOvalRadius(50)
                .setShowGestureLine(true)
                .setGestureLineWidth(3)
                .setGestureLineColor(0xFF038977)
                .setGestureLineErrorColor(0xFFFF0000)
                .setMinSelectCount(4)
                .setActionBarBackgroundColor(0xFF038977)
                .setStatusBarBackgroundColor(0x1A000000)
                .setTitleBackgroundColor(0xFF038977)
                .setTitle("创建手势密码4")
                .setTitleBackButtonVisible(true)
                .show(MainActivity.this);
    }

    public void showInputPwd1(View v) {
        if (mPwdGestureArray == null || mPwdGestureArray.length == 0) {
            showToast("请先创建手势密码");
            return;
        }
        PwdGestureUtils.inputPwd()
                .setRightPassword(mPwdGestureArray)
                .setTitle("验证手势密码1")
                .setTitleBackButtonVisible(false)
                .show(MainActivity.this);
    }

    public void showInputPwd2(View v) {
        if (mPwdGestureArray == null || mPwdGestureArray.length == 0) {
            showToast("请先创建手势密码");
            return;
        }
        PwdGestureUtils.inputPwd()
                .setNormalOvalStrokeWidth(0)
                .setNormalOvalSolidColor(0x1A000000)
                .setNormalOvalSolidSelectColor(0xFFD9534F)
                .setNormalOvalSolidErrorColor(0xFFFF0000)
                .setNormalOvalRadius(10)
                .setSelectOvalSolidColor(0xFFD9534F)
                .setSelectOvalSolidErrorColor(0xFFFF0000)
                .setSelectOvalRadius(10)
                .setGestureLineColor(0xFFD9534F)
                .setGestureLineErrorColor(0xFFFF0000)
                .setRightPassword(mPwdGestureArray)
                .setActionBarBackgroundColor(0xFFD9534F)
                .setStatusBarBackgroundColor(0x1A000000)
                .setTitleBackgroundColor(0xFFD9534F)
                .setTitle("验证手势密码2")
                .setTitleBackButtonVisible(false)
                .show(MainActivity.this);
    }

    public void showInputPwd3(View v) {
        if (mPwdGestureArray == null || mPwdGestureArray.length == 0) {
            showToast("请先创建手势密码");
            return;
        }
        PwdGestureUtils.inputPwd()
                .setNormalOvalStrokeColor(0x1A000000)
                .setNormalOvalStrokeSelectColor(0xFF6AB74B)
                .setNormalOvalStrokeErrorColor(0xFFFF0000)
                .setNormalOvalSolidColor(0)
                .setSelectOvalStrokeWidth(3)
                .setSelectOvalStrokeColor(0xFF6AB74B)
                .setSelectOvalStrokeErrorColor(0xFFFF0000)
                .setSelectOvalSolidColor(0)
                .setGestureLineColor(0xFF6AB74B)
                .setGestureLineErrorColor(0xFFFF0000)
                .setRightPassword(mPwdGestureArray)
                .setActionBarBackgroundColor(0xFF6AB74B)
                .setStatusBarBackgroundColor(0x00000000)
                .setTitleBackgroundColor(0xFF6AB74B)
                .setTitle("验证手势密码3")
                .setTitleBackButtonVisible(false)
                .show(MainActivity.this);
    }

    public void showInputPwd4(View v) {
        if (mPwdGestureArray == null || mPwdGestureArray.length == 0) {
            showToast("请先创建手势密码");
            return;
        }
        PwdGestureUtils.inputPwd()
                .setRowCount(3)
                .setColumnCount(3)
                .setNormalOvalStrokeWidth(3)
                .setNormalOvalStrokeColor(0x1A000000)
                .setNormalOvalStrokeSelectColor(0x22038977)
                .setNormalOvalStrokeErrorColor(0x22FF0000)
                .setNormalOvalSolidColor(0)
                .setNormalOvalSolidSelectColor(0x22038977)
                .setNormalOvalSolidErrorColor(0x22FF0000)
                .setNormalOvalRadius(90)
                .setSelectOvalStrokeWidth(0)
                .setSelectOvalSolidColor(0xFF038977)
                .setSelectOvalSolidErrorColor(0xFFFF0000)
                .setSelectOvalRadius(50)
                .setShowGestureLine(true)
                .setGestureLineWidth(3)
                .setGestureLineColor(0xFF038977)
                .setGestureLineErrorColor(0xFFFF0000)
                .setAutoMatch(true)
                .setAutoResetDelay(1000)
                .setMinSelectCount(4)
                .setRightPassword(mPwdGestureArray)
                .setActionBarBackgroundColor(0xFF038977)
                .setStatusBarBackgroundColor(0x1A000000)
                .setTitleBackgroundColor(0xFF038977)
                .setTitle("验证手势密码4")
                .setTitleBackButtonVisible(false)
                .show(MainActivity.this);
    }

}
