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

    private ActivityMainBinding mBinding;
    private int[] mPwdGestureArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btSetPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.createPwd().show(MainActivity.this);
            }
        });
        mBinding.btSetPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.createPwd()
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setNormalOvalStrokeWidth(2)
                        .setNormalOvalStrokeColor(0xFFAAAAAA)
                        .setNormalOvalSolidColor(0xFFAAAAAA)
                        .setNormalOvalRadius(10)
                        .setSelectOvalStrokeWidth(2)
                        .setSelectOvalStrokeColor(0xFF1BBC9B)
                        .setSelectOvalSolidColor(0xFF1BBC9B)
                        .setSelectOvalRadius(10)
                        .setShowGestureLine(true)
                        .setGestureLineWidth(2)
                        .setGestureLineColor(0xFF1BBC9B)
                        .setAutoMatch(false)
                        .setMinSelectCount(4)
                        .setActionBarBackgroundColor(0xFF1BBC9B)
                        .setStatusBarBackgroundColor(0x1A000000)
                        .setTitleBackgroundColor(0xFF1BBC9B)
                        .setTitle("创建手势密码2")
                        .setTitleBackButtonVisible(true)
                        .show(MainActivity.this);
            }
        });
        mBinding.btSetPassword3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.createPwd()
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setNormalOvalStrokeWidth(2)
                        .setNormalOvalStrokeColor(0xFFAAAAAA)
                        .setNormalOvalSolidColor(0)
                        .setNormalOvalRadius(90)
                        .setSelectOvalStrokeWidth(2)
                        .setSelectOvalStrokeColor(0xFF1BBC9B)
                        .setSelectOvalSolidColor(0)
                        .setSelectOvalRadius(30)
                        .setShowGestureLine(true)
                        .setGestureLineWidth(2)
                        .setGestureLineColor(0xFF1BBC9B)
                        .setAutoMatch(false)
                        .setMinSelectCount(4)
                        .setActionBarBackgroundColor(0xFFFF0000)
                        .setStatusBarBackgroundColor(0x1A000000)
                        .setTitleBackgroundColor(0xFFFF0000)
                        .setTitle("创建手势密码3")
                        .setTitleBackButtonVisible(true)
                        .show(MainActivity.this);
            }
        });
        mBinding.btSetPassword4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.createPwd()
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setNormalOvalStrokeWidth(2)
                        .setNormalOvalStrokeColor(0xFFAAAAAA)
                        .setNormalOvalStrokeSelectColor(0xFF1BBC9B)
                        .setNormalOvalStrokeErrorColor(0xFFFF0000)
                        .setNormalOvalSolidColor(0x22AAAAAA)
                        .setNormalOvalSolidSelectColor(0x221BBC9B)
                        .setNormalOvalSolidErrorColor(0x22FF0000)
                        .setNormalOvalRadius(90)
                        .setSelectOvalStrokeWidth(2)
                        .setSelectOvalStrokeColor(0xFF1BBC9B)
                        .setSelectOvalSolidColor(0xFF1BBC9B)
                        .setSelectOvalRadius(30)
                        .setShowGestureLine(true)
                        .setGestureLineWidth(2)
                        .setGestureLineColor(0xFF1BBC9B)
                        .setAutoMatch(false)
                        .setMinSelectCount(4)
                        .setActionBarBackgroundColor(0xFFFF0000)
                        .setStatusBarBackgroundColor(0x1A000000)
                        .setTitleBackgroundColor(0xFFFF0000)
                        .setTitle("创建手势密码3")
                        .setTitleBackButtonVisible(true)
                        .show(MainActivity.this);
            }
        });
        mBinding.btInputPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPwdGestureArray == null || mPwdGestureArray.length == 0) {
                    showToast("请先设置手势密码");
                    return;
                }
                PwdGestureUtils.inputPwd()
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setNormalOvalStrokeWidth(2)
                        .setNormalOvalStrokeColor(0xFFAAAAAA)
                        .setNormalOvalSolidColor(0xFFAAAAAA)
                        .setNormalOvalRadius(10)
                        .setSelectOvalStrokeWidth(2)
                        .setSelectOvalStrokeColor(0xFF1BBC9B)
                        .setSelectOvalSolidColor(0xFF1BBC9B)
                        .setSelectOvalRadius(10)
                        .setShowGestureLine(true)
                        .setGestureLineWidth(2)
                        .setGestureLineColor(0xFF1BBC9B)
                        .setAutoMatch(true)
                        .setAutoResetDelay(1000)
                        .setMatchFailedColor(0xFFFF0000)
                        .setMinSelectCount(4)
                        .setRightPassword(mPwdGestureArray)
                        .setActionBarBackgroundColor(0xFF1BBC9B)
//                        .setStatusBarBackgroundColor(0x1A000000)
//                        .setTitleBackgroundColor(0xFF1BBC9B)
                        .setTitle("验证手势密码")
                        .setTitleBackButtonVisible(false)
                        .show(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PwdGestureUtils.Build.REQUEST_CODE_CREATE_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    showToast("手势密码设置成功");
                    mPwdGestureArray = data.getIntArrayExtra(PwdGestureUtils.Build.KEY_RIGHT_PASSWORD);
                } else {
                    showToast("取消设置");
                }
                break;
            case PwdGestureUtils.Build.REQUEST_CODE_INPUT_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    // 手势密码验证通过，进入首页
                    showToast("手势密码验证通过");
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                    finish();
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


}
